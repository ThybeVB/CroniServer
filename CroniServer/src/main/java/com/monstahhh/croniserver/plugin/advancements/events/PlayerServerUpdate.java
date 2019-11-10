package com.monstahhh.croniserver.plugin.advancements.events;

import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerServerUpdate implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        if (!CustomAdvancements._manager.getPlayers().contains(p)) {
            CustomAdvancements._manager.addPlayer(p);
        }
        CustomAdvancements._manager.loadProgress(p, "croniserver");
        CustomAdvancements._manager.update(p);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        CustomAdvancements._manager.saveProgress(event.getPlayer(), "croniserver");
    }
}
