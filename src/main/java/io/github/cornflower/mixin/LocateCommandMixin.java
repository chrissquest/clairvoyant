/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.mixin;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.LocateCommand;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.server.command.CommandManager.literal;

@Mixin(LocateCommand.class)
public abstract class LocateCommandMixin {

    private LocateCommandMixin() {}

    @Shadow
    private static int execute(ServerCommandSource source, String name) {
        throw new AssertionError();
    }

    @Inject(method = "register", at = @At(value = "RETURN"))
    private static void onRegister(CommandDispatcher<ServerCommandSource> dispatcher, CallbackInfo info) {
        dispatcher.register(literal("locate").requires(source -> source.hasPermissionLevel(2))
                .then(literal("Cornflower_Ruin").executes(ctx -> execute(ctx.getSource(), "cornflower:ruin_structure"))));
    }
}
