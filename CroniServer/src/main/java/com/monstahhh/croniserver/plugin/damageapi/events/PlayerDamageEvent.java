package com.monstahhh.croniserver.plugin.damageapi.events;

import com.monstahhh.croniserver.plugin.damageapi.DamageAPI;
import com.monstahhh.croniserver.plugin.damageapi.PlayerHandler;
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

public class PlayerDamageEvent implements Listener {

    private PlayerHandler handler = new PlayerHandler();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            if (event.getDamager() instanceof Player) {
                Player attacker = (Player)event.getDamager();
                Player defender = (Player)event.getEntity();

                handler.setPlayerInCombat(attacker);
                handler.setPlayerInCombat(defender);
            } else {
                if (event.getDamager() instanceof Mob) {
                    handler.setPlayerInCombat((Player)event.getEntity());
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player p = (Player)event.getEntity();
            DamageAPI.debugLog(p.getDisplayName() + " DAMAGE: SETDAMAGED");
            handler.setPlayerDamaged(p);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player p = (Player)event.getEntity();
            if (isPlayerFullHealth(p)) {
                DamageAPI.debugLog(p.getDisplayName() + " REGAIN: SETHEALTHY");
                handler.setPlayerHealthy(p);
            } else {
                DamageAPI.debugLog(p.getDisplayName() + " REGAIN: SETDAMAGED");
                handler.setPlayerDamaged(p);
            }
        }
    }

    private boolean isPlayerFullHealth (Player player) {
        double fixedHealth = player.getHealth() + 1.0;
        DamageAPI.debugLog(player.getDisplayName() + "'s Current Health: " + Math.round(fixedHealth));
        DamageAPI.debugLog(player.getDisplayName() + "'s Max Health: " + player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue());

        if (Math.round(fixedHealth) >= player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue()) {
            DamageAPI.debugLog( player.getDisplayName() + " is at Max Health!");
            return true;
        }
        DamageAPI.debugLog(player.getDisplayName() + " is not at Full Health!");
        return false;
    }
}
