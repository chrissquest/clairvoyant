package io.github.cornflower.entity.goal;

import io.github.cornflower.entity.FeyEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class FeyCollectGoal extends Goal {

    /**
     * The distance the Fey can access inventories from.
     * TODO: Make this modifiable by the user?
     */
    private static final int pickupDistance = 3;

    /**
     * If the Fey should wait until its inventory is full before leaving the input block.
     * TODO: Make this modifiable by the user?
     */
    private static final boolean shouldFillInventory = false;

    private FeyEntity feyEntity;

    public FeyCollectGoal(FeyEntity entity) {
        this.feyEntity = entity;
    }

    @Override
    public boolean canStart() {
        if(feyEntity.getInputBlock() == null) {
            return false;
        }
        if(feyEntity.hasItems()) {
            return false;
        }
        return feyEntity.getInputBlock().isWithinDistance(feyEntity.getPos(), pickupDistance);
    }

    @Override
    public boolean shouldContinue() {
        if(feyEntity.getInputBlock() == null) {
            return false;
        }

        if(shouldFillInventory) {
            // Continue until all fey slots are filled.
            Inventory feyInventory = feyEntity.getItems();

            for(int i = 0; i < feyInventory.getInvSize(); i++) {
                if(feyInventory.getInvStack(i) == null) {
                    return true;
                }
            }

            return false;
        } else {
            // Continue until the fey has any items.
            return !feyEntity.hasItems();
        }
    }

    @Override
    public void tick() {
        // Try to move stack from target inventory to fey's inventory.
        // TODO: Implement support for sided inventories
        if (feyEntity.getInputBlock() != null) {
            BlockEntity targetBlockEntity = feyEntity.world.getBlockEntity(feyEntity.getInputBlock());
            if(targetBlockEntity instanceof Inventory) {
                Inventory targetInventory = (Inventory)targetBlockEntity;
                Inventory feyInventory = feyEntity.getItems();

                // Check if target inventory is empty
                int targetSlot = -1;
                for(int i = 0; i < targetInventory.getInvSize(); i++) {
                    if(targetInventory.getInvStack(i) != null) {
                        targetSlot = i;
                        break;
                    }
                }

                if(targetSlot == -1) {
                    return;
                }

                // Check if there is an empty spot in the fey inventory
                int feySlot = -1;
                for(int i = 0; i < feyInventory.getInvSize(); i++) {
                    if(feyInventory.getInvStack(i) == null) {
                        feySlot = i;
                        break;
                    }
                }

                if(feySlot == -1) {
                    return;
                }

                // Move a stack from the target container to the fey inventory
                ItemStack stack = targetInventory.getInvStack(targetSlot);
                targetInventory.removeInvStack(targetSlot);
                feyInventory.setInvStack(feySlot, stack);

                // Now, set target back to input inventory
                if(feyEntity.getOutputBlock() != null) {
                    feyEntity.setPositionTarget(feyEntity.getOutputBlock(), 0);
                }
            }
        }
    }
}
