/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower;


import io.github.cornflower.block.CornflowerBlocks;
import io.github.cornflower.block.entity.CornflowerBlockEntities;
import io.github.cornflower.util.ModBootstrap;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cornflower implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[Cornflower] Starting Initialization.");
        ModBootstrap.bootstrap(); // init things like blocks and items.
        LOGGER.info("[Cornflower] Finished Initialization. (Took {}ms)", System.currentTimeMillis()-startTime);
    }
}
