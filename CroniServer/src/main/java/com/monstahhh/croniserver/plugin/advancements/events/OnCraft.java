package com.monstahhh.croniserver.plugin.advancements.events;

import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.NameKey;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class OnCraft implements Listener {
    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        Player p = (Player)event.getWhoClicked();

        if (event.getCurrentItem() != null) {
            if (event.getCurrentItem().getType() == Material.COOKIE) {
                Advancement advancement = CustomAdvancements._manager.getAdvancement(new NameKey("croniserver", "craftacookie"));
                CustomAdvancements._manager.grantAdvancement(p, advancement);
                CustomAdvancements._manager.saveProgress(p, "croniserver");
            }
        }
    }
}
