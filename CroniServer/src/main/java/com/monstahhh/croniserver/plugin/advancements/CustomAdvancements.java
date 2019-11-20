package com.monstahhh.croniserver.plugin.advancements;

import com.monstahhh.croniserver.plugin.advancements.enums.AdvancementEnum;
import com.monstahhh.croniserver.plugin.advancements.events.*;
import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.CrazyAdvancements;
import eu.endercentral.crazy_advancements.manager.AdvancementManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
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

        _plugin.getServer().getPluginManager().registerEvents(new OnCraft(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new OnDamage(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new OnExp(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new OnInventory(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new OnMove(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new OnSleep(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new PlayerServerUpdate(), _plugin);
    }

    public void disable() {
        for (Player p : _manager.getPlayers()) {
            _manager.saveProgress(p, "croniserver");
            _manager.removePlayer(p);
        }
        System.out.println("[CroniServer] Disabled Advancements");
    }
}
