package com.monstahhh.croniserver.plugin.advancements.events;

import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import com.monstahhh.croniserver.plugin.advancements.enums.AdvancementEnum;
import eu.endercentral.crazy_advancements.Advancement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class OnSleep implements Listener {

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.NOT_POSSIBLE_HERE) {
            Advancement advancement = AdvancementEnum.SWEETDREAMS.getAdvancement();
            CustomAdvancements.grantAdvancement(event.getPlayer(), advancement);
        }
    }
}
