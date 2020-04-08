/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.mixin;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.UUID;

@Environment(EnvType.CLIENT)
@Mixin(PlayerListEntry.class)
public class PlayerListEntryMixin {

    @Shadow
    @Final
    private Map<MinecraftProfileTexture.Type, Identifier> textures;

    @Inject(at = @At("TAIL"), method = "loadTextures")
    private void loadTextures(CallbackInfo callbackInfo) {
        if (MinecraftClient.getInstance().world != null) {
            for (Entity entity : MinecraftClient.getInstance().world.getPlayers()) {
                if (entity instanceof PlayerEntity) {
                    if (entity.getUuid().equals(UUID.fromString("90bbcaac-09fd-49e5-ab92-5f1ddcc5d21b")) ||
                            entity.getUuid().equals(UUID.fromString("6b3f7e10-3eae-4784-9a5a-e4c353ad1f80")) ||
                            entity.getUuid().equals(UUID.fromString("e655aa98-5fec-4b4e-b03a-7cd71df90b05"))) {
                        if (this.textures != null) {
                            textures.put(MinecraftProfileTexture.Type.CAPE, new Identifier("cornflower:textures/cape/dev_cape.png"));
                        }
                    }
                }
            }
        }
    }
}
