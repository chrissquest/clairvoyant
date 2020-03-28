/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.block;


import io.github.cornflower.block.entity.CornflowerCauldronBlockEntity;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
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
    }
}
