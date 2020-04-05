/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower;


import io.github.cornflower.util.ModBootstrap;
import io.github.cornflower.util.ModConstants;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.util.sat4j.minisat.core.DataStructureFactory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cornflower implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[Cornflower] Starting initialization.");
        ModBootstrap.bootstrap(); // init things like blocks and items.
        LOGGER.info("[Cornflower] Finished initialization. (Took {}ms)", System.currentTimeMillis()-startTime);
    }
}
