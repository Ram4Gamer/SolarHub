package me.ram4gamer.whub;

import lombok.Getter;
import lombok.NonNull;
import me.ram4gamer.whub.Listeners.PlayerListener;
import me.ram4gamer.whub.Manager.ItemManager;
import me.ram4gamer.whub.Manager.PriorityManager;
import me.ram4gamer.whub.Manager.QueueManager;
import me.ram4gamer.whub.serverMenu.ServerMenuCommand;
import me.ram4gamer.whub.serverMenu.ServerMenuListener;
import me.ram4gamer.whub.spawn.HubCommand;
import me.ram4gamer.whub.spawn.SetSpawnCommand;
import me.ram4gamer.whub.utils.Utilities;
import me.ram4gamer.whub.utils.chat.Color;
import me.ram4gamer.whub.utils.runnable.Runnable;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;

public final class WHub extends JavaPlugin {

    private static WHub INSTANCE;
    private QueueManager queue;
    @Getter
    private ItemManager itemManager;
    //Config Stuff
    private File spawnFile;
    private YamlConfiguration spawn;

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
        if (Bukkit.getPluginManager().getPlugin("NCore") != null) {
            //Broadcast that nCore is enabled and is running
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "nCore is in the server!");
        }
    }

    public void registerCommands() {
        getCommand("servermenu").setExecutor(new ServerMenuCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("hub").setExecutor(new HubCommand(this));
    }

    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new ServerMenuListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    //Spawn.yml creation
    public void createFiles() throws IOException {
        spawnFile = new File(this.getDataFolder(), "spawn.yml");
        if (!spawnFile.exists()) {
            spawnFile.createNewFile();
            saveResource("spawn.yml", true);
        }

        spawn = YamlConfiguration.loadConfiguration(spawnFile);
    }

    public void saveSpawn() {
        try {
            spawn.save(spawnFile);
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    public YamlConfiguration getSpawn() { return spawn; }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    //Scoreboard methods
    public void setMainScoreboard(Player player) {

        org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("hub", "dummy");
        obj.setDisplayName(Color.translate("&b&lHub"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);



        Score line = obj.getScore(Color.translate("&7&m---------------------"));
        line.setScore(6);

        Score playerName = obj.getScore(Color.translate("&bName: &3") + player.getName());
        playerName.setScore(5);

        Team online = board.registerNewTeam("online");
        online.addEntry(Color.translate("&3"));
        online.setPrefix(Color.translate("&bOnline: "));
        online.setSuffix(ChatColor.DARK_AQUA.toString() + Bukkit.getServer().getOnlinePlayers().size());
        obj.getScore(Color.translate("&3")).setScore(4);

        Team ping = board.registerNewTeam("ping");
        ping.addEntry(Color.translate("&b"));
        ping.setPrefix(ChatColor.AQUA + "Ping: ");
        ping.setSuffix(ChatColor.DARK_AQUA.toString() + Utilities.getPing(player));
        obj.getScore(Color.translate("&b")).setScore(3);

        Score blank = obj.getScore(" ");
        blank.setScore(2);


        Score website = obj.getScore(Color.translate("&3") + this.getConfig().getString("media.website"));
        website.setScore(1);

        player.setScoreboard(board);

        Runnable.scheduleSyncRepeatingTask(this, new BukkitRunnable() {
            @Override
            public void run() {
                ping.setSuffix(ChatColor.DARK_AQUA.toString() + Utilities.getPing(player));
            }
        }, 0, 20L);

        Runnable.scheduleSyncRepeatingTask(this, new BukkitRunnable() {
            @Override
            public void run() {
                online.setSuffix(ChatColor.DARK_AQUA.toString() + Bukkit.getServer().getOnlinePlayers().size());
            }
        }, 0, 20L);

    }

    public void setInQueueScoreboard(Player player) {
        org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("hub", "dummy");
        obj.setDisplayName(Color.translate("&b&lHub"));
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        int pos = QueueManager.practiceQueue.lastIndexOf(player) + 1;
        Team pos1 = board.registerNewTeam("pos1");
        pos1.addEntry(Color.translate("&3"));
        pos1.addEntry("");
        pos1.setPrefix(ChatColor.AQUA + "Your pos is " + ChatColor.DARK_AQUA + "");
        pos1.setSuffix(pos + "#" + ChatColor.AQUA + " out of " + ChatColor.DARK_AQUA.toString() + QueueManager.practiceQueue.size() + "#");
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
                    int pos = QueueManager.practiceQueue.lastIndexOf(player) + 1;
                    pos1.setPrefix(ChatColor.AQUA + "Your pos is " + ChatColor.DARK_AQUA + "");
                    pos1.setSuffix(pos + "#" + ChatColor.AQUA + " out of " + ChatColor.DARK_AQUA.toString() + QueueManager.practiceQueue.size() + "#");
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

    public static WHub getINSTANCE() {
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
