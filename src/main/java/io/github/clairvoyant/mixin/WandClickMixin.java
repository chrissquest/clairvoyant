/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.mixin;

import io.github.clairvoyant.entity.FeyType;
import io.github.clairvoyant.item.Wand;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LootableContainerBlockEntity.class)
public abstract class WandClickMixin extends LockableContainerBlockEntity {
    // Make it extend the block entity so that we can get it's position
    protected WandClickMixin(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    // Mixin to the create menu method
    @Inject(at = @At("HEAD"), method = "createMenu")
    public void createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity playerEntity, CallbackInfoReturnable<ScreenHandler> ci) {
        Hand activeHand = playerEntity.getActiveHand();
        ItemStack stack = playerEntity.getStackInHand(activeHand);

        //  If the container was clicked on by a wand, set the input for the wand and cancel opening the chest
        if (!playerEntity.isSneaking() && stack.getItem() instanceof Wand) {
            CompoundTag tag = stack.getTag();
            if (tag != null && tag.contains("Type")) {
                if (tag.getString("Type").equals(FeyType.TRANSPORT_ITEM.toString())) {
                    // Set the input
                    ((Wand) stack.getItem()).setBlockInput(this.getPos(), stack);
                    playerEntity.sendMessage(new TranslatableText("item.clairvoyant.wand.use_input"), true);
                    // Cancel opening chest
                    ci.setReturnValue(null);
                    ci.cancel();
                }
            }
        }
    }

}
