/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.entity.goal;

import io.github.cornflower.entity.FeyEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class FeyDepositGoal extends Goal {

    /**
     * The distance the Fey can access inventories from.
     * TODO: Make this modifiable by the user?
     */
    public static final int depositDistance = 2;

    private FeyEntity feyEntity;

    public FeyDepositGoal(FeyEntity entity) {
        this.feyEntity = entity;
    }

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
                for (int i = 0; i < targetInventory.getInvSize(); i++) {
                    if (targetInventory.getInvStack(i).isEmpty()) {
                        targetSlot = i;
                        break;
                    }
                }

                if (targetSlot == -1) {
                    return;
                }

                // Check if fey has item stack to give.
                int feySlot = -1;
                for (int i = 0; i < feyInventory.getInvSize(); i++) {
                    if (!feyInventory.getInvStack(i).isEmpty()) {
                        feySlot = i;
                        break;
                    }
                }

                if (feySlot == -1) {
                    return;
                }

                // Move a stack from the fey inventory to the target inventory
                ItemStack stack = feyInventory.getInvStack(feySlot);
                feyInventory.removeInvStack(feySlot);
                targetInventory.setInvStack(targetSlot, stack);

                // Now, set target back to input inventory
                if (feyEntity.getInputBlock() != null) {
                    feyEntity.setPositionTarget(feyEntity.getInputBlock(), 0);
                }
            }
        }
    }
}
