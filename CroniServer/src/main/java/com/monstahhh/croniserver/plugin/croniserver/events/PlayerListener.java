package com.monstahhh.croniserver.plugin.croniserver.events;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

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

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.getWorld().getName().startsWith("uhc3")) {
            if (player.getDisplayName().equalsIgnoreCase("Guaka25")) {
                player.sendMessage(ChatColor.DARK_RED + "YOU FUCKING DIED BITCH");
            }
            Location hub = new Location(Bukkit.getWorld("hub"), 0, 101, 0);
            player.teleport(hub);
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().getDisplayName().equalsIgnoreCase("Guaka25")) {
            if (event.getMessage().equalsIgnoreCase("/ban monstahhhy") || event.getMessage().equalsIgnoreCase("/kick monstahhhy")) {
                event.getPlayer().sendMessage(ChatColor.DARK_RED + "<3");
                event.setCancelled(true);
            }
        }
    }
}
