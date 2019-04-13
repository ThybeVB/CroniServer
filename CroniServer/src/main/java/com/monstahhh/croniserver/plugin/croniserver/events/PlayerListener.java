package com.monstahhh.croniserver.plugin.croniserver.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.getPlayer().getServer().broadcastMessage(ChatColor.GREEN + "Welcome " + event.getPlayer().getDisplayName() + "!");
    }
}
