package com.monstahhh.croniserver.plugin.advancements.events;

import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import com.monstahhh.croniserver.plugin.advancements.enums.AdvancementEnum;
import eu.endercentral.crazy_advancements.Advancement;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OnInventory implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (p.getInventory().getItemInMainHand().getType() == Material.DRAGON_EGG && p.getInventory().getItemInOffHand().getType() == Material.DRAGON_EGG) {
            Advancement advancement = AdvancementEnum.HACKEDBALLS.getAdvancement();
            CustomAdvancements.grantAdvancement(p, advancement);
        }
    }
}