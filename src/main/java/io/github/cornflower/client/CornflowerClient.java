/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.client;


import io.github.cornflower.Cornflower;
import io.github.cornflower.block.CornflowerBlocks;
import io.github.cornflower.block.entity.CornflowerCauldronBlockEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.util.DyeColor;

@Environment(EnvType.CLIENT)
public class CornflowerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        long startTime = System.currentTimeMillis();
        ColorProviderRegistry.BLOCK.register(
                (state, view, pos, tintIndex) -> {
                    if(view != null) {
                        BlockEntity be = view.getBlockEntity(pos);
                        if(be instanceof CornflowerCauldronBlockEntity) {
                            return ((CornflowerCauldronBlockEntity) be).getWaterColor();
                        }
                    }
                    return view != null && pos != null ? BiomeColors.getWaterColor(view, pos) : -1;
                },
                CornflowerBlocks.CORNFLOWER_CAULDRON
        );
        Cornflower.LOGGER.info("[Cornflower] Client initialization complete. (Took {}ms)", System.currentTimeMillis()-startTime);
    }
}
