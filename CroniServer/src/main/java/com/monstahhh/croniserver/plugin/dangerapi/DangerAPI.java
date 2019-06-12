package com.monstahhh.croniserver.plugin.dangerapi;

import com.monstahhh.croniserver.plugin.dangerapi.configapi.Config;
import com.monstahhh.croniserver.plugin.dangerapi.events.PlayerDamageEvent;
import com.monstahhh.croniserver.plugin.dangerapi.events.PlayerDeathEvent;
import com.monstahhh.croniserver.plugin.dangerapi.events.PlayerMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public class DangerAPI {

    private JavaPlugin _plugin;
    private static PluginLogger pluginLogger;

    private static Config config;
    public static Config playerData;
    private static boolean debug = false;

    public DangerAPI (JavaPlugin plugin, PluginLogger logger) {
        _plugin = plugin;
        pluginLogger = logger;
    }

    public void enable () {

        this.setupPluginFiles();

        _plugin.getServer().getPluginManager().registerEvents(new PlayerDamageEvent(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), _plugin);
        _plugin.getServer().getPluginManager().registerEvents(new PlayerMoveEvent(), _plugin);

        pluginLogger.log(Level.INFO, "Enabled Danger API");
    }

    public static boolean isDangerous (Player player) {

        boolean damagedBool = false;
        boolean inCombatBool = false;
        boolean fallingBool = false;

        String displayName = player.getDisplayName();
        Object damaged = playerData.getConfig().get("players." + displayName + ".damaged");
        Object inCombat = playerData.getConfig().get("players." + displayName + ".inCombat");
        Object falling = playerData.getConfig().get("players." + displayName + ".falling");

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
        if (falling != null) {
            if (falling.toString().equals("true")) {
                fallingBool = true;
            }
        }

        if (damagedBool || inCombatBool || fallingBool) {
            return true;
        }

        return false;
    }

    private void setupPluginFiles () {
        config = new Config("plugins/DangerAPI", "config.yml", _plugin);
        Object debugObj = config.getConfig().get("debug");
        if (debugObj == null) {
            config.getConfig().set("debug", false);
            config.saveConfig();
        } else {
            if (debugObj.toString().equals("true")) {
                debug = true;
            }
        }

        playerData = new Config("plugins/DangerAPI", "player_data.yml", _plugin);
    }

    public static void debugLog(String str) {
        if (debug) {
            pluginLogger.log(Level.INFO, str);
        }
    }

    public void disable () {
        File file = new File("plugins/DangerAPI/player_data.yml");
        pluginLogger.log(Level.INFO, "Deleting Damage Data...");
        file.delete();

        pluginLogger.log(Level.INFO, "Disabled Danger API");
    }
}
