package com.monstahhh.croniserver.plugin.advancements;

import com.monstahhh.croniserver.plugin.advancements.events.OnCraft;
import com.monstahhh.croniserver.plugin.advancements.events.PlayerServerUpdate;
import eu.endercentral.crazy_advancements.*;
import eu.endercentral.crazy_advancements.manager.AdvancementManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomAdvancements {

    public static JavaPlugin _plugin;
    public static AdvancementManager _manager;

    public CustomAdvancements(JavaPlugin plugin) {
        _plugin = plugin;
    }

    public void enable () {
        this.createAdvancements();

        _plugin.getServer().getPluginManager().registerEvents(new OnCraft(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new PlayerServerUpdate(), _plugin);
    }

    private void createAdvancements() {
        _manager = CrazyAdvancements.getNewAdvancementManager();

        AdvancementDisplay rootDisplay = new AdvancementDisplay(Material.EMERALD_BLOCK, "CroniServer Advancements", "Made by you", AdvancementDisplay.AdvancementFrame.TASK, false, false, AdvancementVisibility.ALWAYS);
        rootDisplay.setBackgroundTexture("textures/block/sand.png");
        Advancement root = new Advancement(null, new NameKey("croniserver", "root"), rootDisplay);

        AdvancementDisplay childrenDisplay = new AdvancementDisplay(Material.COOKIE, "Cookies!", "Cookietastic", AdvancementDisplay.AdvancementFrame.TASK, true, true, AdvancementVisibility.VANILLA);
        childrenDisplay.setCoordinates(1, 1);
        Advancement craftACookie = new Advancement(root, new NameKey("croniserver", "craftacookie"), childrenDisplay);

        _manager.addAdvancement(root, craftACookie);
    }

    public void disable () {
        for (Player p : _manager.getPlayers()) {
            _manager.saveProgress(p, "croniserver");
            _manager.removePlayer(p);
        }

        System.out.println("[CroniServer] Disabled Advancements");
    }
}
