package me.ram4gamer.whub.Manager;

import me.ram4gamer.whub.WHub;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerHiderManager implements Manager{

    private WHub plugin;
    public static ArrayList<Player> hasHiddenSettings = new ArrayList<>();
    public static ArrayList<Player> hasVisibleSettings = new ArrayList<>();

    public static void hidePlayersfromPlayer(Player player) {
        if (hasHiddenSettings.contains(player)) {
            return;
        }else {
            hasHiddenSettings.add(player);
            for (Player people : Bukkit.getOnlinePlayers()) {
                if (people.hasPermission("hub.bypass.invisible")) {
                    return;
                }else {
                    player.hidePlayer(WHub.getINSTANCE(), people);
                }
            }
        }
    }

    public static void showPlayersforPlayer(Player player) {
        if (hasHiddenSettings.contains(player)) {
            hasHiddenSettings.remove(player);
            hasVisibleSettings.add(player);
        }else {
            hasVisibleSettings.add(player);
            for (Player people : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(WHub.getINSTANCE(), people);
                }
            }
        }
    }


