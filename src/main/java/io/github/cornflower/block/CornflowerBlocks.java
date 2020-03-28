/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.block;

import io.github.cornflower.util.ModConstants;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CornflowerBlocks {

    public static final ItemGroup CORNFLOWER_GROUP = FabricItemGroupBuilder.create(new Identifier(ModConstants.MOD_ID, "cornflower_group"))
            .icon(Items.CORNFLOWER::getStackForRender).build();

    public static final CornflowerCauldronBlock CORNFLOWER_CAULDRON = (CornflowerCauldronBlock) registerBlock(new CornflowerCauldronBlock(), "cornflower_cauldron");

    private static Block registerBlockWithoutItem(Block block, String id) {
        return Registry.register(Registry.BLOCK, new Identifier(ModConstants.MOD_ID, id), block);
    }

    private static Block registerBlock(Block block, String id) {
        Block registered = registerBlockWithoutItem(block, id);
        Registry.register(Registry.ITEM, new Identifier(ModConstants.MOD_ID, id), new BlockItem(registered, new Item.Settings().group(CORNFLOWER_GROUP)));
        return registered;
    }

    public static void init() {}
}
