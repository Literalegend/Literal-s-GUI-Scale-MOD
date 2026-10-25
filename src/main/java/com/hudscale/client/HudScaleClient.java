package com.hudscale.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class HudScaleClient implements ClientModInitializer {
    private static KeyBinding openEditor;

    @Override
    public void onInitializeClient() {
        openEditor = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hudscale.open_editor",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                "category.hudscale"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openEditor.wasPressed()) {
                // Important: only open from normal gameplay. Chat, signs, search boxes,
                // and every other Screen keep ownership of the I key.
                if (client.currentScreen == null && client.player != null) {
                    client.setScreen(new HudEditorScreen());
                }
            }
        });
    }
}
