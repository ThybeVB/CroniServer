package com.monstahhh.croniserver.plugin.advancements.events;

import com.monstahhh.croniserver.plugin.advancements.enums.AdvancementEnum;
import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import eu.endercentral.crazy_advancements.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnMove implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();

        double xPos = Math.round(p.getLocation().getX());
        double yPos = Math.round(p.getLocation().getY());
        double zPos = Math.round(p.getLocation().getZ());
        if (xPos == -173 || xPos == -174) {
            if (zPos == -474) {
                if (yPos == 81) {
                    Advancement advancement = AdvancementEnum.GUAKAHOUSE.getAdvancement();
                    CustomAdvancements.grantAdvancement(p, advancement);
                }
            }
        }
    }
}
