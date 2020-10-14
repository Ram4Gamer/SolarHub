package me.ram4gamer.whub.utils.runnable;

import me.ram4gamer.whub.WHub;
import org.bukkit.scheduler.BukkitRunnable;

public class Runnable{

    @Deprecated
    public static int scheduleSyncRepeatingTask(WHub plugin, BukkitRunnable task, long delay, long period) {
        return task.runTaskTimer(plugin, delay, period).getTaskId();
    }

}
