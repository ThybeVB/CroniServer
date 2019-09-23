package com.monstahhh.croniserver.plugin.sleep.events;

import org.bukkit.ChatColor;
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
        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            if (event.getPlayer().hasPermission("croniserver.sleep")) {
                Player p = event.getPlayer();
                sleepingPlayers.add(p);
                doCheck(event.getPlayer().getWorld());
            }
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

    private void doCheck(World w) {
        int sleepRequirement = 1;
        if (sleepingPlayers.size() >= sleepRequirement) {
            skipNight(w);
        } else {
            doReminder(w, sleepRequirement);
        }
    }

    private void skipNight(World w) {
        sleepingPlayers = new ArrayList<>();
        w.setTime(1000L);
        w.setStorm(false);

        for (Player p : w.getPlayers()) {
            p.sendMessage(ChatColor.YELLOW + "Night has been skipped.");
        }
    }

    private void doReminder(World w, int requiredSleepers) {
        for (Player p : w.getPlayers()) {
            p.sendMessage(ChatColor.YELLOW + String.valueOf(sleepingPlayers.size()) + "/" + requiredSleepers + " Are sleeping.");
        }
    }
}