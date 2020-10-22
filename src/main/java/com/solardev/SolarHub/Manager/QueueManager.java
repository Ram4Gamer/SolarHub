package com.solardev.SolarHub.Manager;

import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class QueueManager {

    List<String> queue = new ArrayList<>();
    public static List<Player>  practiceQueue = new ArrayList<>();

    public boolean add(String name) {
        if (queue.contains(name))
            return false;
        queue.add(name);
        return true;
    }

    public static void removeFromPracticeQueue(Player player) {
        practiceQueue.remove(player);
    }

    @NonNull
    public static void addtoPracticeQueue(Player player) {
        practiceQueue.add(player);
    }

    public void insert(String name, int pos) {
        if (pos > queue.size())
            pos = queue.size();
        queue.add(pos, name);
    }

    public static int getTotalPosinPracticeQueue() {
        int total = practiceQueue.size();
        return total;
    }

    public int getPosinPracticeQueue(Player player) {
        int pos = practiceQueue.indexOf(player);
        return pos;
    }

    public static void inserttoPracticeQueue(Player player, int pos) {
        if (pos > practiceQueue.size())
            pos = practiceQueue.size();
        practiceQueue.add(pos, player);
    }

    public String getFront(boolean removeFromQueue) {
        String name = queue.get(0);
        if (removeFromQueue)
            queue.remove(0);
        return name;
    }

    public static Player getFrontinPracticeQueue(boolean removedFromQueue) {
        Player player = practiceQueue.get(0);
        if (removedFromQueue)
            practiceQueue.remove(0);
        return player;
    }


}
