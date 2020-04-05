/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.group;

import io.github.cornflower.util.ModConstants;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class CornflowerGroup {

    public static final ItemGroup CORNFLOWER_GROUP = FabricItemGroupBuilder.create(new Identifier(ModConstants.MOD_ID, "cornflower_group")).icon(() -> new ItemStack(Blocks.CORNFLOWER)).build();

    public static void init() {}
}
