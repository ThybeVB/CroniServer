package com.monstahhh.croniserver.plugin.damageapi;

import com.monstahhh.croniserver.plugin.damageapi.configapi.Config;
import org.bukkit.entity.Player;

public class PlayerHandler {

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
