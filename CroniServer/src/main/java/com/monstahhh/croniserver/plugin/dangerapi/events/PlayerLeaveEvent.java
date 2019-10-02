package com.monstahhh.croniserver.plugin.dangerapi.events;

import com.monstahhh.croniserver.configapi.Config;
import com.monstahhh.croniserver.plugin.dangerapi.DangerAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveEvent implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Config playerData = DangerAPI.playerData;
        playerData.getConfig().set("players." + event.getPlayer().getDisplayName(), null);
        playerData.saveConfig();
        DangerAPI.debugLog(event.getPlayer().getDisplayName() + " has quit, Danger data removed");
    }
}
