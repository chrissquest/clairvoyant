/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.recipe;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;

public class ClairvoyantRecipes {
    public static final RecipeSerializer<CauldronRecipe> CAULDRON_RECIPE = RecipeSerializer.register("clairvoyant:cauldron", new CauldronRecipe.Serializer());
    public static final RecipeType<CauldronRecipe> CAULDRON_RECIPE_TYPE = RecipeType.register("clairvoyant:cauldron");

    public static void init() {}

}
