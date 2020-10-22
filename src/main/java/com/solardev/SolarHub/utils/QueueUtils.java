package com.solardev.SolarHub.utils;

import com.solardev.SolarHub.SolarHub;
import com.solardev.SolarHub.queue.Queue;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Set;

public class QueueUtils {

    private SolarHub plugin;
    public static ArrayList<Player> queue1 = new ArrayList<>();
    public static ArrayList<Player> queue2 = new ArrayList<>();
    public static ArrayList<Player> queue3 = new ArrayList<>();
    public static ArrayList<Player> queue4 = new ArrayList<>();
    public static ArrayList<Player> queue5 = new ArrayList<>();

    public static int getTotalQueues() {
        return Queue.queues.size();
    }

    public static Set<String> getTotalQueuesString(SolarHub plugin) {
        return plugin.getConfig().getConfigurationSection("servers").getKeys(false);
    }


}
