/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.block.entity;

import io.github.cornflower.block.CornflowerBlocks;
import io.github.cornflower.util.ModConstants;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CornflowerBlockEntities {

    public static final BlockEntityType<CornflowerCauldronBlockEntity> CORNFLOWER_CAULDRON = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(ModConstants.MOD_ID, "cornflower_cauldron"), BlockEntityType.Builder.create(CornflowerCauldronBlockEntity::new, CornflowerBlocks.CORNFLOWER_CAULDRON).build(null));

    public static void init() {}
}
