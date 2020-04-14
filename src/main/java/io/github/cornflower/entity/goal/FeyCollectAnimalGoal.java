/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.entity.goal;

import io.github.cornflower.Cornflower;
import io.github.cornflower.entity.FeyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.List;

public class FeyCollectAnimalGoal extends Goal implements defaultWeight{

    public static final int pickupDistance = 2;

    private FeyEntity feyEntity;

    public FeyCollectAnimalGoal(FeyEntity entity) {
        this.feyEntity = entity;
    }

    public int getDefaultWeight() { return 1; }

    public boolean canStart() {
        if (feyEntity.getInputBlock() == null) {
            return false;
        }
        if (feyEntity.hasPassengers()) {
            return false;
        }
        return feyEntity.getInputBlock().isWithinDistance(feyEntity.getPos(), pickupDistance);
    }

    public boolean shouldContinue() {
        if (feyEntity.getInputBlock() == null) {
            return false;
        }
        // Continue until the fey has any animal.
        return !feyEntity.hasPassengers();
    }

    public void tick() {
        // Move animal to riding fey
        if (feyEntity.getInputBlock() != null) {
            // Get the nearest mob
            BlockPos in = feyEntity.getInputBlock();
            BlockBox mobInArea = BlockBox.create(in.getX() - pickupDistance, in.getY() - pickupDistance, in.getZ() - pickupDistance, in.getX() + pickupDistance, in.getY() + pickupDistance, in.getZ() + pickupDistance);
            // Animal to grab                           get closest                from list          that is type         boundry             ?                       ?                that is not itself     nearest to input
            LivingEntity targetAnimal = feyEntity.world.getClosestEntity(feyEntity.world.getEntities(LivingEntity.class, Box.from(mobInArea), null), TargetPredicate.DEFAULT, feyEntity, in.getX(), in.getY(), in.getZ());
            // If there is an animal to grab
            if (targetAnimal != null) {
                // Check if there are animals
                if (feyEntity.getPassengerList() != null && !feyEntity.getPassengerList().isEmpty()) {
                    // If there are animals, stop
                    return;
                }

                // If theres no animals, that means there's room
                // Make the animal ride the fey
                targetAnimal.startRiding(feyEntity);

                // Move to output location
                if (feyEntity.getOutputBlock() != null) {
                    feyEntity.setPositionTarget(feyEntity.getOutputBlock(), 0);
                }
            }
        }
    }
}
