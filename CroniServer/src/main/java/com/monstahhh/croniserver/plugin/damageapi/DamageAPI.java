package com.monstahhh.croniserver.plugin.damageapi;

import com.monstahhh.croniserver.plugin.damageapi.events.PlayerDamageEvent;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class DamageAPI {

    private JavaPlugin _plugin;
    private PluginLogger pluginLogger;

    public DamageAPI (JavaPlugin plugin, PluginLogger logger) {
        _plugin = plugin;
        pluginLogger = logger;
    }

    public void enable () {
        _plugin.getServer().getPluginManager().registerEvents(new PlayerDamageEvent(), _plugin);

        pluginLogger.log(Level.INFO, "Enabled Damage API");
    }

    public void disable () {

        pluginLogger.log(Level.INFO, "Disabled Damage API");
        _plugin = null;
    }
}
