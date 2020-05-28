/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.item;

import io.github.clairvoyant.group.ClairvoyantGroup;
import io.github.clairvoyant.util.ModConstants;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ClairvoyantItems {

    public static final Item CORNFLOWER_WAND = Registry.register(Registry.ITEM, new Identifier(ModConstants.MOD_ID, "wand_cornflower"), new ClairvoyantWand());
    public static final Item CORNFLOWER_ESSENCE = Registry.register(Registry.ITEM, new Identifier(ModConstants.MOD_ID, "cornflower_essence"), new Item(new Item.Settings().group(ClairvoyantGroup.CORNFLOWER_GROUP)));
    public static final Item BOTTLED_ESSENCE = Registry.register(Registry.ITEM, new Identifier(ModConstants.MOD_ID, "bottled_essence"), new Item(new Item.Settings().group(ClairvoyantGroup.CORNFLOWER_GROUP)));

    public static void init() {}
}
