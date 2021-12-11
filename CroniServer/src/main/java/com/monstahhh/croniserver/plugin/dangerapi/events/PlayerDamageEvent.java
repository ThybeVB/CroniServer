package com.monstahhh.croniserver.plugin.dangerapi.events;

import com.monstahhh.croniserver.plugin.dangerapi.DangerAPI;
import com.monstahhh.croniserver.plugin.dangerapi.PlayerHandler;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.Objects;

public class PlayerDamageEvent implements Listener {

    private final PlayerHandler handler = new PlayerHandler();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            if (event.getDamager() instanceof Player attacker) {
                Player defender = (Player) event.getEntity();

                handler.setPlayerInCombat(attacker);
                handler.setPlayerInCombat(defender);
            } else {
                if (event.getDamager() instanceof Mob) {
                    handler.setPlayerInCombat((Player) event.getEntity());
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player p = (Player) event.getEntity();
            DangerAPI.debugLog(p.getName() + " DAMAGE: SETDAMAGED");
            handler.setPlayerDamaged(p);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player p = (Player) event.getEntity();
            if (isPlayerSufficientHealth(p)) {
                DangerAPI.debugLog(p.getName() + " REGAIN: SETHEALTHY");
                handler.setPlayerHealthy(p);
            } else {
                DangerAPI.debugLog(p.getName() + " REGAIN: SETDAMAGED");
                handler.setPlayerDamaged(p);
            }
        }
    }

    private boolean isPlayerSufficientHealth(Player player) {
        double fixedHealth = player.getHealth() + 1.0;
        DangerAPI.debugLog(player.getName() + "'s Current Health: " + Math.round(fixedHealth));
        DangerAPI.debugLog(player.getName() + "'s Max Health: " + Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getDefaultValue());

        if (Math.round(fixedHealth) > (Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getDefaultValue())*0.75) {
            DangerAPI.debugLog(player.getName() + " is at sufficient health!");
            return true;
        }
        DangerAPI.debugLog(player.getName() + " is not at full health!");
        return false;
    }
}
