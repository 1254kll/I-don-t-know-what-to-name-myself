package com.antibluescreen;
import java.util.HashSet;
import java.util.Set;
public class ModConfig {
    public static boolean killOnHit = true;
    public static boolean stripNbt = true;
    public static boolean banRecipe = true;
    public static boolean killProcess = false;
    public static boolean protectPlayer = true;
    public static boolean triggerClearMobs = false;
    public static final Set<String> BANNED_ITEMS = new HashSet<>();
    static { BANNED_ITEMS.add("lolipickaxe:loli_pickaxe"); }
}
