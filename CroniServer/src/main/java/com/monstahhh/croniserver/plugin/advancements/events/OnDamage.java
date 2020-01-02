package com.monstahhh.croniserver.plugin.advancements.events;

import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import com.monstahhh.croniserver.plugin.advancements.enums.AdvancementEnum;
import eu.endercentral.crazy_advancements.Advancement;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
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

        if (event.getEntity() instanceof Creeper) {
            if (event.getDamager() instanceof Player) {
                if (event.getEntity().getLocation().getY() < 32) {
                    Advancement advancement = AdvancementEnum.CREEPER.getAdvancement();
                    CustomAdvancements.grantAdvancement((Player) event.getDamager(), advancement);
                }
            }
        }

        if (event.getEntity() instanceof PigZombie) {
            if (event.getDamager() instanceof Player) {
                Advancement advancement = AdvancementEnum.ITWASATTHISMOMENT.getAdvancement();
                CustomAdvancements.grantAdvancement((Player) event.getDamager(), advancement);
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

            if (p.getKiller().getDisplayName().equalsIgnoreCase("ongoaviv")) {
                Advancement advancement = AdvancementEnum.SHALOM.getAdvancement();
                CustomAdvancements.grantAdvancement(p, advancement);
            }

            if (p.getKiller().getInventory().getItemInMainHand().getType().toString().endsWith("SHOVEL")) {
                Advancement advancement = AdvancementEnum.YOUSUCK.getAdvancement();
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
        if (xPos >= 880 && xPos <= 1016) {
            if (zPos >= -87 && zPos <= 44) {
                Advancement advancement = AdvancementEnum.SACRIFICE.getAdvancement();
                CustomAdvancements.grantAdvancement(p, advancement);
            }
        }
        if (Objects.requireNonNull(p.getLastDamageCause()).getCause() == EntityDamageEvent.DamageCause.STARVATION) {
            Advancement advancement = AdvancementEnum.AFRICA.getAdvancement();
            CustomAdvancements.grantAdvancement(p, advancement);
        }
        if (p.getWorld().getEnvironment() == World.Environment.NETHER) {
            if (Objects.requireNonNull(p.getLastDamageCause()).getCause() == EntityDamageEvent.DamageCause.LAVA) {
                Advancement advancement = AdvancementEnum.NETHERLANDS.getAdvancement();
                CustomAdvancements.grantAdvancement(p, advancement);
            }
        }


        int numbah = getNumber(5);
        //numbah = 25
    }

    int getNumber(int number) {
        //'number' is 5, because you can see above that is the param, so here it will do 5 * 5 under this
        return number / number;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.DIAMOND_ORE) {
            if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
                Advancement advancement = AdvancementEnum.WHY.getAdvancement();
                CustomAdvancements.grantAdvancement(event.getPlayer(), advancement);
            }
        }
    }
}