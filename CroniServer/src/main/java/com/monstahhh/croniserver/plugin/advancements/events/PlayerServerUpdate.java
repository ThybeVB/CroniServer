package com.monstahhh.croniserver.plugin.advancements.events;

import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import com.monstahhh.croniserver.plugin.advancements.enums.AdvancementEnum;
import eu.endercentral.crazy_advancements.Advancement;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerServerUpdate implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPermission("croniserver.advancements")) {
            return;
        }
        Bukkit.getScheduler().runTaskLater(CustomAdvancements._plugin, () -> {
            Player p = event.getPlayer();

            CustomAdvancements._manager.loadProgress(p, CustomAdvancements.namespace);
            if (!CustomAdvancements._manager.getPlayers().contains(p)) {
                CustomAdvancements._manager.addPlayer(p);
            }
        }, 2);

        joinAdvancement(event.getPlayer());
    }

    private void joinAdvancement(Player p) {
        Advancement advancement = AdvancementEnum.BIGBOY.getAdvancement();
        int progress = CustomAdvancements._manager.getCriteriaProgress(p, advancement);
        if (progress < advancement.getCriteria()) {
            CustomAdvancements._manager.setCriteriaProgress(p, advancement, progress + 1);
            CustomAdvancements._manager.update(p);
            return;
        }
        CustomAdvancements.grantAdvancement(p, advancement);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        CustomAdvancements._manager.removePlayer(event.getPlayer());
    }
}
