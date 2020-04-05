/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;

public class BottledFeyBlock extends Block {
    public BottledFeyBlock() {
        super(FabricBlockSettings.of(Material.STONE).build());
    }
}
