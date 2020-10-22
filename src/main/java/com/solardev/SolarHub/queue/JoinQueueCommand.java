package com.solardev.SolarHub.queue;

import com.solardev.SolarHub.SolarHub;
import com.solardev.SolarHub.utils.BungeeUtils;
import com.solardev.SolarHub.utils.QueueUtils;
import com.solardev.SolarHub.utils.chat.Color;
import com.solardev.SolarHub.Manager.QueueManager;
import com.solardev.SolarHub.utils.runnable.Runnable;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.lang.model.AnnotatedConstruct;
import java.nio.channels.spi.AbstractInterruptibleChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinQueueCommand implements CommandExecutor {

    private SolarHub plugin;

    public JoinQueueCommand(SolarHub plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("joinqueue")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1) {
                    String server = args[0];
                    if (plugin.getConfig().getConfigurationSection("servers").contains(server)) {
                        Queue.createQueue(plugin, server, player);
                        if (QueueUtils.getTotalQueuesString(plugin).size() >= 6)  {
                            if (player.hasPermission("hub.admin")) {
                                player.sendMessage(Color.translate("&cYou can only have 5 queues please remove some of the servers in the config file"));
                            }else {
                                player.sendMessage(Color.translate("&cThere is an issue contact the Admins of the server to fix it!"));
                            }
                        }else {
                            if (Queue.queues.lastIndexOf(server) == 0) {
                               List<Player> queue1 = QueueUtils.queue1;
                               queue1.add(player);
                               int urPos = queue1.lastIndexOf(player) + 1;
                                Bukkit.getScheduler().scheduleSyncDelayedTask(SolarHub.getINSTANCE(), new java.lang.Runnable() {
                                    @Override
                                    public void run() {
                                      if (queue1.get(0).getPlayer() != null) {
                                        Player pos1 = queue1.get(0);
                                        String sendingMessage = plugin.getLang().getString("send-to-server").replaceAll("<server>", server);
                                        pos1.sendMessage(Color.translate(sendingMessage));
                                        queue1.remove(pos1);
                                        plugin.setMainScoreboard(pos1);
                                        BungeeUtils.send(pos1, plugin.getConfig().getString("servers." + server + ".bungeecord-name"));
                                       }
                                    }
                                    }, plugin.getConfig().getInt("queue.delay") * 20);
                                Runnable.scheduleSyncRepeatingTask(SolarHub.getINSTANCE(), new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (!queue1.contains(player)) {
                                            Bukkit.getScheduler().cancelTask(this.getTaskId());
                                        }else {
                                            if (urPos == 0 && queue1.size() == 1) {
                                                queue1.remove(player);
                                                Bukkit.getScheduler().cancelTask(this.getTaskId());
                                            }else {
                                                if (!(queue1.lastIndexOf(player) == 0) && queue1.size() == 0) {
                                                    queue1.remove(player);
                                                    Bukkit.getScheduler().cancelTask(this.getTaskId());
                                                }else {
                                                    int urPos1 = urPos - 1;
                                                    if (queue1.lastIndexOf(player) == 0) {
                                                        player.sendMessage(Color.translate(plugin.getConfig().getString("queue.message").replaceAll("<pos>", String.valueOf(urPos) + "#").replaceAll("<total>", queue1.size() + "#")));
                                                    }else {
                                                        player.sendMessage(Color.translate(plugin.getConfig().getString("queue.message").replaceAll("<pos>", urPos + "#").replaceAll("<total>", String.valueOf(queue1.size()) + "#")));
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }, plugin.getConfig().getLong("queue.send-message-every") * 20, plugin.getConfig().getLong("queue.send-message-every") * 20);

                            }else {
                                player.sendMessage("COMMING SOON!");
                            }
                        }
                   }else {
                       player.sendMessage(Color.translate("&cThat server is Invalid"));}
                }else {
                    player.sendMessage(Color.translate("&cIncorrect usage. /joinqueue <Server>"));
                }
            }
        }


        return false;
    }

    //OLD CODE:


    //    List<Player> queue = new ArrayList<>();
    //   queue.add(player);
    //   Queue.createQueue(plugin, server, player);
    //   System.out.println(Arrays.toString(Queue.queues.toArray()));
    //   int urPos = queue.lastIndexOf(player) + 1;
    //    Bukkit.getScheduler().scheduleSyncDelayedTask(SolarHub.getINSTANCE(), new java.lang.Runnable() {
    //             @Override
    //public void run() {
    //  if (queue.get(0).getPlayer() != null) {
    //    Player pos1 = queue.get(0);
    //  String sendingMessage = plugin.getLang().getString("send-to-server").replaceAll("<server>", server);
    //pos1.sendMessage(Color.translate(sendingMessage));
    //                   queue.remove(pos1);
    //                       plugin.setMainScoreboard(pos1);
    //                        BungeeUtils.send(pos1, plugin.getConfig().getString("servers." + server + ".bungeecord-name"));
    //   }
    //        }
    //     }, plugin.getConfig().getInt("queue.delay") * 20);

    //Sending the pos of the player every 2 seconds
    // Bukkit.getScheduler().runTaskTimerAsynchronously(SolarHub.getINSTANCE(), new java.lang.Runnable() {
    //     @Override
    //  public void run() {
    //   if (!queue.contains(player)) {
    //     return;
    //}
    //        if (urPos == 0 && queue.size() == 1) {
    //          queue.remove(player);
    //        return;
    //  }

    //if (!(queue.lastIndexOf(player) == 0) && queue.size() == 0) {
    //  queue.remove(player);
    //return;
    //     }
    //   int urPos1 = urPos - 1;
    // if (queue.lastIndexOf(player) == 0) {
    //   player.sendMessage(Color.translate(plugin.getConfig().getString("queue.message").replaceAll("<pos>", String.valueOf(urPos) + "#").replaceAll("<total>", QueueManager.practiceQueue.size() + "#")));
    //  }else {
    //    player.sendMessage(Color.translate(plugin.getConfig().getString("queue.message").replaceAll("<pos>", urPos + "#").replaceAll("<total>", String.valueOf(QueueManager.practiceQueue.size()) + "#")));
    //   }
    // }
    // }, 0, plugin.getConfig().getInt("queue.send-message-every") * 20);

    //  new BukkitRunnable() {
    //  @Override
    //    public void run() {
    //       if (!queue.contains(player)) {
    //            return;
    //         }
    //        if (urPos == 0 && queue.size() == 1) {
    //             queue.remove(player);
    //             return;
    //        }

    //      if (!(queue.lastIndexOf(player) == 0) && queue.size() == 0) {
    //          queue.remove(player);
    //           return;
    //        }
    //    int urPos1 = urPos - 1;
    //      if (queue.lastIndexOf(player) == 0) {
    //            player.sendMessage(Color.translate(plugin.getConfig().getString("queue.message").replaceAll("<pos>", String.valueOf(urPos) + "#").replaceAll("<total>", QueueManager.practiceQueue.size() + "#")));
    //         }else {
    //         player.sendMessage(Color.translate(plugin.getConfig().getString("queue.message").replaceAll("<pos>", urPos + "#").replaceAll("<total>", String.valueOf(QueueManager.practiceQueue.size()) + "#")));
    //         }
    //        }

    //    }.runTaskTimer(SolarHub.getINSTANCE(), plugin.getConfig().getLong("send-message-every") * 20, 20L);
}
