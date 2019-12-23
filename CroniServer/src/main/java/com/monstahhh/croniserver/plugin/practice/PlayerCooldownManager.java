package com.monstahhh.croniserver.plugin.practice;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerCooldownManager {

    public static HashMap<Player, Boolean> playersPlayedMap = new HashMap<>();
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
            UhcPractice.showEndcard(p, false);
        }
    }

    public static boolean hasPlayed(Player p) {
        return playersPlayedMap.get(p) != null;
    }

    public void startGame(Player p) {
        if (!isPlaying(p)) {
            Timer timer = new Timer();
            playerCooldownMap.put(p, timer);
            p.sendMessage(ChatColor.BLUE + "Starting UHC Practice! (2 Hours)");
            p.sendMessage(ChatColor.BLUE + "You can use /stopuhc at any time to stop the session.");
            p.sendMessage(ChatColor.BLUE + "Note: You can only play once per day.");
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timer.cancel();
                    timer.purge();
                    playerCooldownMap.remove(p, timer);
                    playersPlayedMap.put(p, true);
                    UhcPractice.showEndcard(p, true);
                }
            }, 60000, 60000); //60,000 == 1 MINUTE (Temporary)
            //}, 3600000, 3600000); //3,600,000 == 2HOURS
        }
    }

    public boolean isPlaying(Player p) {
        return playerCooldownMap.get(p) != null;
    }
}
