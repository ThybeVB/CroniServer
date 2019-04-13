package com.monstahhh.croniserver.plugin.croniserver.events;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class ServerListener implements Listener {

    @EventHandler()
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getWorld().getName().equalsIgnoreCase("uhc") || event.getEntity().getWorld().getName().equalsIgnoreCase("uhc_nether")) {
            if (event.getEntityType() == EntityType.GHAST) {
                ItemStack gold = new ItemStack(Material.GOLD_INGOT, 1);
                event.getDrops().clear();
                event.getDrops().add(gold);
            }
        }

    }
}
