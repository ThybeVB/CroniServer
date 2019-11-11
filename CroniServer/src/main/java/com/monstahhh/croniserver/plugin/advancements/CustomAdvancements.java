package com.monstahhh.croniserver.plugin.advancements;

import com.monstahhh.croniserver.plugin.advancements.events.OnCraft;
import com.monstahhh.croniserver.plugin.advancements.events.OnDeath;
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

    public static void grantAdvancement(Player p, Advancement adv) {
        if (!adv.isGranted(p)) {
            _manager.grantAdvancement(p, adv);
            _manager.saveProgress(p, "croniserver");
        }
    }

    public void enable() {
        this.createAdvancements();

        _plugin.getServer().getPluginManager().registerEvents(new OnCraft(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new OnDeath(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new PlayerServerUpdate(), _plugin);
    }

    private void createAdvancements() {
        _manager = CrazyAdvancements.getNewAdvancementManager();

        AdvancementDisplay rootDisplay = new AdvancementDisplay(Material.EMERALD_BLOCK, "CroniServer Advancements", "Made by you!", AdvancementDisplay.AdvancementFrame.TASK, false, false, AdvancementVisibility.ALWAYS);
        rootDisplay.setBackgroundTexture("textures/block/sand.png");
        Advancement root = new Advancement(null, new NameKey("croniserver", "root"), rootDisplay);

        AdvancementDisplay cookieDisplay = new AdvancementDisplay(Material.COOKIE, "Cookies!", "Cookietastic\n-Troloze", AdvancementDisplay.AdvancementFrame.TASK, true, true, AdvancementVisibility.VANILLA);
        cookieDisplay.setCoordinates(1, 1);
        Advancement craftACookie = new Advancement(root, new NameKey("croniserver", "craftacookie"), cookieDisplay);

        AdvancementDisplay shovelDisplay = new AdvancementDisplay(Material.IRON_SHOVEL, "You Suck", "Die to a Shovel\n-iiiomiii", AdvancementDisplay.AdvancementFrame.TASK, true, true, AdvancementVisibility.VANILLA);
        shovelDisplay.setCoordinates(1, -1);
        Advancement getKilledByAShovel = new Advancement(root, new NameKey("croniserver", "getkilledbyshovel"), shovelDisplay);

        AdvancementDisplay nurseryDisplay = new AdvancementDisplay(Material.COOKED_CHICKEN, "Have A Nice Day!", "One-Way Freedom\n-MyZone03", AdvancementDisplay.AdvancementFrame.TASK, true, true, AdvancementVisibility.VANILLA);
        nurseryDisplay.setCoordinates(1, 0);
        Advancement getKilledByTheNursery = new Advancement(root, new NameKey("croniserver", "killedbynursery"), nurseryDisplay);

        AdvancementDisplay craftHayDisplay = new AdvancementDisplay(Material.HAY_BLOCK, "Businessman", "Craft a Hay Block\n-Monstahhh", AdvancementDisplay.AdvancementFrame.TASK, true, true, AdvancementVisibility.VANILLA);
        craftHayDisplay.setCoordinates(-1, 1);
        Advancement craftHay = new Advancement(root, new NameKey("croniserver", "businessman"), craftHayDisplay);

        AdvancementDisplay netherlandsDisplay = new AdvancementDisplay(Material.ORANGE_WOOL, "Welkom in Nederland", "Ga nu dood.\n-Darmuth", AdvancementDisplay.AdvancementFrame.TASK, true, true, AdvancementVisibility.VANILLA);
        netherlandsDisplay.setCoordinates(-1, 1);
        Advancement netherlands = new Advancement(root, new NameKey("croniserver", "netherlands"), netherlandsDisplay);

        _manager.addAdvancement(root, craftACookie, getKilledByAShovel, getKilledByTheNursery, craftHay, netherlands);
    }

    public void disable() {
        for (Player p : _manager.getPlayers()) {
            _manager.saveProgress(p, "croniserver");
            _manager.removePlayer(p);
        }

        System.out.println("[CroniServer] Disabled Advancements");
    }
}
