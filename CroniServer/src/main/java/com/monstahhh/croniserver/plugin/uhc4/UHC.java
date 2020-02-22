package com.monstahhh.croniserver.plugin.uhc4;

import com.monstahhh.croniserver.plugin.uhc4.events.DeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class UHC {

    private JavaPlugin _plugin;

    public UHC (JavaPlugin plugin) {
        this._plugin = plugin;
    }

    public void enable() {
        _plugin.getServer().getPluginManager().registerEvents(new DeathEvent(), _plugin);
        System.out.println("[CroniServer] Enabled UHC");
    }

    public void disable() {
        System.out.println("[CroniServer] Disabled UHC");
    }
}
