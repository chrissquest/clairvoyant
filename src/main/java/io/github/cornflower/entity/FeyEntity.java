package io.github.cornflower.entity;

import io.github.cornflower.entity.goal.FeyCollectGoal;
import io.github.cornflower.entity.goal.FeyDepositGoal;
import io.github.cornflower.entity.goal.FeyIdleGoal;
import io.github.cornflower.entity.goal.FeyMoveGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.JumpControl;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FeyEntity extends MobEntityWithAi implements Flutterer {
    private static final TrackedData<Byte> FEY_FLAGS;

    // Item transport Fey

    /**
     * The size of the inventory of the item transport fey.
     * TODO: Make this modifiable by the user?
     */
    private static final int inventorySize = 1;

    private BlockPos inputBlock;
    private BlockPos outputBlock;
    // TODO: Persist items in NBT data. See DonkeyEntity.
    private BasicInventory items;

    public FeyEntity(EntityType<? extends FeyEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FEY_FLAGS, (byte) 0);

        items = new BasicInventory(inventorySize);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(3, new FeyIdleGoal(this));
        this.goalSelector.add(2, new FeyMoveGoal(this));
        this.goalSelector.add(1, new FeyCollectGoal(this));
        this.goalSelector.add(1, new FeyDepositGoal(this));
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.getAttributes().register(EntityAttributes.FLYING_SPEED);

        this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(6.0D);
        this.getAttributeInstance(EntityAttributes.FLYING_SPEED).setBaseValue(0.6000000238418579D);
        this.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
    }

    @Override
    protected float getSoundVolume() {
        return 0.1F;
    }

    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.95F;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_BAT_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void pushAway(Entity entity) {
    }

    @Override
    protected void tickCramming() {
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world) {
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }

            public void tick() {
                //if (!BeeEntity.this.pollinateGoal.isRunning()) {
                    super.tick();
                //}
            }
        };
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    public JumpControl getJumpControl() {
        return new JumpControl(this) {
            @Override
            public void tick() {
                // NO JUMPING ALLOWED
            }
        };
    }

    @Override
    public void tick() {
        super.tick();
        //this.setVelocity(this.getVelocity().multiply(1.0D, 0.6D, 1.0D));
    }

    @Override
    protected boolean canClimb() {
        return false;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
    }

    @Override
    protected boolean hasWings() {
        return true;
    }

    @Override
    public boolean canAvoidTraps() {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            return super.damage(source, amount);
        }
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();

        for(int i = 0; i < items.getInvSize(); i++) {
            ItemStack stack = items.getInvStack(i);
            if (stack != null) {
                ItemEntity itemEntity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), stack);

                float f = this.random.nextFloat() * 0.5F;
                float g = this.random.nextFloat() * 6.2831855F;
                itemEntity.setVelocity((double)(-MathHelper.sin(g) * f), 0.20000000298023224D, (double)(MathHelper.cos(g) * f));

                items.removeInvStack(i);
            }
        }
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.dataTracker.set(FEY_FLAGS, tag.getByte("FeyFlags"));
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putByte("FeyFlags", this.dataTracker.get(FEY_FLAGS));
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height / 2.0F;
    }

    public GoalSelector getGoalSelector() {
        return this.goalSelector;
    }

    public boolean hasItems() {
        return !items.isInvEmpty();
    }

    public Inventory getItems() {
        return items;
    }

    public BlockPos getInputBlock() {
        return inputBlock;
    }

    public void setInputBlock(BlockPos inputBlock) {
        this.inputBlock = inputBlock;
    }

    public BlockPos getOutputBlock() {
        return outputBlock;
    }

    public void setOutputBlock(BlockPos outputBlock) {
        this.outputBlock = outputBlock;
    }

    static {
        FEY_FLAGS = DataTracker.registerData(FeyEntity.class, TrackedDataHandlerRegistry.BYTE);
    }
}
