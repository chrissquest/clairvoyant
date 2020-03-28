package io.github.cornflower.entity;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.Entity;

@Environment(EnvType.CLIENT)
public class FeyEntityModel<T extends Entity> extends CompositeEntityModel<T> {

    private final ModelPart head;

    public FeyEntityModel() {
        this.textureWidth = 24;
        this.textureHeight = 12;

        this.head = new ModelPart(this, 0, 0);
        this.head.addCuboid(-3.0F, -6.0F, -3.0F, 6, 6, 6);
        this.head.addCuboid(3.0F, -3.0F, -1.0F, 1, 1, 2);
        this.head.addCuboid(-4.0F, -3.0F, -1.0F, 1, 1, 2);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float customAngle, float headYaw, float headPitch) {
        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.head);
    }
}
