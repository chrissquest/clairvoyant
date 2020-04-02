package io.github.cornflower.entity;

import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CornflowerEntities {

    public static final EntityType<FeyEntity> FEY = FabricEntityTypeBuilder.create(EntityCategory.AMBIENT, FeyEntity::new).size(EntityDimensions.fixed(6/16F, 6/16F)).build();

    public static void init() {
        Registry.register(Registry.ENTITY_TYPE, new Identifier("cornflower", "fey"), FEY);
    }
}
