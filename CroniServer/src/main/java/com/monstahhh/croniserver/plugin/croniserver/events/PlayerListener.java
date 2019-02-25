package com.monstahhh.croniserver.plugin.croniserver.events;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    public static int guakaWheat = 0;
    public static int monstahWheat = 0;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        if (event.getPlayer().getDisplayName().equalsIgnoreCase("Guaka25")) {
            event.getPlayer().getServer().broadcastMessage(ChatColor.DARK_RED + "Welcome, " + event.getPlayer().getDisplayName() + ", monstahhh is way cooler than u");
        } else {
            event.getPlayer().getServer().broadcastMessage(ChatColor.GREEN + "Welcome " + event.getPlayer().getDisplayName() + "!");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final Block block = event.getBlock();
        if (block.getData() == 7) {
            if (event.getPlayer().getDisplayName().equalsIgnoreCase("Guaka25")) {
                guakaWheat++;
            } else if (event.getPlayer().getDisplayName().equalsIgnoreCase("monstahhhy")) {
                monstahWheat++;
            }
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event){
        if (event.getPlayer().getWorld().getName() == "uhc") {
            event.getPlayer().getServer().broadcastMessage(event.getPlayer().getWorld().getName());
            boolean dead = FindPlayerUHCState(event.getPlayer().getName());
        }
    }

    boolean FindPlayerUHCState (String username) {
        return false;
    }
}
