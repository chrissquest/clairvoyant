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

    /**
     * TODO:
     *
     * Figure out structure spawning
     * Possibly utilize common for ores, or create unique ores
     *
     * Cotton Fly
     * Cotton Fly Wool (slowly animated)
     *
     * Cage to hold magical mobs
     * Can only carry 1 at a time, cant swap to other items unless put down
     *
     * Add an interface for magical mobs, all of them should have a certain amount of mana
     *
     *
     *
     * To be determined if todo:
     * Is it possible to make a custom item predicate?
     * Is there a tutorial for custom crafting table recipe type for wand nbt?
     * Design a node system for research?
     * Portal to Fey world?
     * Karma counter attached to the player, influencing what you can do in the mod possibly?
     *
     *
     */

    @Override
    public void onInitialize() {
        long startTime = System.currentTimeMillis();
        LOGGER.info("[Clairvoyant] Starting initialization.");
        ModBootstrap.bootstrap(); // init things like blocks and items.
        LOGGER.info("[Clairvoyant] Finished initialization. (Took {}ms)", System.currentTimeMillis() - startTime);
    }
}
