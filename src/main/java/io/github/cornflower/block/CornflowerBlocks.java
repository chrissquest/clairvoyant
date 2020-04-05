/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.block;

import io.github.cornflower.util.ModConstants;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static io.github.cornflower.group.CornflowerGroup.CORNFLOWER_GROUP;

public class CornflowerBlocks {

    public static final CornflowerCauldronBlock CORNFLOWER_CAULDRON = (CornflowerCauldronBlock) registerBlock(new CornflowerCauldronBlock(), "cornflower_cauldron");
    public static final TimewornBricks TIMEWORN_BRICK = (TimewornBricks) registerBlock(new TimewornBricks(), "timeworn_brick");
    public static final TimewornBrickSlab TIMEWORN_BRICK_SLAB = (TimewornBrickSlab) registerBlock(new TimewornBrickSlab(), "timeworn_brick_slab");
    public static final TimewornBrickStairs TIMEWORN_BRICK_STAIRS = (TimewornBrickStairs) registerBlock(new TimewornBrickStairs(), "timeworn_brick_stairs");
    public static final TimewornBrickWall TIMEWORN_BRICK_WALL = (TimewornBrickWall) registerBlock(new TimewornBrickWall(), "timeworn_brick_wall");
    public static final BottledFeyBlock BOTTLED_FEY = (BottledFeyBlock)registerBlock(new BottledFeyBlock(), "bottled_fey");


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
