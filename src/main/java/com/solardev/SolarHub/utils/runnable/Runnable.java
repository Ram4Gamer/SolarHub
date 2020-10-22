package com.solardev.SolarHub.utils.runnable;

import com.solardev.SolarHub.SolarHub;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Runnable{

    @Deprecated
    public static int scheduleSyncRepeatingTask(SolarHub plugin, BukkitRunnable task, long delay, long period) {
        return task.runTaskTimer(plugin, delay, period).getTaskId();
    }

    public static int runTaskTimer(SolarHub plugin, BukkitRunnable task, long delay, long period) {
        return task.runTaskTimer(plugin, delay, period).getTaskId();
    }

    public static int scheduleSyncDelayedTask(SolarHub plugin, BukkitRunnable task, long delay) {
        return task.runTaskLater(plugin, delay).getTaskId();
    }

}
