package me.ram4gamer.whub.spawn;

import me.ram4gamer.whub.WHub;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
    WHub main;

    public SetSpawnCommand(WHub plugin)
    {
        this.main = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("setspawn") && !(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You cant use this command in Console");
            return true;
        }
        else if (!sender.hasPermission("hub.command.setspawn")) {
            sender.sendMessage(ChatColor.RED + "No permission");
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
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have set the world spawn!"));
            }

            return true;
        }
    }
}
