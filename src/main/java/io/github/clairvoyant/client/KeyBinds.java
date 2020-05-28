/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.client;

import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    public static FabricKeyBinding wandModeKey = FabricKeyBinding.Builder.create(new Identifier("clairvoyant", "wand_mode_key"), InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_CONTROL, "Cornflower").build();
    
}
