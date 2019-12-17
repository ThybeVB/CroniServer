package com.monstahhh.croniserver.plugin.advancements.events;

import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import com.monstahhh.croniserver.plugin.advancements.enums.AdvancementEnum;
import eu.endercentral.crazy_advancements.Advancement;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.Objects;

public class OnInventory implements Listener {

    @EventHandler
    public void onInventory(InventoryEvent event) {
        if (event.getInventory().getHolder() instanceof Player) {
            Player p = (Player) event.getInventory().getHolder();
            PlayerInventory pInv = (PlayerInventory) event.getInventory();
            if (Objects.requireNonNull(pInv.getHelmet()).getType() == Material.ZOMBIE_HEAD) {
                Advancement advancement = AdvancementEnum.AGIRLHASNONAME.getAdvancement();
                CustomAdvancements.grantAdvancement(p, advancement);
            }

            if (p.getInventory().getItemInMainHand().getType() == Material.DRAGON_EGG && p.getInventory().getItemInOffHand().getType() == Material.DRAGON_EGG) {
                Advancement advancement = AdvancementEnum.HACKEDBALLS.getAdvancement();
                CustomAdvancements.grantAdvancement(p, advancement);
            }
        }
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if (event.getItem().getItemStack().getType() == Material.EMERALD_ORE) {
                Advancement advancement = AdvancementEnum.SHINYSTONE.getAdvancement();
                CustomAdvancements.grantAdvancement(p, advancement);
            }
            if (event.getItem().getItemStack().getType() == Material.AZURE_BLUET) {
                Advancement advancement = AdvancementEnum.GUYSIFOUNDTHEFLOWER.getAdvancement();
                CustomAdvancements.grantAdvancement(p, advancement);
            }
            if (event.getItem().getItemStack().getType() == Material.MUSIC_DISC_STAL) {
                Advancement advancement = AdvancementEnum.STAL.getAdvancement();
                CustomAdvancements.grantAdvancement(p, advancement);
            }
        }
    }
}