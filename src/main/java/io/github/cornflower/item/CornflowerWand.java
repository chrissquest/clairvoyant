/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.item;

import io.github.cornflower.Cornflower;
import io.github.cornflower.block.BottledFeyBlock;
import io.github.cornflower.client.KeyBinds;
import io.github.cornflower.entity.CornflowerEntities;
import io.github.cornflower.entity.FeyEntity;
import io.github.cornflower.entity.FeyType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;

import java.util.List;

import static io.github.cornflower.group.CornflowerGroup.CORNFLOWER_GROUP;

public class CornflowerWand extends Item {

    private BlockPos blockInput;
    private BlockPos blockOutput;
    private FeyType mode = FeyType.NONE;

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
            else if (mode == FeyType.TRANSPORT_ITEM && context.getPlayer() != null && context.getWorld().getBlockEntity(context.getBlockPos()) instanceof LootableContainerBlockEntity) {
                if (context.getPlayer().isSneaking()) {
                    // Set output block
                    blockOutput = context.getBlockPos();
                    context.getPlayer().addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_output"), true);
                    return ActionResult.SUCCESS;
                }/*else {
                // Set input block, actually in chest mixin
                blockInput = context.getBlockPos();
                context.getPlayer().addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_input"), true);
                return ActionResult.SUCCESS;
                } */
            } else if (mode == FeyType.TRANSPORT_ANIMAL && context.getPlayer() != null) {
                if (context.getPlayer().isSneaking()) {
                    // Set output block
                    blockOutput = context.getBlockPos();
                    context.getPlayer().addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_output"), true);
                    return ActionResult.SUCCESS;
                } else {
                    // Set input block, actually in chest mixin
                    blockInput = context.getBlockPos();
                    context.getPlayer().addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_input"), true);
                    return ActionResult.SUCCESS;
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

            feyEntity.setInputBlock(this.blockInput);
            feyEntity.setOutputBlock(this.blockOutput);
            feyEntity.setType(mode);

            user.addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_fey"), true);

            return true;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    // Right clicking
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Change modes with mode click
        if (KeyBinds.wandModeKey.isPressed()) {
            // Shift returns the next mode
            mode = mode.shift();
            // Print out what mode was just set
            if (mode == FeyType.TRANSPORT_ITEM)
                user.addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_mode_item"), true);
            else if (mode == FeyType.TRANSPORT_ANIMAL)
                user.addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_mode_animal"), true);
            else
                user.addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_mode_none"), true);

            // Clear input/output blocks
            blockInput = null;
            blockOutput = null;

            return TypedActionResult.success(user.getStackInHand(hand));
        } else return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        // Mode tooltip
        if (mode == FeyType.TRANSPORT_ITEM)
            tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip_mode_item"));
        else if (mode == FeyType.TRANSPORT_ANIMAL)
            tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip_mode_animal"));
        else
            tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip_mode_none"));
        // Input / Output tooltip
        if (blockInput == null) tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip_input"));
        else
            tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip_input").append(blockInput.toShortString()));
        if (blockOutput == null) tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip_output"));
        else
            tooltip.add(new TranslatableText("item.cornflower.wand_cornflower.tooltip_output").append(blockOutput.toShortString()));
    }

    public void setBlockInput(BlockPos blockInput) {
        this.blockInput = blockInput;
    }

}
