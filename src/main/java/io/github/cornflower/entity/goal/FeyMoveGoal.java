/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.entity.goal;

import io.github.cornflower.entity.FeyEntity;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldView;

public class FeyMoveGoal extends Goal {

    /**
     * The maximum distance this fey will move to get to the input or output blocks.
     * TODO: Make this modifiable by the user?
     */
    private static final int maxMoveDistance = 64;

    private FeyEntity feyEntity;

    private BlockPos targetPos;

    public FeyMoveGoal(FeyEntity entity) {
        this.feyEntity = entity;
    }

    @Override
    public boolean canStart() {
        if(feyEntity.getInputBlock() != null
                && feyEntity.getInputBlock().isWithinDistance(feyEntity.getPos(), maxMoveDistance)
                && !feyEntity.hasItems()) {
            // Fey can move to input block
            this.targetPos = feyEntity.getInputBlock();
            return true;
        }

        if(feyEntity.getOutputBlock() != null
                && feyEntity.getOutputBlock().isWithinDistance(feyEntity.getPos(), maxMoveDistance)
                && feyEntity.hasItems()) {
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
        if(feyEntity.getNavigation().isIdle()) {
            feyEntity.getNavigation().startMovingAlong(feyEntity.getNavigation().findPathTo(this.targetPos, 2), 1.0d);
        }
    }

    @Override
    public void stop() {
        feyEntity.getNavigation().stop();
    }
}
