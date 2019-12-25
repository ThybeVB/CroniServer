package com.monstahhh.croniserver.plugin.practice;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerCooldownManager {

    public static HashMap<Player, Boolean> playersPlayedMap = new HashMap<>();
    private static HashMap<Player, Timer> playerCooldownMap = new HashMap<>();

    public static void killPlayer(Player p, boolean survived) {
        Timer timer = playerCooldownMap.getOrDefault(p, null);
        if (timer != null) {
            timer.cancel();
            timer.purge();
            playerCooldownMap.remove(p, timer);
            playersPlayedMap.put(p, true);
            UhcPractice.inDeathProcess.put(p, true);
            UhcPractice.showEndcard(p, survived);
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
                    killPlayer(p, true);
                }
            }, 3600000, 3600000); //3,600,000 == 2HOURS
        }
    }

    public boolean isPlaying(Player p) {
        return playerCooldownMap.get(p) != null;
    }
}
