package com.monstahhh.croniserver.plugin.croniserver.events;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    private AuthMeApi authApi = AuthMeApi.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (p.getLocation().getBlock().getType() == Material.NETHER_PORTAL) {
            p.sendMessage(ChatColor.GREEN + "Noticed you are in a portal, force logging in...");
            authApi.forceLogin(p);
            p.getServer().broadcastMessage(ChatColor.RED + "Force Login was performed for " + p.getDisplayName());
        }

        p.getServer().broadcastMessage(ChatColor.GREEN + "Welcome " + event.getPlayer().getDisplayName() + "!");
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getWorld().getName().equalsIgnoreCase("uhc2") || event.getEntity().getWorld().getName().equalsIgnoreCase("uhc2_nether")) {
            if (event.getEntityType() == EntityType.GHAST) {
                ItemStack gold = new ItemStack(Material.GOLD_INGOT, 1);
                event.getDrops().clear();
                event.getDrops().add(gold);
            }
        }
    }
}
