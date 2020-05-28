/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant;


import io.github.clairvoyant.util.ModBootstrap;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Clairvoyant implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[Clairvoyant] Starting initialization.");
        ModBootstrap.bootstrap(); // init things like blocks and items.
        LOGGER.info("[Clairvoyant] Finished initialization. (Took {}ms)", System.currentTimeMillis() - startTime);
    }
}
