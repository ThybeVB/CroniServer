package com.monstahhh.croniserver.plugin.sleep;

import com.monstahhh.croniserver.plugin.croniserver.CroniServer;
import com.monstahhh.croniserver.plugin.sleep.events.BedEvents;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Sleep {

    private JavaPlugin plugin;

    public Sleep(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void enable() {
        plugin.getServer().getPluginManager().registerEvents(new BedEvents(), plugin);
        CroniServer.logger.log(Level.INFO, "Enabled Sleep");
    }

    public void disable() {
        System.out.println("Disabled Sleep");
    }
}
