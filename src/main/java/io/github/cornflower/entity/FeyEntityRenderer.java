package io.github.cornflower.entity;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.BatEntityModel;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.util.Identifier;

public class FeyEntityRenderer extends MobEntityRenderer<FeyEntity, FeyEntityModel<FeyEntity>>{

    public FeyEntityRenderer(EntityRenderDispatcher entityRenderDispatcher_1)
    {
        super(entityRenderDispatcher_1, new FeyEntityModel<>(), 0.25F);
    }

    @Override
    public Identifier getTexture(FeyEntity entity) {
        return new Identifier("cornflower:textures/entities/fey.png");
    }
}
