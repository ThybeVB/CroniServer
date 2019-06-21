package com.monstahhh.croniserver.plugin.sleep.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class BedEvents implements Listener {

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        //foo
    }

    @EventHandler
    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
        //foo
    }
}
