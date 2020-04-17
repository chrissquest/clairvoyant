/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.item;

import io.github.cornflower.block.BottledFeyBlock;
import io.github.cornflower.client.KeyBinds;
import io.github.cornflower.entity.CornflowerEntities;
import io.github.cornflower.entity.FeyEntity;
import io.github.cornflower.entity.FeyType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import static io.github.cornflower.group.CornflowerGroup.CORNFLOWER_GROUP;

public class CornflowerWand extends Item {

    public CornflowerWand() {
        super(new Settings().group(CORNFLOWER_GROUP).maxCount(1));
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    // On right click block
    public ActionResult useOnBlock(ItemUsageContext context) {
        // If it's a fey in a bottle, release it
        // If it's in item mode and a chest, set it to input/output. The input actually does not work by default, we're using a mixin for that
        // If it's in animal mode, set it to input/output
        if (!context.getWorld().isClient()) {
            CompoundTag tag = context.getStack().getTag();

            if (context.getWorld().getBlockState(context.getBlockPos()).getBlock() instanceof BottledFeyBlock) {
                context.getWorld().removeBlock(context.getBlockPos(), false);

                FeyEntity fey = CornflowerEntities.FEY.create(context.getWorld());
                if (fey != null) {
                    fey.refreshPositionAndAngles(context.getBlockPos(), fey.yaw, fey.pitch);
                    context.getWorld().spawnEntity(fey);
                }
                return ActionResult.SUCCESS;
            }
            // Set chest input/output
            // TODO: Make it work for different types of inventories?
            else if (tag != null && tag.contains("Type")) {
                if (tag.getString("Type").equals(FeyType.TRANSPORT_ITEM.toString()) && context.getPlayer() != null && context.getWorld().getBlockEntity(context.getBlockPos()) instanceof LootableContainerBlockEntity) {
                    if (context.getPlayer().isSneaking()) {
                        // Set output block
                        tag.put("BlockOutput", NbtHelper.fromBlockPos(context.getBlockPos()));
                        context.getPlayer().addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_output"), true);
                        return ActionResult.SUCCESS;
                    }/*else {
                // Set input block, actually in chest mixin
                blockInput = context.getBlockPos();
                context.getPlayer().addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_input"), true);
                return ActionResult.SUCCESS;
                } */
                } else if (tag.getString("Type").equals(FeyType.TRANSPORT_ANIMAL.toString()) && context.getPlayer() != null) {
                    if (context.getPlayer().isSneaking()) {
                        // Set output block
                        tag.put("BlockOutput", NbtHelper.fromBlockPos(context.getBlockPos()));
                        context.getPlayer().addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_output"), true);
                        return ActionResult.SUCCESS;
                    } else {
                        // Set input block, actually in chest mixin
                        tag.put("BlockInput", NbtHelper.fromBlockPos(context.getBlockPos()));
                        context.getPlayer().addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_input"), true);
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public boolean useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        // Handle clicking on a Fey to set the input/output block
        if (entity instanceof FeyEntity) {
            FeyEntity feyEntity = (FeyEntity) entity;

            CompoundTag tag = stack.getTag();
            if (tag != null) {
                if (tag.contains("BlockInput") && tag.get("BlockInput") != null)
                    feyEntity.setInputBlock(NbtHelper.toBlockPos((CompoundTag) tag.get("BlockInput")));
                else feyEntity.setInputBlock(null);
                if (tag.contains("BlockOutput") && tag.get("BlockOutput") != null)
                    feyEntity.setOutputBlock(NbtHelper.toBlockPos((CompoundTag) tag.get("BlockOutput")));
                else feyEntity.setOutputBlock(null);
                if (tag.contains("Type") && tag.getString("Type") != null)
                    feyEntity.setType(FeyType.valueOf(tag.getString("Type")));
                else feyEntity.setType(FeyType.NONE);
                // Clear Fey inventory
                ItemScatterer.spawn(entity.getEntityWorld(), new BlockPos(entity.getPos()), feyEntity.getItems());
                feyEntity.removeAllPassengers();
            }

            user.addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_fey"), true);

            return true;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    // Right clicking
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Change modes with mode click
        if (!world.isClient() || !KeyBinds.wandModeKey.isPressed()) return TypedActionResult.pass(user.getStackInHand(hand));

        if (user.getMainHandStack().getItem() == CornflowerItems.CORNFLOWER_WAND) {
            // Shift returns the next mode
            useSpecificHand(user, user.getMainHandStack());
        }
        if (user.getOffHandStack().getItem() == CornflowerItems.CORNFLOWER_WAND) {
            // Shift returns the next mode
            useSpecificHand(user, user.getOffHandStack());
        }
        return TypedActionResult.success(user.getStackInHand(hand));

    }

    public void useSpecificHand(PlayerEntity user, ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null) {
            // I think i need to rewrite this without the "subtag" whatever that is
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putString("Type", "NONE");
            stack.setTag(compoundTag);
        } else if (tag.contains("Type")) {
            FeyType mode = FeyType.valueOf(tag.getString("Type"));
            mode = mode.shift();
            tag.putString("Type", mode.toString());
            // Print out what mode was just set
            if (mode == FeyType.TRANSPORT_ITEM)
                user.addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_mode_item"), true);
            else if (mode == FeyType.TRANSPORT_ANIMAL)
                user.addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_mode_animal"), true);
            else
                user.addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_mode_none"), true);

            // Clear input/output blocks
            tag.remove("BlockInput");
            tag.remove("BlockOutput");
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        CompoundTag tag = itemStack.getTag();
        if (tag != null) {
            // Mode tooltip
            if (tag.contains("Type")) {
                if (tag.getString("Type").equals(FeyType.TRANSPORT_ITEM.toString()))
                    tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip_mode_item"));
                else if (tag.getString("Type").equals(FeyType.TRANSPORT_ANIMAL.toString()))
                    tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip_mode_animal"));
                else
                    tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip_mode_none"));
            }
            // Input / Output tooltip
            if (tag.contains("BlockInput") && tag.get("BlockInput") != null) {
                tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip_input").append(NbtHelper.toBlockPos((CompoundTag) tag.get("BlockInput")).toShortString()));
            } else
                tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip_input"));
            if (tag.contains("BlockOutput") && tag.get("BlockOutput") != null)
                tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip_output").append(NbtHelper.toBlockPos((CompoundTag) tag.get("BlockOutput")).toShortString()));
            else
                tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip_output"));
        }
    }

    public void setBlockInput(BlockPos blockInput, ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null)
            tag.put("BlockInput", NbtHelper.fromBlockPos(blockInput));
    }

}
