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

import static io.github.clairvoyant.group.ClairvoyantGroup.CLAIRVOYANT_GROUP;

public class ClairvoyantItems {

    public static final Item CLAIRVOYANT_EYE =  Registry.register(Registry.ITEM, new Identifier(ModConstants.MOD_ID, "clairvoyant_eye"), new Item(new Item.Settings().maxCount(1)));

    public static final Item CORNFLOWER_ESSENCE = Registry.register(Registry.ITEM, new Identifier(ModConstants.MOD_ID, "cornflower_essence"), new Item(new Item.Settings().group(CLAIRVOYANT_GROUP)));
    public static final Item BOTTLED_CORNFLOWER = Registry.register(Registry.ITEM, new Identifier(ModConstants.MOD_ID, "bottled_cornflower"), new Item(new Item.Settings().group(CLAIRVOYANT_GROUP).maxCount(16)));
    //public static final Item WOODEN_CAGE = Registry.register(Registry.ITEM, new Identifier(ModConstants.MOD_ID, "wooden_cage"), new Item(new Item.Settings().group(ClairvoyantGroup.CLAIRVOYANT_GROUP)));
    public static final Item WOODEN_WAND = Registry.register(Registry.ITEM, new Identifier(ModConstants.MOD_ID, "wooden_wand"), new Wand(CLAIRVOYANT_GROUP));

    public static void init() {}
}
