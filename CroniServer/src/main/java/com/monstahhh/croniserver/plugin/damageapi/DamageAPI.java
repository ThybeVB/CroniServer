package com.monstahhh.croniserver.plugin.damageapi;

import com.monstahhh.croniserver.plugin.damageapi.configapi.Config;
import com.monstahhh.croniserver.plugin.damageapi.events.PlayerDamageEvent;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Level;

public class DamageAPI {

    private JavaPlugin _plugin;
    private PluginLogger pluginLogger;

    private static Config config;
    private static boolean debug = false;

    public DamageAPI (JavaPlugin plugin, PluginLogger logger) {
        _plugin = plugin;
        pluginLogger = logger;
    }

    public void enable () {

        this.setupPluginFiles();

        _plugin.getServer().getPluginManager().registerEvents(new PlayerDamageEvent(), _plugin);

        pluginLogger.log(Level.INFO, "Enabled Damage API");
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
    }

    public void disable () {
        pluginLogger.log(Level.INFO, "Disabled Damage API");
    }
}
