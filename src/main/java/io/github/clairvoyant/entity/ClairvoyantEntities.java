/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.entity;

import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ClairvoyantEntities {

    // Build the EntityType
    public static final EntityType<FeyEntity> FEY = FabricEntityTypeBuilder.<FeyEntity>create(SpawnGroup.AMBIENT, FeyEntity::new).size(EntityDimensions.fixed(6 / 16F, 6 / 16F)).build();

    public static void init() {
        // Have to register Entity Attributes, and the entity separately , because vanilla hides this fabric has a register
        FabricDefaultAttributeRegistry.register(FEY, FeyEntity.createMobAttributes());

        // Register the entities
        Registry.register(Registry.ENTITY_TYPE, new Identifier("clairvoyant", "fey"), FEY);
    }
}
