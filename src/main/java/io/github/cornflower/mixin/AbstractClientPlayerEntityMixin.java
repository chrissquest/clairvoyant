/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Environment(EnvType.CLIENT)
@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin {

    @Shadow protected abstract PlayerListEntry getPlayerListEntry();

    @Inject(method = "getCapeTexture", at = @At("RETURN"), cancellable = true)
    private void getCapeTexture(CallbackInfoReturnable<Identifier> info) {
        if(
                this.getPlayerListEntry().getProfile().getId().equals(UUID.fromString("90bbcaac-09fd-49e5-ab92-5f1ddcc5d21b")) ||
                this.getPlayerListEntry().getProfile().getId().equals(UUID.fromString("6b3f7e10-3eae-4784-9a5a-e4c353ad1f80")) ||
                this.getPlayerListEntry().getProfile().getId().equals(UUID.fromString("e655aa98-5fec-4b4e-b03a-7cd71df90b05"))
        ) {
            info.setReturnValue(new Identifier("cornflower:textures/cape/dev_cape.png"));
        }
    }
}
