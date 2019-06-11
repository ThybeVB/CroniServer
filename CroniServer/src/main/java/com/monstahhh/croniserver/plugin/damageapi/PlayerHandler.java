package com.monstahhh.croniserver.plugin.damageapi;

import com.monstahhh.croniserver.plugin.damageapi.configapi.Config;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerHandler {

    private List<Player> playersInCombat = new LinkedList<>();
    public void setPlayerInCombat (Player player) {
        Config playerData = DamageAPI.playerData;

        playerData.getConfig().set("players." + player.getDisplayName() + ".inCombat", true);
        playerData.saveConfig();


        startTimerForPlayer(player);
    }

    public void setPlayerInNeutral (Player player) {
        Config playerData = DamageAPI.playerData;

        playerData.getConfig().set("players." + player.getDisplayName() + "inCombat", false);
        playerData.saveConfig();
    }

    private void startTimerForPlayer (Player player) {
        Timer timer = new Timer();
        if (playersInCombat.contains(player)) {
            timer.cancel();
            DamageAPI.debugLog("COMBAT WILL BE RESTARTED FOR " + player.getDisplayName());
            timer.purge();
        }

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                DamageAPI.debugLog("COMBAT FINISHED FOR " + player.getDisplayName());
                playersInCombat.remove(player);
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
