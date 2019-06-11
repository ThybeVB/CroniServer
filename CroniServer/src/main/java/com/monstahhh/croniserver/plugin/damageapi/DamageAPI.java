package com.monstahhh.croniserver.plugin.damageapi;

import com.monstahhh.croniserver.plugin.damageapi.configapi.Config;
import com.monstahhh.croniserver.plugin.damageapi.events.PlayerDamageEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public class DamageAPI {

    private JavaPlugin _plugin;
    private static PluginLogger pluginLogger;

    public static Config config;
    public static Config playerData;
    public static boolean debug = false;

    public DamageAPI (JavaPlugin plugin, PluginLogger logger) {
        _plugin = plugin;
        pluginLogger = logger;
    }

    public void enable () {

        this.setupPluginFiles();

        _plugin.getServer().getPluginManager().registerEvents(new PlayerDamageEvent(), _plugin);

        pluginLogger.log(Level.INFO, "Enabled Damage API");
    }

    public static boolean isDangerous (Player player) {

        boolean damagedBool = false;
        boolean inCombatBool = false;

        String displayName = player.getDisplayName();
        Object damaged = playerData.getConfig().get("players." + displayName + ".damaged");
        Object inCombat = playerData.getConfig().get("players." + displayName + ".inCombat");

        if (damaged != null) {
            if (damaged.toString().equals("true")) {
                damagedBool = true;
            }
        }
        if (inCombat != null) {
            if (inCombat.toString().equals("true")) {
                inCombatBool = true;
            }
        }

        if (damagedBool || inCombatBool) {
            return true;
        }

        return false;
    }

    private void setupPluginFiles () {
        config = new Config("plugins/DamageAPI", "config.yml", _plugin);
        Object debugObj = config.getConfig().get("debug");
        if (debugObj == null) {
            config.getConfig().set("debug", false);
            config.saveConfig();
        } else {
            if (debugObj.toString().equals("true")) {
                debug = true;
            }
        }

        playerData = new Config("plugins/DamageAPI", "player_data.yml", _plugin);
    }

    public static void debugLog(String str) {
        if (debug) {
            pluginLogger.log(Level.INFO, str);
        }
    }

    public void disable () {
        File file = new File("plugins/DamageAPI/player_data.yml");
        pluginLogger.log(Level.INFO, "Deleting Damage Data...");
        file.delete();

        pluginLogger.log(Level.INFO, "Disabled Damage API");
    }
}
