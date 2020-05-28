/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.world.World;


public class CauldronRecipe implements Recipe<Inventory> {

    private final Identifier id;
    private final DefaultedList<Ingredient> ingredients;
    private final ItemStack result;

    public CauldronRecipe(Identifier id, DefaultedList<Ingredient> ingredients, ItemStack result) {
        this.id = id;
        this.ingredients = ingredients;
        this.result = result;
    }

    public DefaultedList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public boolean matches(Inventory inv, World world) {

        ItemStack[] tempInv = new ItemStack[inv.getInvSize()];
        int invLength = 0;
        boolean match = true;

        for (int i = 0; i < tempInv.length; i++) tempInv[i] = inv.getInvStack(i).copy();
        for (ItemStack stack : tempInv) if (stack != null && !stack.isEmpty()) invLength++;

        if (ingredients.size() != invLength) match = false;

        for (Ingredient ingredient : ingredients) {
            boolean ingredientFound = false;
            for (ItemStack stack : tempInv) {
                if (ingredient.test(stack) && !ingredientFound) {
                    ingredientFound = true;
                    stack.decrement(1);
                }
            }
            if (!ingredientFound) match = false;
        }

        return match;
    }

    @Override
    public ItemStack craft(Inventory inv) {
        return this.result.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return this.result;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ClairvoyantRecipes.CAULDRON_RECIPE;
    }

    @Override
    public RecipeType<?> getType() {
        return ClairvoyantRecipes.CAULDRON_RECIPE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<CauldronRecipe> {

        @Override
        public CauldronRecipe read(Identifier id, JsonObject json) {
            DefaultedList<Ingredient> ingredients = getIngredients(JsonHelper.getArray(json, "items"));
            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for cauldron recipe.");
            } else if (ingredients.size() > 5) {
                throw new JsonParseException("Too many ingredients for cauldron recipe.");
            } else {
                ItemStack stack = ShapedRecipe.getItemStack(JsonHelper.getObject(json, "result"));
                return new CauldronRecipe(id, ingredients, stack);
            }
        }

        @Override
        public CauldronRecipe read(Identifier id, PacketByteBuf buf) {
            int stackCount = buf.readVarInt();
            DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(stackCount, Ingredient.EMPTY);

            for (int j = 0; j < ingredients.size(); ++j) {
                ingredients.set(j, Ingredient.fromPacket(buf));
            }

            ItemStack stack = buf.readItemStack();
            return new CauldronRecipe(id, ingredients, stack);
        }

        @Override
        public void write(PacketByteBuf buf, CauldronRecipe recipe) {
            buf.writeVarInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.ingredients) {
                ingredient.write(buf);
            }

            buf.writeItemStack(recipe.getOutput());
        }

        private static DefaultedList<Ingredient> getIngredients(JsonArray json) {
            DefaultedList<Ingredient> ingredients = DefaultedList.of();
            for (int i = 0; i < json.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(json.get(i));
                if (!ingredient.isEmpty()) {
                    ingredients.add(ingredient);
                }
            }
            return ingredients;
        }
    }
}
