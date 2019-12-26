package com.monstahhh.croniserver.plugin.practice.events;

import com.monstahhh.croniserver.plugin.practice.PlayerCooldownManager;
import com.monstahhh.croniserver.plugin.practice.UhcPractice;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class GameEvents implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player p = event.getEntity().getKiller();
        if (p != null) {
            if (p.getWorld().getName().equalsIgnoreCase("uhcpractice")) {
                if (event.getEntity() instanceof Mob) {
                    int amountKilled = UhcPractice.playerMobsKilled.getOrDefault(p, 0);
                    if (amountKilled == 0) {
                        UhcPractice.playerMobsKilled.put(p, 1);
                    } else {
                        UhcPractice.playerMobsKilled.replace(p, amountKilled + 1);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity().getWorld().getName().equalsIgnoreCase("uhcpractice")) {
            PlayerCooldownManager.killPlayer(event.getEntity(), false);
        }
    }
}
