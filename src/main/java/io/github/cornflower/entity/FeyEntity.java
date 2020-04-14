/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.entity;

import com.mojang.datafixers.types.templates.TypeTemplate;
import io.github.cornflower.Cornflower;
import io.github.cornflower.entity.goal.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.control.JumpControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.Tag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class FeyEntity extends MobEntityWithAi implements Flutterer {
    private static final TrackedData<Byte> FEY_FLAGS;
    // Type of Fey
    private FeyType type = FeyType.NONE;

    /**
     * The size of the inventory of the item transport fey.
     * TODO: Make this modifiable by the user?
     */
    private static final int inventorySize = 1;

    private BlockPos inputBlock;
    private BlockPos outputBlock;
    // TODO: Persist items in NBT data. See DonkeyEntity.
    private BasicInventory items;
    private List<Goal> Goals = new ArrayList<>();

    public FeyEntity(EntityType<? extends FeyEntity> entityType, World world) {
        super(entityType, world);

        this.moveControl = new FlightMoveControl(this, 20, true);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(FEY_FLAGS, (byte) 0);

        items = new BasicInventory(inventorySize);
    }

    @Override
    protected void initGoals() {
        // Because I guess it's not possible to remove all goals
        if (Goals != null) {
            for (Goal goal : Goals) {
                goalSelector.remove(goal);
            }

            if (type == FeyType.TRANSPORT_ITEM) {
                Goals.add(new FeyIdleGoal(this));
                Goals.add(new FeyMoveItemGoal(this));
                Goals.add(new FeyCollectItemGoal(this));
                Goals.add(new FeyDepositItemGoal(this));
            } else if (type == FeyType.TRANSPORT_ANIMAL) {
                Goals.add(new FeyIdleGoal(this));
                Goals.add(new FeyMoveAnimalGoal(this));
                Goals.add(new FeyCollectAnimalGoal(this));
                Goals.add(new FeyDepositAnimalGoal(this));
            }

            for (Goal goal : Goals) {
                if (goal instanceof defaultWeight)
                    goalSelector.add(((defaultWeight) goal).getDefaultWeight(), goal);
            }
        }
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
                super.tick();
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
            Cornflower.LOGGER.info("Enumtostring : " + type.toString() + "  Enumtoname : " + type.name() + "  Stringtoenum : " + FeyType.valueOf("NONE"));
            return super.damage(source, amount);
        }
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();

        for (int i = 0; i < items.getInvSize(); i++) {
            ItemStack stack = items.getInvStack(i);
            if (stack != null) {
                ItemEntity itemEntity = new ItemEntity(this.world, this.getX(), this.getY(), this.getZ(), stack);

                float f = this.random.nextFloat() * 0.5F;
                float g = this.random.nextFloat() * 6.2831855F;
                itemEntity.setVelocity((-MathHelper.sin(g) * f), 0.20000000298023224D, (MathHelper.cos(g) * f));

                items.removeInvStack(i);
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        this.dataTracker.set(FEY_FLAGS, tag.getByte("FeyFlags"));

        if (tag.contains("Type")) {
            this.type = FeyType.getType(tag.get("Type").asString());
            // Need to re init goals because the entity loads those before nbt
            initGoals();
        }

        if (tag.contains("InputContainer")) {
            this.inputBlock = NbtHelper.toBlockPos(tag.getCompound("InputContainer"));
        }

        if (tag.contains("OutputContainer")) {
            this.outputBlock = NbtHelper.toBlockPos(tag.getCompound("OutputContainer"));
        }
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putByte("FeyFlags", this.dataTracker.get(FEY_FLAGS));

        if (type != null) {
            tag.putString("Type", type.toString());
        }

        if (inputBlock != null) {
            tag.put("InputContainer", NbtHelper.fromBlockPos(inputBlock));
        }

        if (outputBlock != null) {
            tag.put("OutputContainer", NbtHelper.fromBlockPos(outputBlock));
        }
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

    public void setType(FeyType type) {
        this.type = type;
        // Need to re init goals if the type changes
        initGoals();
    }

    static {
        FEY_FLAGS = DataTracker.registerData(FeyEntity.class, TrackedDataHandlerRegistry.BYTE);
    }

}
