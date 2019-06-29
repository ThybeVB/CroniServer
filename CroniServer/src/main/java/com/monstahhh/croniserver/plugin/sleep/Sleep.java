package com.monstahhh.croniserver.plugin.sleep;

import com.monstahhh.croniserver.plugin.sleep.events.BedEvents;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Sleep {

    private JavaPlugin _plugin;

    public Sleep (JavaPlugin plugin) {
        _plugin = plugin;
    }

    public void enable () {
        _plugin.getServer().getPluginManager().registerEvents(new BedEvents(), _plugin);

        _plugin.getLogger().log(Level.INFO, "Enabled Sleep");
    }

    public void disable () {
        _plugin.getLogger().log(Level.INFO, "Disabled Sleep Extension");
    }
}
