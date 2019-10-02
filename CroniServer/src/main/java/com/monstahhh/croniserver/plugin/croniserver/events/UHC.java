package com.monstahhh.croniserver.plugin.croniserver.events;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UHC implements Listener {

    private JavaPlugin plugin;

    public UHC(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void enable() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getWorld().getName().equalsIgnoreCase("uhc3") || event.getEntity().getWorld().getName().equalsIgnoreCase("uhc3_nether")) {
            if (event.getEntityType() == EntityType.GHAST) {
                ItemStack gold = new ItemStack(Material.GOLD_INGOT, 1);
                event.getDrops().clear();
                event.getDrops().add(gold);
            }
        }
    }
}
