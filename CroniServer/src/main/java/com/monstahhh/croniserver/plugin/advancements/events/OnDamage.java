package com.monstahhh.croniserver.plugin.advancements.events;

import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import com.monstahhh.croniserver.plugin.advancements.enums.AdvancementEnum;
import eu.endercentral.crazy_advancements.Advancement;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

public class OnDamage implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Snowball) {
                Advancement advancement = AdvancementEnum.SNOWBALL.getAdvancement();
                CustomAdvancements.grantAdvancement((Player) event.getEntity(), advancement);
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        if (p.getKiller() != null) {
            Advancement notPacifistAdvancement = AdvancementEnum.NOTPACIFIST.getAdvancement();
            CustomAdvancements.grantAdvancement(p.getKiller(), notPacifistAdvancement);

            if (p.getKiller().getDisplayName().equalsIgnoreCase("Guaka25")) {
                Advancement advancement = AdvancementEnum.GUAKAAPPROVED.getAdvancement();
                CustomAdvancements.grantAdvancement(p, advancement);
            }

            if (p.getKiller().getDisplayName().equalsIgnoreCase("Cronibet")) {
                Advancement advancement = AdvancementEnum.FURNITURYDEATH.getAdvancement();
                int progress = CustomAdvancements._manager.getCriteriaProgress(p, advancement);
                if (progress < advancement.getCriteria()) {
                    CustomAdvancements._manager.setCriteriaProgress(p, advancement, progress + 1);
                    CustomAdvancements._manager.update(p);
                } else {
                    CustomAdvancements.grantAdvancement(p, advancement);
                }
            }

            if (p.getKiller().getInventory().getItemInMainHand().getType().toString().endsWith("SHOVEL")) {
                Advancement advancement = AdvancementEnum.GETKILLEDBYSHOVEL.getAdvancement();
                CustomAdvancements.grantAdvancement(p, advancement);
                return;
            }
        }

        double xPos = p.getLocation().getX();
        double zPos = p.getLocation().getZ();
        if (xPos >= 203 && xPos <= 210) {
            if (zPos <= -275 && zPos >= -281) {
                if (p.getKiller().getDisplayName().equalsIgnoreCase("MyZone03")) {
                    Advancement advancement = AdvancementEnum.KILLEDBYNURSERY.getAdvancement();
                    CustomAdvancements.grantAdvancement(p, advancement);
                }
            }
        }

        if (p.getWorld().getEnvironment() == World.Environment.NETHER) {
            if (Objects.requireNonNull(p.getLastDamageCause()).getCause() == EntityDamageEvent.DamageCause.LAVA) {
                Advancement advancement = AdvancementEnum.NETHERLANDS.getAdvancement();
                CustomAdvancements.grantAdvancement(p, advancement);
            }
        }
    }
}