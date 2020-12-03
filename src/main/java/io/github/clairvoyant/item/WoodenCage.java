/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.item;

import io.github.clairvoyant.entity.ClairvoyantEntities;
import io.github.clairvoyant.entity.FeyEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class WoodenCage extends Item {

    private EntityType entityTypeStored;


    public WoodenCage(Settings settings) {
        super(new Settings().maxCount(1));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (entity instanceof FeyEntity) {
            // Get the entity clicked on and what creature's already inside the tag
            FeyEntity feyEntity = (FeyEntity) entity;
            CompoundTag tag = stack.getTag();

            if (tag != null) {
                if (tag.contains("CreatureStored") && tag.get("CreatureStored") != null) {
                    // Do nothing, cage has a creature
                    user.sendMessage(new TranslatableText("item.clairvoyant.wooden_cage.use_full"), true);
                    return ActionResult.FAIL;
                }
            } else {
                // Store the creature as nbt
                CompoundTag compoundTag = getCompound(feyEntity.getType());
                stack.setTag(compoundTag);
                // Maybe add a sound effect and swiping particles
                user.sendMessage(new TranslatableText("item.clairvoyant.wooden_cage.use_fey"), true);
                feyEntity.remove();
                return ActionResult.SUCCESS;
            }
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack stack = context.getStack();
        CompoundTag tag = stack.getTag();

        // If there's a creature inside
        if (tag.contains("CreatureStored") && tag.get("CreatureStored") != null) {
            // If it's a fey
            if (getEntityType(tag.getCompound("CreatureStored")) == ClairvoyantEntities.FEY) {
                // Spawn in a fey in the world
                FeyEntity fey = ClairvoyantEntities.FEY.create(context.getWorld());
                if (fey != null) {
                    fey.refreshPositionAndAngles(context.getBlockPos(), fey.yaw, fey.pitch);
                    context.getWorld().spawnEntity(fey);
                    // Remove from tag
                    tag.remove("CreatureStored");
                }
            }
        }
        return super.useOnBlock(context);
    }

    public EntityType<?> getEntityType(CompoundTag tag) {
        if (tag != null && tag.contains("CreatureStored")) {
            CompoundTag compoundTag = tag.getCompound("CreatureStored");
            if (compoundTag.contains("id")) {
                return (EntityType) EntityType.get(compoundTag.getString("id")).orElse(this.entityTypeStored);
            }
        }

        return this.entityTypeStored;
    }

    public CompoundTag getCompound(EntityType<?> entityType) {
        CompoundTag entityTag = new CompoundTag();
        // Put in the id
        entityTag.putString("id", entityType.getTranslationKey());
        // return it
        return entityTag;
    }

}
