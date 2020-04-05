/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.entity;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;


// Made with Blockbench

@Environment(EnvType.CLIENT)
public class FeyEntityModel extends CompositeEntityModel<FeyEntity> {

    private final ModelPart head;
    private final ModelPart rightHand;
    private final ModelPart leftHand;

    public FeyEntityModel() {
        this.textureWidth = 24;
        this.textureHeight = 12;

        this.head = new ModelPart(this, 0, 0);
        this.head.addCuboid(-3.0F, -6.0F, -3.0F, 6, 6, 6);

        this.rightHand = new ModelPart(this, 0, 0);
        this.rightHand.addCuboid(-4.0F, -3.0F, -1.0F, 1, 1, 2);

        this.leftHand = new ModelPart(this, 0, 0);
        this.leftHand.addCuboid(3.0F, -3.0F, -1.0F, 1, 1, 2);
    }

    @Override
    public void setAngles(FeyEntity entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
        this.head.pitch = headPitch * 0.017453292F;
        this.head.yaw = 3.1415927F - headYaw * 0.017453292F;
        this.head.roll = 3.1415927F;

        this.head.setPivot(0.0F, 0.0F, 0.0F);

        this.leftHand.setPivot(0.0F, 5.5F, 0.0F);
        this.rightHand.setPivot(0.0F, 5.5F, 0.0F);
    }

    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.head, this.rightHand, this.leftHand);
    }
}
