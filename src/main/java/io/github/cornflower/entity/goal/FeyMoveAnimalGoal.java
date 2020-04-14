/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.entity.goal;

import io.github.cornflower.entity.FeyEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;

public class FeyMoveAnimalGoal extends Goal implements defaultWeight{

    private static final int maxMoveDistance = 64;

    private FeyEntity feyEntity;

    private BlockPos targetPos;

    public FeyMoveAnimalGoal(FeyEntity entity) {
        this.feyEntity = entity;
    }

    public int getDefaultWeight() { return 2; }

    public boolean canStart() {
        if (feyEntity.getInputBlock() != null
                && feyEntity.getInputBlock().isWithinDistance(feyEntity.getPos(), maxMoveDistance)
                && !feyEntity.hasPassengers()) {
            // Fey can move to input block
            this.targetPos = feyEntity.getInputBlock();
            return true;
        }

        if (feyEntity.getOutputBlock() != null
                && feyEntity.getOutputBlock().isWithinDistance(feyEntity.getPos(), maxMoveDistance)
                && feyEntity.hasPassengers()) {
            // Fey can move to output block
            this.targetPos = feyEntity.getOutputBlock();
            return true;
        }

        return false;
    }

    @Override
    public boolean shouldContinue() {
        return this.canStart();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void tick() {
        if (feyEntity.getNavigation().isIdle()) {
            feyEntity.getNavigation().startMovingAlong(feyEntity.getNavigation().findPathTo(this.targetPos, 2), 1.0d);
        }
    }

    @Override
    public void stop() {
        feyEntity.getNavigation().stop();
    }
}
