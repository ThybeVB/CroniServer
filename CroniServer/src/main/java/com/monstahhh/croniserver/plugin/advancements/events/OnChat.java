package com.monstahhh.croniserver.plugin.advancements.events;

import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import com.monstahhh.croniserver.plugin.advancements.enums.AdvancementEnum;
import eu.endercentral.crazy_advancements.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChat implements Listener {

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (event.getMessage().startsWith("AA")) {
            Player p = event.getPlayer();

            double xPos = p.getLocation().getX();
            double zPos = p.getLocation().getZ();
            if (xPos >= 203 && xPos <= 210) {
                if (zPos <= -275 && zPos >= -281) {
                    Advancement advancement = AdvancementEnum.NURSERYRHYMES.getAdvancement();
                    CustomAdvancements.grantAdvancement(p, advancement);
                }
            }
        }
    }
}
