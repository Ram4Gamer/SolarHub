package me.ram4gamer.whub.scoreboard;

import me.ram4gamer.whub.WHub;
import me.ram4gamer.whub.utils.Utilities;
import me.ram4gamer.whub.utils.chat.Color;
import me.ram4gamer.whub.utils.runnable.Runnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

public class Scoreboard {

    private static WHub plugin;

    public Scoreboard(WHub plugin) {
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

        Runnable.scheduleSyncRepeatingTask(WHub.getINSTANCE(), new BukkitRunnable() {
            @Override
            public void run() {
                player.getScoreboard().getTeam("ping").setSuffix(Utilities.getPing(player) + "");
            }
        }, 20L, 58838685L);
    }
}
