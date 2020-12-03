/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.util;

import io.github.clairvoyant.block.ClairvoyantBlocks;
import io.github.clairvoyant.block.entity.ClairvoyantBlockEntities;
import io.github.clairvoyant.entity.ClairvoyantEntities;
import io.github.clairvoyant.group.ClairvoyantGroup;
import io.github.clairvoyant.item.ClairvoyantItems;
import io.github.clairvoyant.recipe.ClairvoyantRecipes;
import io.github.clairvoyant.world.feature.ClairvoyantWorldFeatures;

public class ModBootstrap {

    public static void bootstrap() {
        ClairvoyantGroup.init();
        ClairvoyantItems.init();
        ClairvoyantBlocks.init();
        ClairvoyantBlockEntities.init();
        ClairvoyantEntities.init();
        ClairvoyantRecipes.init();
        //ClairvoyantWorldFeatures.init();
    }
}
