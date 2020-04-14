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
import io.github.cornflower.entity.CornflowerEntities;
import io.github.cornflower.entity.FeyEntityRenderer;
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
public class CornflowerClient implements ClientModInitializer {
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
        }, CornflowerBlocks.CORNFLOWER_CAULDRON);

        KeyBindingRegistry.INSTANCE.addCategory("Cornflower");
        KeyBindingRegistry.INSTANCE.register(KeyBinds.wandModeKey);

        EntityRendererRegistry.INSTANCE.register(CornflowerEntities.FEY, (entityRenderDispatcher, context) -> new FeyEntityRenderer(entityRenderDispatcher));

        BlockRenderLayerMap.INSTANCE.putBlock(CornflowerBlocks.BOTTLED_FEY, RenderLayer.getTranslucent());

        Cornflower.LOGGER.info("[Cornflower] Client initialization complete. (Took {}ms)", System.currentTimeMillis() - startTime);
    }
}
