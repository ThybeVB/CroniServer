package com.monstahhh.croniserver.plugin.dangerapi;

import com.monstahhh.croniserver.plugin.dangerapi.configapi.Config;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerHandler {

    private HashMap<Player, Timer> timedPlayers = new HashMap<>();

    public void setPlayerInCombat(Player player) {
        Config playerData = DangerAPI.playerData;

        playerData.getConfig().set("players." + player.getDisplayName() + ".inCombat", true);
        playerData.saveConfig();

        startTimerForPlayer(player);
    }

    public void setPlayerInNeutral(Player player) {
        Config playerData = DangerAPI.playerData;

        playerData.getConfig().set("players." + player.getDisplayName() + ".inCombat", false);
        playerData.saveConfig();
    }

    private void startTimerForPlayer(Player player) {
        DangerAPI.debugLog("Starting Timer for " + player.getDisplayName());

        if (timedPlayers.get(player) != null) {
            Object oldTimer = timedPlayers.get(player);
            ((Timer) oldTimer).cancel();
            ((Timer) oldTimer).purge();
            timedPlayers.remove(player, oldTimer);
            DangerAPI.debugLog("COMBAT WILL BE RESTARTED FOR " + player.getDisplayName());
        }
        Timer timer = new Timer();
        timedPlayers.put(player, timer);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                DangerAPI.debugLog("COMBAT FINISHED FOR " + player.getDisplayName());
                timer.cancel();
                timer.purge();

                timedPlayers.remove(player, timer);
                setPlayerInNeutral(player);
            }
        }, 30000, 30000);
    }

    public void setPlayerDamaged(Player player) {
        Config playerData = DangerAPI.playerData;

        playerData.getConfig().set("players." + player.getDisplayName() + ".damaged", true);
        playerData.saveConfig();
    }

    public void setPlayerHealthy(Player player) {
        Config playerData = DangerAPI.playerData;

        playerData.getConfig().set("players." + player.getDisplayName() + ".damaged", false);
        playerData.saveConfig();
    }

    public void setFalling(Player player) {
        Config playerData = DangerAPI.playerData;

        playerData.getConfig().set("players." + player.getDisplayName() + ".falling", true);
        playerData.saveConfig();
    }

    public void setStill(Player player) {
        Config playerData = DangerAPI.playerData;

        playerData.getConfig().set("players." + player.getDisplayName() + ".falling", false);
        playerData.saveConfig();
    }
}
