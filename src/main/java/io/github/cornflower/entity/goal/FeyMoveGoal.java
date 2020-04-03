package io.github.cornflower.entity.goal;

import io.github.cornflower.entity.FeyEntity;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class FeyMoveGoal extends MoveToTargetPosGoal {

    /**
     * The maximum distance this fey will move to get to the input or output blocks.
     * TODO: Make this modifiable by the user?
     */
    private static final int maxMoveDistance = 64;

    private FeyEntity feyEntity;

    public FeyMoveGoal(FeyEntity entity) {
        super(entity, 1.0, maxMoveDistance, maxMoveDistance);
        this.feyEntity = entity;
    }

    @Override
    public boolean canStart() {
        if(feyEntity.getInputBlock() == feyEntity.getPositionTarget()
                && !feyEntity.hasItems()) {
            // Fey can move to input block
            return super.canStart();
        }

        if(feyEntity.getOutputBlock() == feyEntity.getPositionTarget()
                && feyEntity.hasItems()) {
            // Fey can move to output block
            return super.canStart();
        }

        return super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue();
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        if(feyEntity.hasItems()) {
            return feyEntity.getOutputBlock() == pos;
        } else {
            return feyEntity.getInputBlock() == pos;
        }

    }
}
