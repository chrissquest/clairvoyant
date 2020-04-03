package io.github.cornflower.entity.goal;

import io.github.cornflower.entity.FeyEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class FeyIdleGoal extends Goal {
    private Random random = new Random();

    private FeyEntity feyEntity;

    public FeyIdleGoal(FeyEntity feyEntity) {
        this.feyEntity = feyEntity;
    }

    @Override
    public boolean canStart() {
        return true;
    }

    /**
     * Chooses a random location around fey to target and move towards every mob tick.
     * TODO: Make this a bit more elegant.
     */
    @Override
    public void tick() {
        BlockPos blockPos = feyEntity.getBlockPos();

        if (this.random.nextInt(30) == 0 || blockPos.isWithinDistance(feyEntity.getPos(), 2.0D)) {
            blockPos = new BlockPos(feyEntity.getX() + (double) this.random.nextInt(7) - (double) this.random.nextInt(7), feyEntity.getY() + (double) this.random.nextInt(6) - 2.0D, feyEntity.getZ() + (double) this.random.nextInt(7) - (double) this.random.nextInt(7));
        }

        double d = (double) blockPos.getX() + 0.5D - feyEntity.getX();
        double e = (double) blockPos.getY() + 0.1D - feyEntity.getY();
        double f = (double) blockPos.getZ() + 0.5D - feyEntity.getZ();
        Vec3d vec3d = feyEntity.getVelocity();
        Vec3d vec3d2 = vec3d.add((Math.signum(d) * 0.5D - vec3d.x) * 0.10000000149011612D, (Math.signum(e) * 0.699999988079071D - vec3d.y) * 0.10000000149011612D, (Math.signum(f) * 0.5D - vec3d.z) * 0.10000000149011612D);
        feyEntity.setVelocity(vec3d2);
        float g = (float) (MathHelper.atan2(vec3d2.z, vec3d2.x) * 57.2957763671875D) - 90.0F;
        float h = MathHelper.wrapDegrees(g - feyEntity.yaw);
        feyEntity.forwardSpeed = 0.5F;
        feyEntity.yaw += h;
    }
}
