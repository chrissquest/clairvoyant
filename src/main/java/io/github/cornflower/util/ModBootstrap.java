/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.util;

import io.github.cornflower.block.CornflowerBlocks;
import io.github.cornflower.block.entity.CornflowerBlockEntities;
import io.github.cornflower.entity.CornflowerEntities;

public class ModBootstrap {

    public static void bootstrap() {
        CornflowerBlocks.init();
        CornflowerBlockEntities.init();
        CornflowerEntities.init();
    }
}
