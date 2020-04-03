package io.github.cornflower.entity.goal;

import io.github.cornflower.entity.FeyEntity;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.MobEntityWithAi;
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
        this.cooldown = 0;
        this.feyEntity = entity;
    }

    @Override
    protected int getInterval(MobEntityWithAi mob) {
        return 0;
    }

    @Override
    public boolean canStart() {
        if(feyEntity.getInputBlock() != null
                && feyEntity.getInputBlock().isWithinDistance(feyEntity.getPos(), maxMoveDistance)
                && !feyEntity.hasItems()) {
            // Fey can move to input block
            this.targetPos = feyEntity.getInputBlock();
            return super.canStart();
        }

        if(feyEntity.getOutputBlock() != null
                && feyEntity.getOutputBlock().isWithinDistance(feyEntity.getPos(), maxMoveDistance)
                && feyEntity.hasItems()) {
            // Fey can move to output block
            this.targetPos = feyEntity.getOutputBlock();
            return super.canStart();
        }

        return false;
    }

    @Override
    public boolean shouldContinue() {
        if(targetPos == feyEntity.getInputBlock()
                && feyEntity.getInputBlock().isWithinDistance(feyEntity.getPos(), FeyCollectGoal.pickupDistance)) {
            return false;
        }
        if(targetPos == feyEntity.getOutputBlock()
                &&feyEntity.getOutputBlock().isWithinDistance(feyEntity.getPos(), FeyDepositGoal.depositDistance)) {
            return false;
        }
        return super.shouldContinue();
    }

    @Override
    protected boolean findTargetPos() {
        if(feyEntity.hasItems()) {
            if(feyEntity.getOutputBlock() != null && feyEntity.getOutputBlock().isWithinDistance(feyEntity.getPos(), maxMoveDistance)) {
                this.targetPos = feyEntity.getOutputBlock();
                return true;
            }
        } else {
            if(feyEntity.getInputBlock() != null && feyEntity.getInputBlock().isWithinDistance(feyEntity.getPos(), maxMoveDistance)) {
                this.targetPos = feyEntity.getInputBlock();
                return true;
            }
        }
        return false;
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
