package com.monstahhh.croniserver.plugin.advancements.events;

import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import com.monstahhh.croniserver.plugin.advancements.enums.AdvancementEnum;
import eu.endercentral.crazy_advancements.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class OnCraft implements Listener {

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        Player p = (Player) event.getWhoClicked();

        if (event.getCurrentItem() != null) {
            switch (event.getCurrentItem().getType()) {
                case COOKIE:
                    Advancement cookie = AdvancementEnum.CRAFTACOOKIE.getAdvancement();
                    CustomAdvancements.grantAdvancement(p, cookie);
                    break;
                case HAY_BLOCK:
                    Advancement hay = AdvancementEnum.BUSINESSMAN.getAdvancement();
                    CustomAdvancements.grantAdvancement(p, hay);
                    break;
                case CAKE:
                    Advancement cake = AdvancementEnum.THELIE.getAdvancement();
                    CustomAdvancements.grantAdvancement(p, cake);
                    break;
            }
        }
    }
}
