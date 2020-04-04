package com.monstahhh.croniserver.plugin.uhc4.events;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Random;

public class DeathEvent implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getWorld().getName().equalsIgnoreCase("uhc4") || event.getEntity().getWorld().getName().equalsIgnoreCase("uhc4_nether")) {
            Random random = new Random();

            if (event.getEntityType() == EntityType.WITHER_SKELETON) {
                event.getDrops().clear();
                if (random.nextInt(100) <= 33) {
                    ItemStack coal = new ItemStack(Material.COAL, 1);
                    event.getDrops().add(coal);
                }
                if (random.nextInt(1000) <= 35) {
                    ItemStack witherSkull = new ItemStack(Material.WITHER_SKELETON_SKULL, 1);
                    event.getDrops().add(witherSkull);
                }
                if (random.nextInt(10) <= 5) {
                    ItemStack oneBone = new ItemStack(Material.BONE, 1);
                    event.getDrops().add(oneBone);
                } else {
                    ItemStack twoBones = new ItemStack(Material.BONE, 2);
                    event.getDrops().add(twoBones);
                }
            }
            if (event.getEntityType() == EntityType.GHAST) {
                event.getDrops().clear();
                if (random.nextInt(10) <= 5) {
                    ItemStack gold = new ItemStack(Material.GOLD_INGOT, 1);
                    event.getDrops().add(gold);
                }
                if (random.nextInt(10) <= 5) {
                    ItemStack onePowder = new ItemStack(Material.GUNPOWDER, 1);
                    event.getDrops().add(onePowder);
                } else {
                    ItemStack twoPowder = new ItemStack(Material.GUNPOWDER, 2);
                    event.getDrops().add(twoPowder);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        if (p.getWorld().getName().startsWith("uhc4")) {
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                LuckPerms api = provider.getProvider();
                //foo
            }
        }
    }
}
