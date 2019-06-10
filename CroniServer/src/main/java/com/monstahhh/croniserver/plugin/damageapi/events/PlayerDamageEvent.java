package com.monstahhh.croniserver.plugin.damageapi.events;

import com.monstahhh.croniserver.plugin.damageapi.PlayerHandler;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class PlayerDamageEvent implements Listener {

    PlayerHandler handler = new PlayerHandler();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player p = (Player) event.getEntity();
            if (!isPlayerFullHealth(p)) {
                handler.setPlayerDamaged(p);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player p = (Player)event.getEntity();
            if (isPlayerFullHealth(p)) {
                handler.setPlayerHealthy(p);
            }
        }
    }

    private boolean isPlayerFullHealth (Player player) {
        if (player.getHealth() == 20.0) {
            return true;
        }
        return false;
    }
}
