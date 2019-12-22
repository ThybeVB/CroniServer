package com.monstahhh.croniserver.plugin.practice;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerCooldownManager {

    private static HashMap<Player, Timer> playerCooldownMap = new HashMap<>();
    private static JavaPlugin plugin;

    public PlayerCooldownManager(JavaPlugin _plugin) {
        plugin = _plugin;
    }

    public static void killPlayer(Player p) {
        Timer timer = playerCooldownMap.getOrDefault(p, null);
        if (timer != null) {
            timer.cancel();
            timer.purge();
            playerCooldownMap.remove(p, timer);
            showEndcard(p, false);
        }
    }

    private static void showEndcard(Player p, boolean survived) {
        p.sendMessage(ChatColor.GREEN + "--- Game has finished ---");
        if (survived) {
            p.sendMessage(ChatColor.BLUE + "> You Survived!");
        } else {
            p.sendMessage(ChatColor.DARK_RED + "> You Died!");
        }
        p.sendMessage(ChatColor.DARK_RED + "> Mobs Killed: " + UhcPractice.playerMobsKilled.getOrDefault(p, 0));
        p.sendMessage(ChatColor.GREEN + "-------------------------");

        Bukkit.getScheduler().runTask(plugin, () -> {
            p.teleport(new Location(Bukkit.getWorld("hub"), 0, 100, 0));
        });
        UhcPractice.playerMobsKilled.remove(p);
    }

    public void startGame(Player p) {
        if (!isPlaying(p)) {
            Timer timer = new Timer();
            playerCooldownMap.put(p, timer);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timer.cancel();
                    timer.purge();
                    playerCooldownMap.remove(p, timer);
                    showEndcard(p, true);
                }
            }, 20000, 20000); //20,000 == 20 SECONDS (Temporary)
            //}, 3600000, 3600000); //3,600,000 == 2HOURS
        }
    }

    public boolean isPlaying(Player p) {
        return playerCooldownMap.get(p) != null;
    }
}
