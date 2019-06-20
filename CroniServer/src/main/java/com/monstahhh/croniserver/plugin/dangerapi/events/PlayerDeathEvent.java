package com.monstahhh.croniserver.plugin.dangerapi.events;

import com.monstahhh.croniserver.plugin.dangerapi.DangerAPI;
import com.monstahhh.croniserver.plugin.dangerapi.PlayerHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerDeathEvent implements Listener {

    private PlayerHandler handler = new PlayerHandler();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent event) {
        Player player = event.getEntity();
        handler.setPlayerHealthy(player);
        handler.setPlayerInNeutral(player);
        DangerAPI.debugLog(player.getDisplayName() + " has died, damage data has been reset");
    }
}
