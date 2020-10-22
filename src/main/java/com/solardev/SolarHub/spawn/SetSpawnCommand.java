package com.solardev.SolarHub.spawn;

import com.solardev.SolarHub.SolarHub;
import com.solardev.SolarHub.utils.chat.Color;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
    SolarHub main;

    public SetSpawnCommand(SolarHub plugin)
    {
        this.main = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("setspawn") && !(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You cant use this command in Console");
            return true;
        }
        else if (!sender.hasPermission("hub.command.setspawn")) {
            sender.sendMessage(Color.translate(main.getLang().getString("No-permission")));
            return true;
        }
        else {
            if (sender.hasPermission("hub.command.setspawn")) {
                Player p = (Player) sender;
                Location l = p.getLocation();
                main.getSpawn().set("spawn.world", p.getLocation().getWorld().getName());
                main.getSpawn().set("spawn.x", p.getLocation().getX());
                main.getSpawn().set("spawn.y", p.getLocation().getY());
                main.getSpawn().set("spawn.z", p.getLocation().getZ());
                main.getSpawn().set("spawn.yaw", p.getLocation().getYaw());
                main.getSpawn().set("spawn.pitch", p.getLocation().getPitch());
                main.saveSpawn();
                p.sendMessage(Color.translate(main.getLang().getString("setspawn-message")));
            }

            return true;
        }
    }
}
