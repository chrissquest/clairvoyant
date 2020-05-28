/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class BottledFeyBlock extends Block {
    public BottledFeyBlock() {
        super(FabricBlockSettings.of(Material.GLASS).nonOpaque().build());
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos blockPos, EntityContext entityContext) {
        return Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 11.0D, 12.0D);
    }
}
