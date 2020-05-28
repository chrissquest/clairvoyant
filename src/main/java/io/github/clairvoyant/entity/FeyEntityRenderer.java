/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FeyEntityRenderer extends MobEntityRenderer<FeyEntity, FeyEntityModel> {

    public FeyEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new FeyEntityModel(), 0.25F);
    }

    public Identifier getTexture(FeyEntity entity) {
        return new Identifier("clairvoyant:textures/entities/fey.png");
    }

    protected void setupTransforms(FeyEntity feyEntity, MatrixStack matrixStack, float f, float g, float h) {
        // I don't know why I need to lower the model, why does it render so highhhh
        matrixStack.translate(0.0D, -1.125D, 0.0D);

        super.setupTransforms(feyEntity, matrixStack, f, g, h);
    }

}
