package com.monstahhh.croniserver.plugin.dangerapi.events;

import com.monstahhh.croniserver.plugin.dangerapi.PlayerHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerMoveEvent implements Listener {

    private PlayerHandler handler = new PlayerHandler();

    @EventHandler
    public void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if (p.getVelocity().getY() < 0) {
            handler.setFalling(p);
        } else if (p.getVelocity().getY() >= 0) {
            handler.setStill(p);
        }
    }
}
