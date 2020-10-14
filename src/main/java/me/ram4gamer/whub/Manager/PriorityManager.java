package me.ram4gamer.whub.Manager;

import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PriorityManager implements Manager {

    public static ArrayList<Player> vipPracticQueue = new ArrayList<>();

    public static void inserttovipPracticeQueue(Player player, int pos) {
        if (pos > vipPracticQueue.size())
            pos = vipPracticQueue.size();
        vipPracticQueue.add(pos, player);
    }

    @NonNull
    public static void addtovipPracticeQueue(Player player) {
        vipPracticQueue.add(player);
    }

    public static void removeFromVipPracticeQueue(Player player) {
        vipPracticQueue.remove(player);
    }
}
