package com.monstahhh.croniserver.plugin.advancements;

import com.monstahhh.croniserver.plugin.advancements.enums.AdvancementEnum;
import com.monstahhh.croniserver.plugin.advancements.events.*;
import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.CrazyAdvancements;
import eu.endercentral.crazy_advancements.manager.AdvancementManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomAdvancements {

    public static JavaPlugin _plugin;
    public static AdvancementManager _manager;

    public CustomAdvancements(JavaPlugin plugin) {
        _plugin = plugin;
    }

    public static void grantAdvancement(Player p, Advancement adv) {
        if (!adv.isGranted(p)) {
            if (p.getGameMode() == GameMode.SURVIVAL) {
                _manager.grantAdvancement(p, adv);
                _manager.saveProgress(p, "croniserver");
            }
        }
    }

    public void enable() {
        _manager = CrazyAdvancements.getNewAdvancementManager();

        AdvancementEnum.registerAdvancements();

        PluginManager pluginManager = _plugin.getServer().getPluginManager();
        pluginManager.registerEvents(new OnCraft(), _plugin);
        pluginManager.registerEvents(new OnDamage(), _plugin);
        pluginManager.registerEvents(new OnExp(), _plugin);
        pluginManager.registerEvents(new OnInventory(), _plugin);
        pluginManager.registerEvents(new OnMove(), _plugin);
        pluginManager.registerEvents(new OnSleep(), _plugin);
        pluginManager.registerEvents(new PlayerServerUpdate(), _plugin);
    }

    public void disable() {
        StringBuilder s = new StringBuilder("Removed From Advancement Manager: ");
        for (Player p : _manager.getPlayers()) {
            _manager.saveProgress(p, "croniserver");
            _manager.removePlayer(p);
            s.append(p.getDisplayName());
            s.append(", ");
        }
        System.out.println(s.toString());
        System.out.println("[CroniServer] Disabled Advancements");
    }
}
