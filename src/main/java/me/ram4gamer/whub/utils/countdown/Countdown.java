package me.ram4gamer.whub.utils.countdown;

import me.ram4gamer.whub.WHub;
import me.ram4gamer.whub.utils.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
public class Countdown extends BukkitRunnable {
    private final String broadcastMessage;
    private final int[] broadcastAt;
    private final Runnable tickHandler;
    private final Runnable broadcastHandler;
    private final Runnable finishHandler;
    private final Predicate<Player> messageFilter;
    private int seconds;
    private boolean first = true;

    public static CountdownBuilder of(int amount, TimeUnit unit) {
        return new CountdownBuilder((int)unit.toSeconds((long)amount));
    }

    Countdown(int seconds, String broadcastMessage, Runnable tickHandler, Runnable broadcastHandler, Runnable finishHandler, Predicate<Player> messageFilter, int... broadcastAt) {
        this.seconds = seconds;
        this.broadcastMessage = ChatColor.translateAlternateColorCodes('&', broadcastMessage);
        this.broadcastAt = broadcastAt;
        this.tickHandler = tickHandler;
        this.broadcastHandler = broadcastHandler;
        this.finishHandler = finishHandler;
        this.messageFilter = messageFilter;
        this.runTaskTimer(WHub.getINSTANCE(), 0L, 20L);
    }

    public final void run() {
        if (!this.first) {
            --this.seconds;
        } else {
            this.first = false;
        }

        int[] var1 = this.broadcastAt;
        int var2 = var1.length;

        label50:
        for(int var3 = 0; var3 < var2; ++var3) {
            int index = var1[var3];
            if (this.seconds == index) {
                String message = this.broadcastMessage.replace("{time}", TimeUtils.formatIntoDetailedString(this.seconds));
                Iterator var6 = Bukkit.getOnlinePlayers().iterator();

                while(true) {
                    Player player;
                    do {
                        if (!var6.hasNext()) {
                            if (this.broadcastHandler != null) {
                                this.broadcastHandler.run();
                            }
                            continue label50;
                        }

                        player = (Player)var6.next();
                    } while(this.messageFilter != null && !this.messageFilter.test(player));

                    player.sendMessage(message);
                }
            }
        }

        if (this.seconds == 0) {
            if (this.finishHandler != null) {
                this.finishHandler.run();
            }

            this.cancel();
        } else if (this.tickHandler != null) {
            this.tickHandler.run();
        }

    }

    public int getSecondsRemaining() {
        return this.seconds;
    }
}



