package com.monstahhh.croniserver.plugin.advancements.events;

import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerServerUpdate implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(CustomAdvancements._plugin, () -> {
            Player p = event.getPlayer();

            CustomAdvancements._manager.loadProgress(p, "croniserver");
            if (!CustomAdvancements._manager.getPlayers().contains(p)) {
                CustomAdvancements._manager.addPlayer(p);
            }
        }, 3);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        CustomAdvancements._manager.removePlayer(event.getPlayer());
    }
}
