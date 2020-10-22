package com.solardev.SolarHub.serverMenu;

import com.solardev.SolarHub.Manager.QueueManager;
import com.solardev.SolarHub.utils.chat.Color;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ServerMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("servermenu")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                openServerMenu(p);
            }
        }


        return false;
    }

    public void openServerMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.DARK_AQUA + "Server Selector");

        ItemStack is = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_AQUA + "Practice!");
        List<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Color.translate("&bInQueue&f: &3") + QueueManager.getTotalPosinPracticeQueue());
        meta.setLore(lore);
        is.setItemMeta(meta);
        inv.setItem(6 - 2, is);

        player.openInventory(inv);
    }
}
