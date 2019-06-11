package com.monstahhh.croniserver.plugin.damageapi;

import com.monstahhh.croniserver.plugin.damageapi.configapi.Config;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerHandler {

    HashMap<Player, Timer> timedPlayers = new HashMap<>();

    public void setPlayerInCombat (Player player) {
        Config playerData = DamageAPI.playerData;

        playerData.getConfig().set("players." + player.getDisplayName() + ".inCombat", true);
        playerData.saveConfig();


        startTimerForPlayer(player);
    }

    public void setPlayerInNeutral (Player player) {
        Config playerData = DamageAPI.playerData;

        playerData.getConfig().set("players." + player.getDisplayName() + ".inCombat", false);
        playerData.saveConfig();
    }

    private void startTimerForPlayer (Player player) {

        DamageAPI.debugLog("Starting Timer for " + player.getDisplayName());

        if (timedPlayers.get(player) != null) {
            timedPlayers.remove(player);
            DamageAPI.debugLog("COMBAT WILL BE RESTARTED FOR " + player.getDisplayName());
        }
        Timer timer = new Timer();
        timedPlayers.put(player, timer);
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                DamageAPI.debugLog("COMBAT FINISHED FOR " + player.getDisplayName());
                timedPlayers.remove(player);
                setPlayerInNeutral(player);
            }
        }, 0, 30000);
    }

    public void setPlayerDamaged (Player player) {
        Config playerData = DamageAPI.playerData;

        playerData.getConfig().set("players." + player.getDisplayName() + ".damaged", true);
        playerData.saveConfig();
    }

    public void setPlayerHealthy (Player player) {
        Config playerData = DamageAPI.playerData;

        playerData.getConfig().set("players." + player.getDisplayName() + ".damaged", false);
        playerData.saveConfig();
    }
}
