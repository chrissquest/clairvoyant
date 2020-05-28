/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.block.entity;

import io.github.clairvoyant.block.ClairvoyantBlocks;
import io.github.clairvoyant.util.ModConstants;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ClairvoyantBlockEntities {

    public static final BlockEntityType<CornflowerCauldronBlockEntity> CORNFLOWER_CAULDRON = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(ModConstants.MOD_ID, "clairvoyant_cauldron"), BlockEntityType.Builder.create(CornflowerCauldronBlockEntity::new, ClairvoyantBlocks.CORNFLOWER_CAULDRON).build(null));

    public static void init() {}
}
