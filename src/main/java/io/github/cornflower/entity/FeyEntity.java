package io.github.cornflower.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FeyEntity extends AmbientEntity {
    private static final TrackedData<Byte> FEY_FLAGS;
    // Item transport Fey
    private BlockPos inputBlock;
    private BlockPos outputBlock;

    public FeyEntity(EntityType<? extends FeyEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FEY_FLAGS, (byte) 0);
    }

    protected float getSoundVolume() {
        return 0.1F;
    }

    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.95F;
    }

    public SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BAT_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

    public boolean isPushable() {
        return false;
    }

    protected void pushAway(Entity entity) {
    }

    protected void tickCramming() {
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(6.0D);
    }


    public void tick() {
        super.tick();
        this.setVelocity(this.getVelocity().multiply(1.0D, 0.6D, 1.0D));
    }

    protected void mobTick() {
        super.mobTick();
        BlockPos blockPos = new BlockPos(this);
        // Guessing this just chooses a random location to move to every mob tick and goes

        if (this.random.nextInt(30) == 0 || blockPos.isWithinDistance(this.getPos(), 2.0D)) {
            blockPos = new BlockPos(this.getX() + (double) this.random.nextInt(7) - (double) this.random.nextInt(7), this.getY() + (double) this.random.nextInt(6) - 2.0D, this.getZ() + (double) this.random.nextInt(7) - (double) this.random.nextInt(7));
        }

        double d = (double) blockPos.getX() + 0.5D - this.getX();
        double e = (double) blockPos.getY() + 0.1D - this.getY();
        double f = (double) blockPos.getZ() + 0.5D - this.getZ();
        Vec3d vec3d = this.getVelocity();
        Vec3d vec3d2 = vec3d.add((Math.signum(d) * 0.5D - vec3d.x) * 0.10000000149011612D, (Math.signum(e) * 0.699999988079071D - vec3d.y) * 0.10000000149011612D, (Math.signum(f) * 0.5D - vec3d.z) * 0.10000000149011612D);
        this.setVelocity(vec3d2);
        float g = (float) (MathHelper.atan2(vec3d2.z, vec3d2.x) * 57.2957763671875D) - 90.0F;
        float h = MathHelper.wrapDegrees(g - this.yaw);
        this.forwardSpeed = 0.5F;
        this.yaw += h;

    }

    protected boolean canClimb() {
        return false;
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }

    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }

    public boolean canAvoidTraps() {
        return true;
    }

    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            return super.damage(source, amount);
        }
    }

    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.dataTracker.set(FEY_FLAGS, tag.getByte("FeyFlags"));
    }

    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putByte("FeyFlags", this.dataTracker.get(FEY_FLAGS));
    }

    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height / 2.0F;
    }

    static {
        FEY_FLAGS = DataTracker.registerData(FeyEntity.class, TrackedDataHandlerRegistry.BYTE);
    }
}
