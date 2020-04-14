/*
 * This file is part of Cornflower
 * Copyright (C) 2020, Team Cornflower.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.cornflower.client;

import io.github.cornflower.util.ModConstants;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    public static FabricKeyBinding wandModeKey = FabricKeyBinding.Builder.create(new Identifier("cornflower", "wand_mode_key"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_CONTROL, "Cornflower").build();
    
}
