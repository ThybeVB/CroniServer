package com.monstahhh.croniserver.plugin.damageapi.events;

import com.monstahhh.croniserver.plugin.damageapi.DamageAPI;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageEvent implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player p = (Player)event.getEntity();
            p.getServer().getConsoleSender().sendMessage("plr entity dmg");
            //DamageAPI.config.getConfig().set("this.is.a.location", "plr damaged");
            //DamageAPI.config.saveConfig();
        }
    }
}
