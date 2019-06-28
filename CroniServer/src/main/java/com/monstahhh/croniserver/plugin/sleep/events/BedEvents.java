package com.monstahhh.croniserver.plugin.sleep.events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.util.ArrayList;
import java.util.List;

public class BedEvents implements Listener {

    private List<Player> sleepingPlayers = new ArrayList<>();

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (event.getPlayer().hasPermission("croniserver.sleep")) {
            Player p = event.getPlayer();
            sleepingPlayers.add(p);
            doCheck(event.getPlayer().getWorld());
        }
    }

    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
        if (event.getPlayer().hasPermission("croniserver.sleep")) {
            Player p = event.getPlayer();
            sleepingPlayers.remove(p);
            doCheck(event.getPlayer().getWorld());
        }
    }

    private void doCheck (World w) {
        int sleepRequirement = 1;
        if (sleepingPlayers.size() >= sleepRequirement) {
            skipNight(w);
        }
    }

    private void skipNight (World w) {
        sleepingPlayers = new ArrayList<>();
        w.setTime(1000L);
    }
}
