package com.solardev.SolarHub.Manager;

import com.solardev.SolarHub.SolarHub;

public class PausedQueueManager implements Manager {

    private SolarHub plugin;
    private static boolean practiceQueued;

    //public boolean getPractice(WHub plugin) {
    //   return plugin.getConfig().getBoolean("paused-queues.Practice");
    //}

    public boolean getServer(SolarHub plugin, String server) {
        return plugin.getConfig().getBoolean("paused-queues." + server);
    }

    public void setServer(SolarHub plugin, String server) {
        if (plugin.getConfig().getConfigurationSection("servers").contains(server)) {
            if (plugin.getConfig().getConfigurationSection("paused-queues").contains(server)) {
                if (plugin.getConfig().getBoolean("paused-queues." + server)) {
                    return;
                }else {
                    plugin.getConfig().set("paused-queues." + server, true);
                }
            }
        }
    }
}
