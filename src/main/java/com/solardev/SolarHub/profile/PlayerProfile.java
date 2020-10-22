package com.solardev.SolarHub.profile;

import com.solardev.SolarHub.SolarHub;
import com.solardev.SolarHub.builder.BuilderMode;
import com.solardev.SolarHub.Manager.QueueManager;
import org.bukkit.entity.Player;

public class PlayerProfile {

    private static SolarHub plugin;

    public boolean isPlayerinBuildMode(Player player) {
        return BuilderMode.getBuildMode(player);
    }

    public boolean isinQueue(Player player) {
        return QueueManager.practiceQueue.contains(player);
    }

}
