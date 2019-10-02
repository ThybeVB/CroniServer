package com.monstahhh.croniserver.plugin.dangerapi;

import com.monstahhh.croniserver.configapi.Config;
import com.monstahhh.croniserver.plugin.croniserver.CroniServer;
import com.monstahhh.croniserver.plugin.dangerapi.events.PlayerDamageEvent;
import com.monstahhh.croniserver.plugin.dangerapi.events.PlayerDeathEvent;
import com.monstahhh.croniserver.plugin.dangerapi.events.PlayerLeaveEvent;
import com.monstahhh.croniserver.plugin.dangerapi.events.PlayerMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public class DangerAPI {

    public static Config playerData;
    private static JavaPlugin plugin;
    private static boolean debug = false;

    public DangerAPI(JavaPlugin plugin) {
        DangerAPI.plugin = plugin;
    }

    public static boolean isDangerous(Player player) {

        String displayName = player.getDisplayName();
        Object damaged = playerData.getConfig().get("players." + displayName + ".damaged");
        Object inCombat = playerData.getConfig().get("players." + displayName + ".inCombat");
        Object falling = playerData.getConfig().get("players." + displayName + ".falling");

        boolean damagedBool = false;
        if (damaged != null) {
            if (damaged.toString().equals("true")) {
                damagedBool = true;
            }
        }

        boolean inCombatBool = false;
        if (inCombat != null) {
            if (inCombat.toString().equals("true")) {
                inCombatBool = true;
            }
        }

        boolean fallingBool = false;
        if (falling != null) {
            if (falling.toString().equals("true")) {
                fallingBool = true;
            }
        }

        return damagedBool || inCombatBool || fallingBool;
    }

    public static void debugLog(String str) {
        if (debug) {
            CroniServer.logger.log(Level.INFO, str);
        }
    }

    public void enable() {

        this.setupPluginFiles();

        plugin.getServer().getPluginManager().registerEvents(new PlayerDamageEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerMoveEvent(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerLeaveEvent(), plugin);

        CroniServer.logger.log(Level.INFO, "Enabled Danger API");
    }

    private void setupPluginFiles() {
        Config config = new Config("plugins/DangerAPI", "config.yml", plugin);
        Object debugObj = config.getConfig().get("debug");
        if (debugObj == null) {
            config.getConfig().set("debug", false);
            config.saveConfig();
        } else {
            if (debugObj.toString().equals("true")) {
                debug = true;
            }
        }

        playerData = new Config("plugins/DangerAPI", "player_data.yml", plugin);
    }

    public void disable() {
        File file = new File("plugins/DangerAPI/player_data.yml");
        debugLog("Deleting Damage Data...");
        boolean deleted = file.delete();
        if (!deleted) {
            CroniServer.logger.log(Level.SEVERE, "Failed to delete Damage Data");
        }

        System.out.println("Disabled Danger API");
    }
}
