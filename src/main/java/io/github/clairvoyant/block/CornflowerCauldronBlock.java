/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.block;


import io.github.clairvoyant.block.entity.CornflowerCauldronBlockEntity;
import io.github.clairvoyant.item.Wand;
import io.github.clairvoyant.util.CampfireUtil;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class CornflowerCauldronBlock extends CauldronBlock implements BlockEntityProvider {

    public CornflowerCauldronBlock() {
        super(FabricBlockSettings.copyOf(Blocks.CAULDRON).materialColor(DyeColor.LIGHT_BLUE));
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new CornflowerCauldronBlockEntity();
    }

    public static void spawnBubbleParticles(World world, BlockPos pos, double waterLevel) {
        Random rand = new Random();
        double pixel = 0.0625d; // one pixel in block pos calculations if the block is 16x16x16
        world.addParticle(ParticleTypes.BUBBLE_POP,
                (double) pos.getX() + 0.5D + rand.nextDouble() / 4.0D * (double) (rand.nextBoolean() ? 1 : -1),
                (double) pos.getY() + (4 * pixel) + ((waterLevel * 3 + 2) * pixel),
                (double) pos.getZ() + 0.5D + rand.nextDouble() / 4.0D * (double) (rand.nextBoolean() ? 1 : -1),
                0.0D,
                0.005D,
                0.0D
        );
        BlockEntity be = world.getBlockEntity(pos);
        if (be != null) {
            if (be instanceof CornflowerCauldronBlockEntity) {
                if (((CornflowerCauldronBlockEntity) be).getCraftingStage() == CornflowerCauldronBlockEntity.CraftingStage.CRAFTING) {
                    world.addParticle(ParticleTypes.END_ROD,
                            (double) pos.getX() + 0.5D + rand.nextDouble() / 4.0D * (double) (rand.nextBoolean() ? 1 : -1),
                            (double) pos.getY() + (4 * pixel) + ((waterLevel * 3 + 2) * pixel),
                            (double) pos.getZ() + 0.5D + rand.nextDouble() / 4.0D * (double) (rand.nextBoolean() ? 1 : -1),
                            (rand.nextBoolean() ? 1 : -1) * 0.05d,
                            (rand.nextBoolean() ? 1 : -1) * 0.05d,
                            (rand.nextBoolean() ? 1 : -1) * 0.05d
                    );
                }
            }
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof Inventory) {
                ItemScatterer.spawn(world, pos, (Inventory)blockEntity);
                world.updateComparators(pos, this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // Runs twice for each hand
        if (hand == Hand.MAIN_HAND) {
            if (world.isClient) return super.onUse(state, world, pos, player, hand, hit);
            else if (!super.onUse(state, world, pos, player, hand, hit).equals(ActionResult.SUCCESS)) {
                BlockEntity be = world.getBlockEntity(pos);
                if (be instanceof CornflowerCauldronBlockEntity) {
                    CornflowerCauldronBlockEntity cauldron = (CornflowerCauldronBlockEntity) be;
                    ItemStack stack = player.getStackInHand(hand);
                    if (!stack.isEmpty() && world.getBlockState(pos).get(CornflowerCauldronBlock.LEVEL) > 0 && CampfireUtil.isCampfireLitUnder(world, pos)) {
                        if (stack.getItem() instanceof Wand) {
                            // If craftable
                            if (cauldron.getRecipeForInvContent().isPresent()) {
                                // Craft the recipe item and lower water levels
                                ItemScatterer.spawn(world, pos, new SimpleInventory(cauldron.getRecipeForInvContent().get().craft(new SimpleInventory(cauldron.getInv().toArray(new ItemStack[]{})))));
                                this.setLevel(world, pos, state, state.get(LEVEL) - 1);
                            } else {
                                // Else spill out the items
                                ItemScatterer.spawn(world, pos, cauldron.getInv());
                            }
                            cauldron.clearInv();
                            cauldron.setCraftingStage(CornflowerCauldronBlockEntity.CraftingStage.NONE);
                            return ActionResult.SUCCESS;
                            // If it's not a bucket or bottle then take it as an ingredient
                        } else if (stack.getItem() != Items.WATER_BUCKET && stack.getItem() != Items.GLASS_BOTTLE && stack.getItem() != Items.BUCKET && stack.getItem() != Items.POTION) {
                            if (!cauldron.isInvFull()) {
                                cauldron.addItem(stack.split(1));
                                // Update water color when added ingredient
                                if (cauldron.getRecipeForInvContent().isPresent())
                                    cauldron.setCraftingStage(CornflowerCauldronBlockEntity.CraftingStage.DONE);
                                else cauldron.setCraftingStage(CornflowerCauldronBlockEntity.CraftingStage.CRAFTING);
                                return ActionResult.SUCCESS;
                            }
                        }
                    } else {
                        // Remove last item in inv when right click with nothing
                        int lastItem = 0;
                        for (int i = 0; i < cauldron.getInv().size(); i++) {
                            if (!cauldron.getInv().get(i).isEmpty()) lastItem = i;
                        }
                        ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), cauldron.getInv().get(lastItem));
                        cauldron.getInv().set(lastItem, ItemStack.EMPTY);

                        // Update water color here too
                        if (cauldron.getRecipeForInvContent().isPresent())
                            cauldron.setCraftingStage(CornflowerCauldronBlockEntity.CraftingStage.DONE);
                        else if (lastItem > 0)
                            cauldron.setCraftingStage(CornflowerCauldronBlockEntity.CraftingStage.CRAFTING);
                        else cauldron.setCraftingStage(CornflowerCauldronBlockEntity.CraftingStage.NONE);
                    }

                }
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (world.getBlockState(pos).get(CornflowerCauldronBlock.LEVEL) > 0) {
            if (CampfireUtil.isCampfireLitUnder(world, pos)) {
                CornflowerCauldronBlock.spawnBubbleParticles(world, pos, world.getBlockState(pos).get(CornflowerCauldronBlock.LEVEL));
                world.playSound(pos.getX() + 0.5d,
                        pos.getY() + 0.5d,
                        pos.getZ() + 0.5d,
                        SoundEvents.BLOCK_BUBBLE_COLUMN_BUBBLE_POP,
                        SoundCategory.BLOCKS,
                        0.2F + random.nextFloat() * 0.2F,
                        0.9F + random.nextFloat() * 0.15F,
                        false
                );
            }
        }
    }
}
