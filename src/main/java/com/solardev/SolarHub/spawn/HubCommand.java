package com.solardev.SolarHub.spawn;

import com.solardev.SolarHub.SolarHub;
import com.solardev.SolarHub.utils.chat.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCommand implements CommandExecutor {

    private SolarHub plugin;

    public HubCommand(SolarHub plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("hub")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("hub.command.hub")) {
                    if (plugin.getSpawn().getConfigurationSection("spawn") == null) {
                        return true;
                    }

                    World w = Bukkit.getServer().getWorld(plugin.getSpawn().getString("spawn.world"));
                    double x = plugin.getSpawn().getDouble("spawn.x");
                    double y = plugin.getSpawn().getDouble("spawn.y");
                    double z = plugin.getSpawn().getDouble("spawn.z");
                    float yaw = (float)plugin.getSpawn().getInt("spawn.yaw");
                    float pitch = (float)plugin.getSpawn().getInt("spawn.pitch");
                    player.sendMessage(Color.translate(plugin.getLang().getString("teleport-to-hub-message")));
                    player.teleport(new Location(w, x, y, z, yaw, pitch));
                }else {
                    player.sendMessage(Color.translate(plugin.getLang().getString("No-permission")));
                }
            }
        }


        return false;
    }
}
