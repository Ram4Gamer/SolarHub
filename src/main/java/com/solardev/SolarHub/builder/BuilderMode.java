package com.solardev.SolarHub.builder;

import com.solardev.SolarHub.SolarHub;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BuilderMode {

    public static ArrayList<Player> build = new ArrayList<>();
    private static SolarHub plugin;

    public static boolean getBuildMode(Player player) {
        return build.contains(player);
    }

    public static void addPlayer(Player player) {
        if (build.contains(player)) {
            return;
        }else {
            build.add(player);
        }
    }

    public static Integer getPlayers() {
        return build.size();
    }

    public static void removePlayer(Player player) {
        if (!build.contains(player)) {
            return;
        }else {
            build.remove(player);
        }
    }
}
