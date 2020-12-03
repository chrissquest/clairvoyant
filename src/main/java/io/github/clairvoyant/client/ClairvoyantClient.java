/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.client;


import io.github.clairvoyant.Clairvoyant;
import io.github.clairvoyant.block.ClairvoyantBlocks;
import io.github.clairvoyant.block.entity.CornflowerCauldronBlockEntity;
import io.github.clairvoyant.entity.ClairvoyantEntities;
import io.github.clairvoyant.entity.FeyEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class ClairvoyantClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        long startTime = System.currentTimeMillis();
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
            if (view != null) {
                BlockEntity be = view.getBlockEntity(pos);
                if (be instanceof CornflowerCauldronBlockEntity) {
                    switch (((CornflowerCauldronBlockEntity) be).getCraftingStage()) {
                        case CRAFTING:
                            return MaterialColor.MAGENTA.color;
                        case DONE:
                            return MaterialColor.LIME.color;
                        default:
                            break;
                    }
                }
            }
            return view != null && pos != null ? BiomeColors.getWaterColor(view, pos) : -1;
        }, ClairvoyantBlocks.CORNFLOWER_CAULDRON);

        EntityRendererRegistry.INSTANCE.register(ClairvoyantEntities.FEY, (entityRenderDispatcher, context) -> new FeyEntityRenderer(entityRenderDispatcher));

        BlockRenderLayerMap.INSTANCE.putBlock(ClairvoyantBlocks.BOTTLED_FEY, RenderLayer.getCutout());

        Clairvoyant.LOGGER.info("[Cornflower] Client initialization complete. (Took {}ms)", System.currentTimeMillis() - startTime);
    }
}
