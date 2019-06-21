package com.monstahhh.croniserver.plugin.sleep;

import com.monstahhh.croniserver.plugin.sleep.events.BedEvents;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Sleep {

    private JavaPlugin _plugin;
    private PluginLogger pluginLogger;

    public Sleep (JavaPlugin plugin, PluginLogger logger) {
        _plugin = plugin;
        pluginLogger = logger;
    }

    public void enable () {
        _plugin.getServer().getPluginManager().registerEvents(new BedEvents(), _plugin);

        pluginLogger.log(Level.INFO, "Enabled Sleep");
    }

    public void disable () {
        pluginLogger.log(Level.INFO, "Disabled Sleep Extension");
    }
}
