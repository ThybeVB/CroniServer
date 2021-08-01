package com.monstahhh.croniserver.plugin.advancements;

import com.monstahhh.croniserver.plugin.advancements.enums.AdvancementEnum;
import com.monstahhh.croniserver.plugin.advancements.events.*;
import eu.endercentral.crazy_advancements.Advancement;
import eu.endercentral.crazy_advancements.CrazyAdvancements;
import eu.endercentral.crazy_advancements.manager.AdvancementManager;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomAdvancements {

    public static JavaPlugin _plugin;
    public static AdvancementManager _manager;

    public static String namespace = "croniserver";

    public CustomAdvancements(JavaPlugin plugin) {
        _plugin = plugin;
    }

    public static void grantAdvancement(Player p, Advancement adv) {
        if (!adv.isGranted(p)) {
            if (p.getGameMode() == GameMode.SURVIVAL) {
                if (p.hasPermission("croniserver.advancements")) {
                    _manager.grantAdvancement(p, adv);
                    _manager.saveProgress(p, namespace);

                    TextChannel serverChat = DiscordSRV.getPlugin().getMainTextChannel();
                    String advName = adv.getDisplay().getTitle().toString().split("\"")[3];
                    serverChat.sendMessage(":medal: **" + p.getName() + " has made the advancement " + advName + "**").queue();
                }
            }
        }
    }

    public void enable() {
        _manager = CrazyAdvancements.getNewAdvancementManager();
        _manager.makeAccessible(namespace);
        AdvancementEnum.registerAdvancements();

        _plugin.getServer().getPluginManager().registerEvents(new OnChat(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new OnCraft(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new OnDamage(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new OnExp(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new OnInventory(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new OnMove(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new OnSleep(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new PlayerServerUpdate(), _plugin);
    }

    public void disable() {
        _manager.resetAccessible();
        System.out.println("[CroniServer] Disabled Advancements");
    }
}
