package com.hudscale.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HudRegistry {
    private static final List<HudElement> ELEMENTS = new ArrayList<>();
    private HudRegistry() {}
    public static void initialize(int width, int height) {
        if (!ELEMENTS.isEmpty()) return;
        ELEMENTS.add(new HudElement("hotbar", "Hotbar", width/2-91, height-24, 182, 22));
        ELEMENTS.add(new HudElement("scoreboard", "Scoreboard", width-128, 60, 120, 90));
        ELEMENTS.add(new HudElement("chat", "Chat", 4, height-150, 280, 135));
        ELEMENTS.add(new HudElement("bossbar", "Boss Bar", width/2-91, 12, 182, 20));
        ELEMENTS.add(new HudElement("effects", "Status Effects", width-126, 8, 118, 42));
        ELEMENTS.add(new HudElement("mod_overlay", "Mod HUD / Overlay", 12, 45, 150, 55));
        HudConfig.load(ELEMENTS);
    }
    public static List<HudElement> all(){ return Collections.unmodifiableList(ELEMENTS); }
}
