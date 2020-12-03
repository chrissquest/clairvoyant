/*
 * This file is part of Clairvoyant
 * Copyright (C) 2020, Chriss.
 *
 * This code is licensed under GNU General Public License v3.0, the full license text can be found in LICENSE
 */

package io.github.clairvoyant.client;

import io.github.clairvoyant.util.ModConstants;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    public static KeyBinding wandModeKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + ModConstants.MOD_ID +".wand_mode_key", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "Cornflower"));
}
