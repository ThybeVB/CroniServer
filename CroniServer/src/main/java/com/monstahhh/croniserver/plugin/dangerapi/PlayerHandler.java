package com.monstahhh.croniserver.plugin.dangerapi;

import com.monstahhh.croniserver.configapi.Config;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerHandler {

    private final HashMap<Player, Timer> timedPlayers = new HashMap<>();

    public void setPlayerInCombat(Player player) {
        Config playerData = DangerAPI.playerData;

        playerData.getConfig().set("players." + player.getName() + ".inCombat", true);
        playerData.saveConfig();

        startTimerForPlayer(player);
    }

    public void setPlayerInNeutral(Player player) {
        Config playerData = DangerAPI.playerData;

        playerData.getConfig().set("players." + player.getName() + ".inCombat", false);
        playerData.saveConfig();
    }

    private void startTimerForPlayer(Player player) {
        DangerAPI.debugLog("Starting Timer for " + player.getName());

        if (timedPlayers.get(player) != null) {
            Timer oldTimer = timedPlayers.get(player);
            oldTimer.cancel();
            oldTimer.purge();
            timedPlayers.remove(player, oldTimer);
            DangerAPI.debugLog("COMBAT WILL BE RESTARTED FOR " + player.getName());
        }
        Timer timer = new Timer();
        timedPlayers.put(player, timer);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                DangerAPI.debugLog("COMBAT FINISHED FOR " + player.getName());
                timer.cancel();
                timer.purge();

                timedPlayers.remove(player, timer);
                setPlayerInNeutral(player);
            }
        }, 15000, 15000);
    }

    public void setPlayerDamaged(Player player) {
        Config playerData = DangerAPI.playerData;

        playerData.getConfig().set("players." + player.getName() + ".damaged", true);
        playerData.saveConfig();
    }

    public void setPlayerHealthy(Player player) {
        Config playerData = DangerAPI.playerData;

        playerData.getConfig().set("players." + player.getName() + ".damaged", false);
        playerData.saveConfig();
    }

    public void setFalling(Player player) {
        Config playerData = DangerAPI.playerData;

        playerData.getConfig().set("players." + player.getName() + ".falling", true);
        playerData.saveConfig();
    }

    public void setStill(Player player) {
        Config playerData = DangerAPI.playerData;

        playerData.getConfig().set("players." + player.getName() + ".falling", false);
        playerData.saveConfig();
    }
}
