package com.solardev.SolarHub.scoreboard;

import com.solardev.SolarHub.SolarHub;
import com.solardev.SolarHub.utils.Utilities;
import com.solardev.SolarHub.utils.chat.Color;
import com.solardev.SolarHub.utils.runnable.Runnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

public class Scoreboard {

    private static SolarHub plugin;

    public Scoreboard(SolarHub plugin) {
        this.plugin = plugin;
    }

    public static void setMainScoreboard(Player player) {

        org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("hub", "dummy");
        obj.setDisplayName(Color.translate("&b&lHub"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        Team ping = board.registerNewTeam("ping");
        ping.addEntry(Color.translate("&b"));
        ping.addEntry("");
        ping.setPrefix(ChatColor.AQUA + "Ping: ");
        ping.setSuffix(Utilities.getPing(player) + "");
        obj.getScore(Color.translate("&b")).setScore(3);


        Score blank = obj.getScore(" ");
        blank.setScore(2);

        Score website = obj.getScore(Color.translate("&3") + plugin.getConfig().getInt("media.website"));
        website.setScore(1);

        player.setScoreboard(board);

        Runnable.scheduleSyncRepeatingTask(SolarHub.getINSTANCE(), new BukkitRunnable() {
            @Override
            public void run() {
                player.getScoreboard().getTeam("ping").setSuffix(Utilities.getPing(player) + "");
            }
        }, 20L, 58838685L);
    }
}
