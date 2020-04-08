/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.recipe;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;

public class CornflowerRecipes {
    public static final RecipeSerializer<CauldronRecipe> CAULDRON_RECIPE = RecipeSerializer.register("cornflower:cauldron", new CauldronRecipe.Serializer());
    public static final RecipeType<CauldronRecipe> CAULDRON_RECIPE_TYPE = RecipeType.register("cornflower:cauldron");

    public static void init() {}

}
