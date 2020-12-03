/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.group;

import io.github.clairvoyant.item.ClairvoyantItems;
import io.github.clairvoyant.util.ModConstants;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ClairvoyantGroup {

    public static final ItemGroup CLAIRVOYANT_GROUP = FabricItemGroupBuilder.create(new Identifier(ModConstants.MOD_ID, "clairvoyant_group")).icon(() -> new ItemStack(ClairvoyantItems.CLAIRVOYANT_EYE)).build();

    public static void init() {}
}
