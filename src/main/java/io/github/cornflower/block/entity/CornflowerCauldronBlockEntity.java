/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.block.entity;

import io.github.cornflower.util.CampfireUtil;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.Tickable;

public class CornflowerCauldronBlockEntity extends BlockEntity implements Tickable {

    public CornflowerCauldronBlockEntity() {
        super(CornflowerBlockEntities.CORNFLOWER_CAULDRON);
    }

    private int waterColor = MaterialColor.MAGENTA.color;

    @Override
    public void tick() {
        if((this.world != null ? this.world.getBlockState(this.pos).get(CauldronBlock.LEVEL) : 0) > 0) {
            if(CampfireUtil.isCampfireLitUnder(this.world, this.pos)) {
                // might need this later
            }
        }
    }

    public int getWaterColor() {
        return this.waterColor;
    }

    public void setWaterColor(int waterColor) {
        this.waterColor = waterColor;
    }
}
