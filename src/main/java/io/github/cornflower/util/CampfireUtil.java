/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.util;

import io.github.cornflower.mixin.CampfireBlockAccessor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CampfireUtil {

    public static boolean isCampfireLitUnder(World world, BlockPos pos) {
        BlockPos campfirePos = pos.down(1);
        if(world.getBlockEntity(campfirePos) == null) return false;
        if(!world.getBlockEntity(campfirePos).getType().equals(BlockEntityType.CAMPFIRE)) return false;
        if(!CampfireBlockAccessor.callIsLitCampfire(world.getBlockState(campfirePos))) return false;

        return true;
    }
}
