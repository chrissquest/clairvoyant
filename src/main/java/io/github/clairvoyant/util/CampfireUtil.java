/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.util;

import io.github.clairvoyant.mixin.CampfireBlockAccessor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CampfireUtil {

    public static boolean isCampfireLitUnder(World world, BlockPos pos) {
        BlockEntity campfire = world.getBlockEntity(pos.down(1));
        if (campfire == null) return false;
        if (!campfire.getType().equals(BlockEntityType.CAMPFIRE)) return false;
        return CampfireBlockAccessor.callIsLitCampfire(world.getBlockState(pos.down(1)));
    }
}
