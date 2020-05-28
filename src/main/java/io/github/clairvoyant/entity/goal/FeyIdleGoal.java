/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.entity.goal;

import io.github.clairvoyant.entity.FeyEntity;
import net.minecraft.entity.ai.goal.Goal;

public class FeyIdleGoal extends Goal implements defaultWeight{

    private FeyEntity feyEntity;
    public FeyIdleGoal(FeyEntity feyEntity) {
        this.feyEntity = feyEntity;
    }

    public int getDefaultWeight() { return 3; }

    @Override
    public boolean canStart() {
        return this.feyEntity.getGoalSelector().getRunningGoals().count() == 0;
    }

    @Override
    public boolean shouldContinue() {
        return this.feyEntity.getGoalSelector().getRunningGoals().count() == 1;
    }

}
