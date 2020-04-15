/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.entity.goal;

import io.github.cornflower.entity.FeyEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class FeyDepositAnimalGoal extends Goal implements defaultWeight{

    public static final int depositDistance = 1;

    private FeyEntity feyEntity;

    public FeyDepositAnimalGoal(FeyEntity entity) {
        this.feyEntity = entity;
    }

    public int getDefaultWeight() { return 1; }

    public boolean canStart() {
        if (feyEntity.getInputBlock() == null) {
            return false;
        }
        if (!feyEntity.hasPassengers()) {
            return false;
        }
        return feyEntity.getOutputBlock().isWithinDistance(feyEntity.getPos(), depositDistance);
    }

    public boolean shouldContinue() {
        // TODO: Double check how this works, debug when shouldContinue runs, and is it running correctly
        // The fey often gets confused when depositing an animal, not sure why exactly
        // Continue until fey no longer has animals.
        return feyEntity.hasPassengers() && feyEntity.getOutputBlock() != null;
    }

    public void tick() {
        // Move riding animal to ground
        if (feyEntity.getOutputBlock() != null) {
            // If there is a riding animal
            if(feyEntity.getPassengerList() != null && !feyEntity.getPassengerList().isEmpty()) {
                // Get riding animal
                LivingEntity depositAnimal = (LivingEntity) feyEntity.getPassengerList().get(0);
                if (depositAnimal != null) {

                    // Dismount the riding mob
                    feyEntity.removeAllPassengers();

                    // Move back to input location
                    if (feyEntity.getInputBlock() != null) {
                        feyEntity.setPositionTarget(feyEntity.getInputBlock(), 0);
                    }
                }
            }
        }
    }
}
