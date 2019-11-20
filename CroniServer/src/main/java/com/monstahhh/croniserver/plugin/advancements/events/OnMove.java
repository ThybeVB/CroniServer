package com.monstahhh.croniserver.plugin.advancements.events;

import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import com.monstahhh.croniserver.plugin.advancements.enums.AdvancementEnum;
import eu.endercentral.crazy_advancements.Advancement;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public class OnMove implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();

        double xPos = Math.round(p.getLocation().getX());
        double yPos = Math.round(p.getLocation().getY());
        double zPos = Math.round(p.getLocation().getZ());
        if (xPos == -173 || xPos == -174) {
            if (zPos == 474) {
                if (yPos == 81) {
                    Advancement advancement = AdvancementEnum.GUAKAHOUSE.getAdvancement();
                    CustomAdvancements.grantAdvancement(p, advancement);
                }
            }
        }

        if (yPos >= 2000) {
            Advancement advancement = AdvancementEnum.FALLINGUP.getAdvancement();
            CustomAdvancements.grantAdvancement(p, advancement);
        }
    }

    @EventHandler
    public void onPlayerInteractEvent(org.bukkit.event.player.PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (player.getInventory().getItemInMainHand().getType().equals(Material.POTION) && player.getInventory().getItemInMainHand().getData().getData() == PotionEffectType.SPEED.getId()) {
            Advancement advancement = AdvancementEnum.LUDICROUS.getAdvancement();
            CustomAdvancements.grantAdvancement(e.getPlayer(), advancement);
        }
    }
}