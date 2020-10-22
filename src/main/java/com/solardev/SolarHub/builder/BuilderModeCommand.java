package com.solardev.SolarHub.builder;

import com.solardev.SolarHub.SolarHub;
import com.solardev.SolarHub.utils.chat.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuilderModeCommand implements CommandExecutor {

    private static SolarHub plugin;

    public BuilderModeCommand(SolarHub plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("build")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (plugin.getConfig().getBoolean("builder-mode.require-permission")) {
                    if (player.hasPermission(plugin.getConfig().getString("builder-mode.permission"))) {
                        if (BuilderMode.getBuildMode(player) == true) {
                            player.sendMessage(Color.translate(plugin.getLang().getString("disabled-builder-mode")));
                            BuilderMode.removePlayer(player);
                        }else {
                            player.sendMessage(Color.translate(plugin.getLang().getString("enabled-builder-mode")));
                            BuilderMode.addPlayer(player);
                        }
                    }else {
                        player.sendMessage(Color.translate(plugin.getLang().getString("No-permission")));
                    }
                }
            }
        }

        return false;
    }
}
