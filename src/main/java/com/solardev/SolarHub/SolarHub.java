package com.solardev.SolarHub;

import com.solardev.SolarHub.queue.Queue;
import com.solardev.SolarHub.serverMenu.ServerMenuCommand;
import com.solardev.SolarHub.serverMenu.ServerMenuListener;
import lombok.Getter;
import lombok.NonNull;
import com.solardev.SolarHub.Listeners.PlayerListener;
import com.solardev.SolarHub.Manager.ItemManager;
import com.solardev.SolarHub.Manager.PriorityManager;
import com.solardev.SolarHub.Manager.QueueManager;
import com.solardev.SolarHub.builder.BuilderModeCommand;
import com.solardev.SolarHub.queue.JoinQueueCommand;
import com.solardev.SolarHub.spawn.HubCommand;
import com.solardev.SolarHub.spawn.SetSpawnCommand;
import com.solardev.SolarHub.utils.Utilities;
import com.solardev.SolarHub.utils.chat.Color;
import com.solardev.SolarHub.utils.runnable.Runnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class SolarHub extends JavaPlugin {

    private static SolarHub INSTANCE;
    private QueueManager queue;
    @Getter
    private ItemManager itemManager;
    //Config Stuff
    private File spawnFile;
    private YamlConfiguration spawn;
    private File scoreboardFile;
    private YamlConfiguration scoreboard;
    private File langFile;
    private YamlConfiguration lang;
    //Random stuff
    private static String message;

    @Override
    public void onEnable() {
        //Making the Instance of the plugin.
        INSTANCE = this;
        //Register all of the commands
        registerCommands();
        //Register all of the Listeners
        registerListeners();
        //Register BungeeCord stuff
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        //Config stuff
        try {
            this.createFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        //Check if nCore is included
        new BukkitRunnable() {
            public void run() {

                for(Player p : Bukkit.getOnlinePlayers()){
                    setMainScoreboard(p);
                }
            }

        }.runTaskTimer(this, 60L, getConfig().getInt("scoreboard.update-every"));

        System.out.println(Queue.queues.toString());

    }

    public void registerCommands() {
        getCommand("servermenu").setExecutor(new ServerMenuCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("hub").setExecutor(new HubCommand(this));
        getCommand("build").setExecutor(new BuilderModeCommand(this));
        getCommand("joinqueue").setExecutor(new JoinQueueCommand(this));
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ServerMenuListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
    }



    //Spawn.yml creation & scoreboard.yml creation
    public void createFiles() throws IOException {
        spawnFile = new File(this.getDataFolder(), "spawn.yml");
        scoreboardFile = new File(this.getDataFolder(), "scoreboard.yml");
        langFile = new File(this.getDataFolder(), "lang.yml");
        if (!spawnFile.exists()) {
            spawnFile.createNewFile();
            saveResource("spawn.yml", true);
        }

        if (!scoreboardFile.exists()) {
            scoreboardFile.createNewFile();
            saveResource("scoreboard.yml", true);
        }

        if (!langFile.exists()) {
            langFile.createNewFile();
            saveResource("lang.yml", true);
        }

      //  if (!menuFile.exists()) {
       //     menuFile.createNewFile();
       //     saveResource("menus.yml", true);
       // }



        spawn = YamlConfiguration.loadConfiguration(spawnFile);
        scoreboard = YamlConfiguration.loadConfiguration(scoreboardFile);
        lang = YamlConfiguration.loadConfiguration(langFile);
       // menu = YamlConfiguration.loadConfiguration(menuFile);
    }

    public void saveSpawn() {
        try {
            spawn.save(spawnFile);
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    public YamlConfiguration getSpawn() { return spawn; }

    public YamlConfiguration getScoreboard() { return scoreboard; }

    public YamlConfiguration getLang() {
        return lang;
    }

   // public YamlConfiguration getMenu() {
   //     return menu;
   // }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    //Scoreboard methods
    public void setMainScoreboard(Player player) {

        if (!getScoreboard().getBoolean("enabled")) {
            return;
        }

        org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("hub", "dummy");
        obj.setDisplayName(Color.translate(this.getScoreboard().getString("lobby-title")));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);


        List<String> lines = this.scoreboard.getStringList("lobby");
        int size = lines.size() + 1;
        for (String linestring : lines) {
            size--;
            Team line = board.registerNewTeam("line" + size);
            String test = linestring.replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size())).replaceAll("<ping>", String.valueOf(Utilities.getPing(player))).replaceAll("<website>", getConfig().getString("media.website"));
            line.addEntry(ChatColor.translateAlternateColorCodes('&', linestring.replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size())).replaceAll("<ping>", String.valueOf(Utilities.getPing(player))).replaceAll("<website>", getConfig().getString("media.website"))).replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size())).replaceAll("<ping>", String.valueOf(Utilities.getPing(player))).replaceAll("<website>", getConfig().getString("media.website")));
            obj.getScore(ChatColor.translateAlternateColorCodes('&', linestring.replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size())).replaceAll("<ping>", String.valueOf(Utilities.getPing(player))).replaceAll("<website>", getConfig().getString("media.website")))).setScore(size);

            Runnable.runTaskTimer(this, new BukkitRunnable() {
                @Override
                public void run() {
                    if (!QueueManager.practiceQueue.contains(player)) {
                        List<String> lines = scoreboard.getStringList("lobby");
                        try {
                            int size = lines.size() + 1;
                            for (String linestring : lines) {
                                size--;
                                Team line = board.registerNewTeam("line" + size);
                                String test = linestring.replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size())).replaceAll("<ping>", String.valueOf(Utilities.getPing(player))).replaceAll("<website>", getConfig().getString("media.website"));
                                line.addEntry(ChatColor.translateAlternateColorCodes('&', linestring.replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size())).replaceAll("<ping>", String.valueOf(Utilities.getPing(player))).replaceAll("<website>", getConfig().getString("media.website")).replaceAll("<pos>", String.valueOf(QueueManager.practiceQueue.lastIndexOf(player) + 1)).replaceAll("<total-queue>", String.valueOf(QueueManager.practiceQueue.size()))).replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size())).replaceAll("<ping>", String.valueOf(Utilities.getPing(player))).replaceAll("<website>", getConfig().getString("media.website")));
                                obj.getScore(ChatColor.translateAlternateColorCodes('&', linestring.replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size())).replaceAll("<ping>", String.valueOf(Utilities.getPing(player))).replaceAll("<website>", getConfig().getString("media.website")).replaceAll("<pos>", String.valueOf(QueueManager.practiceQueue.lastIndexOf(player) + 1)).replaceAll("<total-queue>", String.valueOf(QueueManager.practiceQueue.size())))).setScore(size);
                            }
                        }catch (IllegalArgumentException var3) {
                            Bukkit.getScheduler().cancelTask(this.getTaskId());
                        }
                    }else {
                        Bukkit.getScheduler().cancelTask(this.getTaskId());
                    }
                }
            }, 0L, getConfig().getInt("scoreboard.update-every") * 20);
        }

        player.setScoreboard(board);


    }

    public void setInQueueScoreboard(Player player) {

        if (!getScoreboard().getBoolean("enabled")) {
            return;
        }

        org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("hub", "dummy");
        obj.setDisplayName(Color.translate(getScoreboard().getString("in-queue-title")));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> lines = this.scoreboard.getStringList("in-queue");
        int size = lines.size() + 1;
        for (String linestring : lines) {
            size--;
            Team line = board.registerNewTeam("line" + size);
            String test = linestring.replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size())).replaceAll("<ping>", String.valueOf(Utilities.getPing(player))).replaceAll("<website>", getConfig().getString("media.website"));
            line.addEntry(ChatColor.translateAlternateColorCodes('&', linestring.replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size())).replaceAll("<ping>", String.valueOf(Utilities.getPing(player))).replaceAll("<website>", getConfig().getString("media.website")).replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size())).replaceAll("<ping>", String.valueOf(Utilities.getPing(player))).replaceAll("<website>", getConfig().getString("media.website"))));
            obj.getScore(ChatColor.translateAlternateColorCodes('&', linestring.replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size())).replaceAll("<ping>", String.valueOf(Utilities.getPing(player))).replaceAll("<website>", getConfig().getString("media.website")))).setScore(size);

            int finalSize = size;
            Runnable.runTaskTimer(this, new BukkitRunnable() {
                @Override
                public void run() {
                    if (QueueManager.practiceQueue.contains(player)) {
                        List<String> lines = scoreboard.getStringList("in-queue");
                        int size = lines.size() + 1;
                        for (String linestring : lines) {
                            size--;
                            Team line = board.registerNewTeam("line" + size);
                            String test = linestring.replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size())).replaceAll("<ping>", String.valueOf(Utilities.getPing(player))).replaceAll("<website>", getConfig().getString("media.website"));
                            line.addEntry(ChatColor.translateAlternateColorCodes('&', linestring.replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size())).replaceAll("<ping>", String.valueOf(Utilities.getPing(player))).replaceAll("<website>", getConfig().getString("media.website")).replaceAll("<pos>", String.valueOf(QueueManager.practiceQueue.lastIndexOf(player) + 1)).replaceAll("<total-queue>", String.valueOf(QueueManager.practiceQueue.size()))).replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size())).replaceAll("<ping>", String.valueOf(Utilities.getPing(player))).replaceAll("<website>", getConfig().getString("media.website")));
                            obj.getScore(ChatColor.translateAlternateColorCodes('&', linestring.replaceAll("<players>", String.valueOf(Bukkit.getOnlinePlayers().size())).replaceAll("<ping>", String.valueOf(Utilities.getPing(player))).replaceAll("<website>", getConfig().getString("media.website")).replaceAll("<pos>", String.valueOf(QueueManager.practiceQueue.lastIndexOf(player) + 1)).replaceAll("<total-queue>", String.valueOf(QueueManager.practiceQueue.size())))).setScore(size);
                        }
                    }else {
                        Bukkit.getScheduler().cancelTask(this.getTaskId());
                    }
                }
            }, 0L, getConfig().getInt("scoreboard.update-every") * 20);
            }
        }



    public void setInVipQueueScoreboard(Player player) {
        org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("hub", "dummy");
        obj.setDisplayName(Color.translate("&b&lHub"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        int pos = PriorityManager.vipPracticQueue.lastIndexOf(player) + 1;
        Team pos1 = board.registerNewTeam("pos2");
        pos1.addEntry(Color.translate("&3"));
        pos1.addEntry("");
        pos1.setPrefix(ChatColor.AQUA + "Your pos is " + ChatColor.DARK_AQUA + "");
        pos1.setSuffix(pos + "#" + ChatColor.AQUA + " out of " + ChatColor.DARK_AQUA.toString() + PriorityManager.vipPracticQueue.size() + "#");
        obj.getScore(Color.translate("&3")).setScore(5);

        Score server = obj.getScore(Color.translate("&bfor &3Practice&B Queue"));
        server.setScore(4);

        Team ping = board.registerNewTeam("ping");
        ping.addEntry(Color.translate("&b"));
        ping.setPrefix(ChatColor.AQUA + "Ping: ");
        ping.setSuffix(ChatColor.DARK_AQUA.toString() + Utilities.getPing(player));
        obj.getScore(Color.translate("&b")).setScore(3);

        Score line = obj.getScore(Color.translate("&7&m------------------------"));
        line.setScore(7);

        Score playerName = obj.getScore(Color.translate("&bName: &3") + player.getName());
        playerName.setScore(6);

        Score blank = obj.getScore(" ");
        blank.setScore(2);

        Score website = obj.getScore(Color.translate("&3") + this.getConfig().getString("media.website"));
        website.setScore(1);

        player.setScoreboard(board);

        Runnable.scheduleSyncRepeatingTask(this, new BukkitRunnable() {
            @Override
            public void run() {
                if (QueueManager.practiceQueue.contains(player)) {
                    int pos = PriorityManager.vipPracticQueue.lastIndexOf(player) + 1;
                    pos1.setPrefix(ChatColor.AQUA + "Your pos is " + ChatColor.DARK_AQUA + "");
                    pos1.setSuffix(pos + "#" + ChatColor.AQUA + " out of " + ChatColor.DARK_AQUA.toString() + PriorityManager.vipPracticQueue.size() + "#");
                }else {
                    Bukkit.getScheduler().cancelTask(this.getTaskId());
                }

            }
        }, 0, 20L);

        Runnable.scheduleSyncRepeatingTask(this, new BukkitRunnable() {
            @Override
            public void run() {
                ping.setSuffix(ChatColor.DARK_AQUA.toString() + Utilities.getPing(player));
            }
        }, 0, 20L);
    }

    //Method to access the Main class Instance

    public static SolarHub getINSTANCE() {
        return INSTANCE;
    }

    @NonNull
    public QueueManager getQueue() {
        return queue;
    }

    @NonNull
    public ItemManager getItemManager() {
        return itemManager;
    }
}
