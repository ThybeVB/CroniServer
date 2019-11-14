package com.monstahhh.croniserver.plugin.advancements.events;

import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import com.monstahhh.croniserver.plugin.advancements.enums.AdvancementEnum;
import eu.endercentral.crazy_advancements.Advancement;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class OnExp implements Listener {

    @EventHandler
    public void onPlayerLevelChange(PlayerLevelChangeEvent event) {
        if (event.getNewLevel() >= 100) {
            Advancement advancement = AdvancementEnum.POUNDS.getAdvancement();
            CustomAdvancements.grantAdvancement(event.getPlayer(), advancement);
        }
    }
}
