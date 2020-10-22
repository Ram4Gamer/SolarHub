package com.solardev.SolarHub.queue;

import com.solardev.SolarHub.SolarHub;
import com.solardev.SolarHub.utils.chat.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Queue {

    private SolarHub plugin;
    private final HashMap<String, Player> queue = new HashMap<>();
    public static ArrayList<String> queues = new ArrayList<>();


    public Player getPlayer(SolarHub plugin, Player player, String server) {
        return queue.get(server);
    }

    public String getServer(SolarHub plugin, String server, Player player) {
        return queue.get(server).toString();
    }

    public static void createQueue(SolarHub plugin, String server, Player player) {
        List<String> queues = Collections.singletonList(plugin.getConfig().getString("servers." + server + "bungeecord-name"));
        for (String queueString : queues) {
            if (Queue.queues.contains(server)) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + server + " is already created and registered as queue");
                return;
            }else {
                Queue.queues.add(server);
            }

        }
    }

    public boolean isQueueCreated(SolarHub plugin, String server) {
        return queues.contains(server);
    }

    public void getQueue(SolarHub plugin, String server) {
        if (isQueueCreated(plugin, server)) {
            ArrayList<Player> queue1 = new ArrayList<>();
        }else {
            return;
        }
    }
}
