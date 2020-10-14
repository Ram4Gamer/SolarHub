package me.ram4gamer.whub.spawn;

import me.ram4gamer.whub.WHub;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCommand implements CommandExecutor {

    private WHub plugin;

    public HubCommand(WHub plugin) {
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
                    player.teleport(new Location(w, x, y, z, yaw, pitch));
                }
            }
        }


        return false;
    }
}
