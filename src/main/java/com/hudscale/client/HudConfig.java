package com.hudscale.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.*;

public final class HudConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("hudscale.json");
    private record Entry(int x, int y, float scale) {}
    private HudConfig() {}
    public static void load(List<HudElement> elements) {
        if (!Files.exists(FILE)) return;
        try (Reader r=Files.newBufferedReader(FILE)) {
            Type t=new TypeToken<Map<String,Entry>>(){}.getType();
            Map<String,Entry> map=GSON.fromJson(r,t); if(map==null)return;
            for(HudElement e:elements){ Entry v=map.get(e.id); if(v!=null){e.x=v.x;e.y=v.y;e.scale=Math.max(.25f,Math.min(4f,v.scale));} }
        } catch(Exception ignored) {}
    }
    public static void save(List<HudElement> elements) {
        Map<String,Entry> map=new LinkedHashMap<>();
        for(HudElement e:elements) map.put(e.id,new Entry(e.x,e.y,e.scale));
        try { Files.createDirectories(FILE.getParent()); Files.writeString(FILE,GSON.toJson(map)); } catch(IOException ignored) {}
    }
}
