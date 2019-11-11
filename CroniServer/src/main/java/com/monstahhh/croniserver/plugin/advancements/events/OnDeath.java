package com.monstahhh.croniserver.plugin.advancements.events;

import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.NameKey;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

public class OnDeath implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();
        if (p.getKiller() != null) {
            System.out.println(p.getKiller().getItemOnCursor().getType().toString());
            if (p.getKiller().getInventory().getItemInMainHand().getType().toString().endsWith("SHOVEL")) {
                Advancement advancement = CustomAdvancements._manager.getAdvancement(new NameKey("croniserver", "getkilledbyshovel"));
                CustomAdvancements.grantAdvancement(p, advancement);
                return;
            }
        }

        double xPos = p.getLocation().getX();
        double yPos = p.getLocation().getY();
        double zPos = p.getLocation().getZ();
        if (xPos >= 203 && xPos <= 210) {
            if (zPos <= -275 && zPos >= -281) {
                Advancement advancement = CustomAdvancements._manager.getAdvancement(new NameKey("croniserver", "killedbynursery"));
                CustomAdvancements.grantAdvancement(p, advancement);
            }
        }
        if (xPos == -175 || xPos == -174) {
            if (zPos == -474) {
                if (yPos == 81) {
                    Advancement advancement = CustomAdvancements._manager.getAdvancement(new NameKey("croniserver", "guakahouse"));
                    CustomAdvancements.grantAdvancement(p, advancement);
                }
            }
        }

        if (p.getWorld().getEnvironment() == World.Environment.NETHER) {
            if (Objects.requireNonNull(p.getLastDamageCause()).getCause() == EntityDamageEvent.DamageCause.LAVA) {
                Advancement advancement = CustomAdvancements._manager.getAdvancement(new NameKey("croniserver", "netherlands"));
                CustomAdvancements.grantAdvancement(p, advancement);
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {

    }
}
