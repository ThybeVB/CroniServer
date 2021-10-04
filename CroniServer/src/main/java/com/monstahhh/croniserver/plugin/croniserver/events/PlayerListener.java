package com.monstahhh.croniserver.plugin.croniserver.events;

import fr.xephi.authme.api.v3.AuthMeApi;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private final AuthMeApi authApi = AuthMeApi.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (p.getLocation().getBlock().getType() == Material.NETHER_PORTAL) {
            p.sendMessage(ChatColor.GREEN + "Noticed you are in a portal, force logging in...");
            authApi.forceLogin(p);
            p.getServer().sendMessage(Component.text(ChatColor.RED + "Force Login was performed for " + p.getName()));
        }

        p.getServer().sendMessage(Component.text(ChatColor.GREEN + "Welcome " + event.getPlayer().getName() + "!"));
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().getName().equalsIgnoreCase("Guaka25")) {
            if (event.getMessage().equalsIgnoreCase("/ban monstahhhy") || event.getMessage().equalsIgnoreCase("/kick monstahhhy") || event.getMessage().equalsIgnoreCase("/demote monstahhhy")) {
                event.getPlayer().sendMessage(ChatColor.DARK_RED + "<3");
                event.setCancelled(true);
            }
        }
    }
}
