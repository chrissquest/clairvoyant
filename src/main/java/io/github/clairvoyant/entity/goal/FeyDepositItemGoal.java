/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.entity.goal;

import io.github.clairvoyant.entity.FeyEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class FeyDepositItemGoal extends Goal implements defaultWeight{

    /**
     * The distance the Fey can access inventories from.
     * TODO: Make this modifiable by the user?
     */
    public static final int depositDistance = 2;

    private FeyEntity feyEntity;

    public FeyDepositItemGoal(FeyEntity entity) {
        this.feyEntity = entity;
    }

    public int getDefaultWeight() { return 1; }

    @Override
    public boolean canStart() {
        if (feyEntity.getInputBlock() == null) {
            return false;
        }
        if (!feyEntity.hasItems()) {
            return false;
        }
        return feyEntity.getOutputBlock().isWithinDistance(feyEntity.getPos(), depositDistance);
    }

    @Override
    public boolean shouldContinue() {
        // Continue until fey no longer has items.
        return feyEntity.hasItems() && feyEntity.getOutputBlock() != null;
    }

    @Override
    public void tick() {
        // Try to move stack from fey's inventory to target inventory.
        // TODO: Implement support for sided inventories
        if (feyEntity.getOutputBlock() != null) {
            BlockEntity targetBlockEntity = feyEntity.world.getBlockEntity(feyEntity.getOutputBlock());
            if (targetBlockEntity instanceof Inventory) {
                Inventory targetInventory = (Inventory) targetBlockEntity;
                Inventory feyInventory = feyEntity.getItems();

                // Check if target inventory is full
                int targetSlot = -1;
                for (int i = 0; i < targetInventory.size(); i++) {
                    if (targetInventory.getStack(i).isEmpty()) {
                        targetSlot = i;
                        break;
                    }
                }

                if (targetSlot == -1) {
                    return;
                }

                // Check if fey has item stack to give.
                int feySlot = -1;
                for (int i = 0; i < feyInventory.size(); i++) {
                    if (!feyInventory.getStack(i).isEmpty()) {
                        feySlot = i;
                        break;
                    }
                }

                if (feySlot == -1) {
                    return;
                }

                // Move a stack from the fey inventory to the target inventory
                ItemStack stack = feyInventory.getStack(feySlot);
                feyInventory.removeStack(feySlot);
                targetInventory.setStack(targetSlot, stack);

                // Now, set target back to input inventory
                if (feyEntity.getInputBlock() != null) {
                    feyEntity.setPositionTarget(feyEntity.getInputBlock(), 0);
                }
            }
        }
    }
}
