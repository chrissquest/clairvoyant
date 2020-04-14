/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.mixin;

import io.github.cornflower.item.CornflowerWand;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LootableContainerBlockEntity.class)
public abstract class LootableContainerBlockEntityMixin extends LockableContainerBlockEntity {

    protected LootableContainerBlockEntityMixin(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    @Inject(method = "createMenu", at = @At("HEAD"), cancellable = true)
    private void createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity playerEntity, CallbackInfoReturnable<Container> ci) {
        if (playerEntity.getMainHandStack().getItem() instanceof CornflowerWand) {
            ItemStack stack = playerEntity.getMainHandStack();
            if (!playerEntity.isSneaking()) ((CornflowerWand) stack.getItem()).setBlockInput(this.pos, stack);
            playerEntity.addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_input"), true);
            ci.setReturnValue(null);
            ci.cancel();
        }
        if (playerEntity.getOffHandStack().getItem() instanceof CornflowerWand) {
            ItemStack stack = playerEntity.getOffHandStack();
            if (!playerEntity.isSneaking()) ((CornflowerWand) stack.getItem()).setBlockInput(this.pos, stack);
            playerEntity.addChatMessage(new TranslatableText("item.cornflower.wand_cornflower.use_input"), true);
            ci.setReturnValue(null);
            ci.cancel();
        }
    }
}
