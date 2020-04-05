/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.DyeColor;

public class TimewornBrickStairs extends StairsBlock {
    public TimewornBrickStairs() {
        super(CornflowerBlocks.TIMEWORN_BRICK.getDefaultState(), FabricBlockSettings.copy(Blocks.BRICKS).materialColor(DyeColor.GRAY).build());
    }
}
