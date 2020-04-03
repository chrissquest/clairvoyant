/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.block;


import io.github.cornflower.block.entity.CornflowerCauldronBlockEntity;
import io.github.cornflower.util.CampfireUtil;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class CornflowerCauldronBlock extends CauldronBlock implements BlockEntityProvider {

    public CornflowerCauldronBlock() {
        super(FabricBlockSettings.copy(Blocks.CAULDRON).materialColor(DyeColor.LIGHT_BLUE).build());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new CornflowerCauldronBlockEntity();
    }

    public static void spawnBubbleParticles(World world, BlockPos pos, double waterLevel) {
        Random rand = new Random();
        double pixel = 0.0625d; // one pixel in block pos calculations if the block is 16x16x16
        world.addParticle(ParticleTypes.BUBBLE_POP,
                (double)pos.getX() + 0.5D + rand.nextDouble() / 4.0D * (double)(rand.nextBoolean() ? 1 : -1),
                (double)pos.getY() + (4*pixel) + ((waterLevel*3+2)*pixel),
                (double)pos.getZ() + 0.5D + rand.nextDouble() / 4.0D * (double)(rand.nextBoolean() ? 1 : -1),
                0.0D,
                0.005D,
                0.0D);
        BlockEntity be = world.getBlockEntity(pos);
        if(be != null) {
            if(be instanceof CornflowerCauldronBlockEntity) {
                if(((CornflowerCauldronBlockEntity) be).getCraftingStage() == CornflowerCauldronBlockEntity.CraftingStage.CRAFTING) {
                    world.addParticle(ParticleTypes.END_ROD,
                            (double)pos.getX() + 0.5D + rand.nextDouble() / 4.0D * (double)(rand.nextBoolean() ? 1 : -1),
                            (double)pos.getY() + (4*pixel) + ((waterLevel*3+2)*pixel),
                            (double)pos.getZ() + 0.5D + rand.nextDouble() / 4.0D * (double)(rand.nextBoolean() ? 1 : -1),
                            (rand.nextBoolean() ? 1 : -1) * 0.05d,
                            (rand.nextBoolean() ? 1 : -1) * 0.05d,
                            (rand.nextBoolean() ? 1 : -1) * 0.05d
                    );
                }
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(world.isClient) return super.onUse(state, world, pos, player, hand, hit);
        ItemStack stack = player.getStackInHand(hand);
        if(!stack.isEmpty()) {
            if(stack.getItem() == Items.WATER_BUCKET || stack.getItem() == Items.BUCKET || stack.getItem() == Items.GLASS_BOTTLE) {
                return super.onUse(state, world, pos, player, hand, hit);
            }
            if(world.getBlockEntity(pos) instanceof CornflowerCauldronBlockEntity) {
                CornflowerCauldronBlockEntity cauldron = (CornflowerCauldronBlockEntity) world.getBlockEntity(pos);
                if((cauldron != null ? cauldron.getCraftingStage() : CornflowerCauldronBlockEntity.CraftingStage.NONE) == CornflowerCauldronBlockEntity.CraftingStage.NONE) {

                }
            }
        }



        return super.onUse(state, world, pos, player, hand, hit);
        /*
        if(world.isClient) return super.onUse(state, world, pos, player, hand, hit);
        ItemStack itemStack = player.getStackInHand(hand);
        if(!itemStack.isEmpty()) {
            if(itemStack.getItem() instanceof DyeItem) {
                BlockEntity cauldron = world.getBlockEntity(pos);
                if(cauldron instanceof CornflowerCauldronBlockEntity) {
                    if(((CornflowerCauldronBlockEntity) cauldron).getCraftingStage() == CornflowerCauldronBlockEntity.CraftingStage.NONE) {
                        ((CornflowerCauldronBlockEntity) cauldron).setCraftingStage(CornflowerCauldronBlockEntity.CraftingStage.CRAFTING);
                        ItemStack s = itemStack.copy();
                        s.setCount(1);
                        ((CornflowerCauldronBlockEntity)cauldron).addItem(s);
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
        */
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if(world.getBlockState(pos).get(CornflowerCauldronBlock.LEVEL) > 0) {
            if(CampfireUtil.isCampfireLitUnder(world, pos)) {
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