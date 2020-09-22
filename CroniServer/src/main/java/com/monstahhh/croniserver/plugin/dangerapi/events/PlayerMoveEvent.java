package com.monstahhh.croniserver.plugin.dangerapi.events;

import com.monstahhh.croniserver.plugin.dangerapi.PlayerHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerMoveEvent implements Listener {

    private final PlayerHandler handler = new PlayerHandler();

    @EventHandler
    public void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if (p.isOnGround()) {
            handler.setStill(p);
        } else {
            handler.setFalling(p);
        }
    }
}
