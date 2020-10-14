package me.ram4gamer.whub.Listeners;

import me.ram4gamer.whub.Manager.ItemManager;
import me.ram4gamer.whub.WHub;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

public class PlayerListener implements Listener {

    private WHub plugin;

    public PlayerListener(WHub plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        //Teleport to spawn
        if (plugin.getSpawn().getConfigurationSection("spawn") == null) {
            return;
        }

        World w = Bukkit.getServer().getWorld(plugin.getSpawn().getString("spawn.world"));
        double x = plugin.getSpawn().getDouble("spawn.x");
        double y = plugin.getSpawn().getDouble("spawn.y");
        double z = plugin.getSpawn().getDouble("spawn.z");
        float yaw = (float)plugin.getSpawn().getInt("spawn.yaw");
        float pitch = (float)plugin.getSpawn().getInt("spawn.pitch");
        e.getPlayer().teleport(new Location(w, x, y, z, yaw, pitch));

        //give items
        ItemManager.giveDefaultItems(player);

        // Set Scoreboard
        plugin.setMainScoreboard(player);
    }


    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        World w = Bukkit.getServer().getWorld(plugin.getSpawn().getString("spawn.world"));
        double x = plugin.getSpawn().getDouble("spawn.x");
        double y = plugin.getSpawn().getDouble("spawn.y");
        double z = plugin.getSpawn().getDouble("spawn.z");
        float yaw = (float)plugin.getSpawn().getInt("spawn.yaw");
        float pitch = (float)plugin.getSpawn().getInt("spawn.pitch");
        e.getPlayer().teleport(new Location(w, x, y, z, yaw, pitch));
    }

    @EventHandler
    public void on(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
            if (plugin.getConfig().getBoolean("options.drop-items") == true) {
                e.setCancelled(false);
            }else {
                e.setCancelled(true);
            }
        }
    @EventHandler
    public void on(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "Server Selector")) {
                player.performCommand("servermenu");
            }
        }

    }

    @EventHandler
    public void on(InventoryDragEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getOldCursor().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "Server Selector")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(InventoryMoveItemEvent e) {
        if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "Server Selector")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        if (plugin.getConfig().getBoolean("options.weather-change") == true) {
            e.setCancelled(false);
        }else {
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onPlayerJoinEvent2(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();
        player.sendMessage(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "&7&m------------------------"));
        player.sendMessage(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "&b&lStore: &7store.solarpvp.cc"));
        player.sendMessage(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "&b&lForum: &7www.solarpvp.cc"));
        player.sendMessage(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "&b&lTwitter: &7@SolarPvP"));
        player.sendMessage(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "&7&m------------------------"));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (plugin.getConfig().getBoolean("options.break-block") == true) {
            event.setCancelled(false);
        }else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickItem(PlayerPickupItemEvent e) {
        if (plugin.getConfig().getBoolean("options.pickup-items") == false) {
            e.setCancelled(true);
            e.getItem().remove();
        }else {
            e.setCancelled(false);
        }

    }



    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (plugin.getConfig().getBoolean("options.food-fevel-change") == true) {
            e.setCancelled(false);
        }else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }


}

