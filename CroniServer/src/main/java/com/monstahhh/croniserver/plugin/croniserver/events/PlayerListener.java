package com.monstahhh.croniserver.plugin.croniserver.events;

import com.monstahhh.croniserver.configapi.Config;
import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import fr.xephi.authme.api.v3.AuthMeApi;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private Config playerWorldInvs = new Config("plugins/CroniServer", "player_inventories.yml");
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
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().getDisplayName().equalsIgnoreCase("Guaka25")) {
            if (event.getMessage().equalsIgnoreCase("/ban monstahhhy") || event.getMessage().equalsIgnoreCase("/kick monstahhhy") || event.getMessage().equalsIgnoreCase("/demote monstahhhy")) {
                event.getPlayer().sendMessage(ChatColor.DARK_RED + "<3");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player p = event.getPlayer();
        if (!p.getWorld().getName().startsWith("world")) {
            if (!p.getWorld().getName().startsWith("uhcpractice")) {
                boolean hasBeenCleared = playerWorldInvs.getConfig().getBoolean("worlds." + p.getWorld().getName() + ".players." + p.getDisplayName());
                if (!hasBeenCleared) {
                    Bukkit.getScheduler().runTaskLater(CustomAdvancements._plugin, () -> {
                        playerWorldInvs.getConfig().set("worlds." + p.getWorld().getName() + ".players." + p.getDisplayName(), true);
                        playerWorldInvs.saveConfig();
                        p.getInventory().clear();

                        TextComponent component = new TextComponent("#minecraft-news");
                        component.setColor(net.md_5.bungee.api.ChatColor.BLUE);
                        component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discordapp.com/channels/305792249877364738/524211976109424641/661015846965084161"));

                        p.sendMessage(ChatColor.GREEN + "Because of a bug with our Inventory plugin, we have had to clear your inventory in this world.");
                        p.sendMessage(ChatColor.GREEN + "For more info on this, view");
                        p.spigot().sendMessage(component);
                    }, 20);
                }
            }
        }
    }
}
