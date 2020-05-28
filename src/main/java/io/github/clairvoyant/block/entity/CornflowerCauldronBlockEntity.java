/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.block.entity;

import io.github.clairvoyant.recipe.CauldronRecipe;
import io.github.clairvoyant.recipe.ClairvoyantRecipes;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;

import java.util.Optional;

public class CornflowerCauldronBlockEntity extends BlockEntity implements BlockEntityClientSerializable {

    private DefaultedList<ItemStack> inv = DefaultedList.ofSize(5, ItemStack.EMPTY);
    private CraftingStage craftingStage = CraftingStage.NONE;

    public CornflowerCauldronBlockEntity() {
        super(ClairvoyantBlockEntities.CORNFLOWER_CAULDRON);
    }

    public CraftingStage getCraftingStage() {
        return craftingStage;
    }

    public void setCraftingStage(CraftingStage craftingStage) {
        this.craftingStage = craftingStage;
        this.updateListeners();
    }

    public void clearInv() {
        if (this.world != null && this.world.isClient) return;
        this.getInv().clear();
        this.updateListeners();
    }

    private void updateListeners() {
        if (this.getWorld() == null) return;
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
    }

    public void addItem(ItemStack stack) {
        if (!this.isInvFull()) {
            this.inv.set(this.inv.indexOf(ItemStack.EMPTY), stack);
            this.updateListeners();
        }
    }

    public DefaultedList<ItemStack> getInv() {
        return this.inv;
    }

    public boolean isInvFull() {
        for (ItemStack stack : this.inv) {
            if (stack.isEmpty()) return false;
        }
        return true;
    }

    public boolean isInvEmpty() {
        for (ItemStack stack : this.inv) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        this.setCraftingStage(CraftingStage.values()[tag.getInt("craftingStage")]);
        Inventories.fromTag(tag, this.inv);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putInt("craftingStage", this.craftingStage.ordinal());
        Inventories.toTag(tag, this.inv);
        return super.toTag(tag);
    }

    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        this.fromTag(compoundTag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        return this.toTag(compoundTag);
    }

    public Optional<CauldronRecipe> getRecipeForInvContent() {
        BasicInventory binv = new BasicInventory(inv.toArray(new ItemStack[]{}));

        if (world != null)
            return world.getRecipeManager().getFirstMatch(ClairvoyantRecipes.CAULDRON_RECIPE_TYPE, binv, world);
        else return Optional.empty();
    }

    public enum CraftingStage {
        NONE, // not crafting (no ingredients) - biome water color
        CRAFTING, // crafting (1-5 ingredients + timer) - magenta water color
        DONE, // done (finished but not collected) - lime water color
        ;
    }
}
