package me.ram4gamer.whub.Manager;

import me.ram4gamer.whub.WHub;
import me.ram4gamer.whub.utils.chat.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public final class ItemManager implements Manager {

    private WHub plugin;

    public static void giveDefaultItems(Player player) {
        player.getInventory().clear();
        //Compass
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.setDisplayName(Color.translate("&bServer Selector"));
        ArrayList<String> compassLore = new ArrayList<>();
        compassLore.add("");
        compassLore.add(Color.translate("&3Right Click &bto open the Server Selector Menu "));
        compassMeta.setLore(compassLore);
        compass.setItemMeta(compassMeta);
        player.getInventory().addItem(compass);
        //Player Hider/Enabler
        if (PlayerHiderManager.hasHiddenSettings.contains(player)) {
            ItemStack redstoneTorch = new ItemStack(Material.REDSTONE_TORCH_OFF);
            ItemMeta redstoneMeta = redstoneTorch.getItemMeta();
            redstoneMeta.setDisplayName(Color.translate("&bShow players"));
            ArrayList<String> redstoneLore = new ArrayList<>();
            redstoneLore.add("");
            redstoneMeta.setLore(redstoneLore);
            redstoneTorch.setItemMeta(redstoneMeta);
            player.getInventory().addItem(redstoneTorch);
        }else {
            ItemStack redstoneTorch = new ItemStack(Material.REDSTONE_TORCH_ON);
            ItemMeta redstoneMeta = redstoneTorch.getItemMeta();
            redstoneMeta.setDisplayName(Color.translate("&bHide players"));
            ArrayList<String> redstoneLore = new ArrayList<>();
            redstoneLore.add("");
            redstoneMeta.setLore(redstoneLore);
            redstoneTorch.setItemMeta(redstoneMeta);
            player.getInventory().addItem(redstoneTorch);
        }
    }
}
