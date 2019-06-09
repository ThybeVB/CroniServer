package com.monstahhh.croniserver.plugin.damageapi;

import com.monstahhh.croniserver.plugin.damageapi.configapi.Config;
import com.monstahhh.croniserver.plugin.damageapi.events.PlayerDamageEvent;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Level;

public class DamageAPI {

    private JavaPlugin _plugin;
    private PluginLogger pluginLogger;
    public static Config config;

    public DamageAPI (JavaPlugin plugin, PluginLogger logger) {
        _plugin = plugin;
        pluginLogger = logger;
    }

    public void enable () {

        this.setupPluginFiles();
        config.getConfig().set("this.is.a.location", "null");
        config.saveConfig();

        _plugin.getServer().getPluginManager().registerEvents(new PlayerDamageEvent(), _plugin);

        pluginLogger.log(Level.INFO, "Enabled Damage API");
    }

    private void setupPluginFiles () {
        config = new Config("plugins/DamageAPI", "config.yml", _plugin);
    }

    public void disable () {

        Object str = config.getConfig().get("this.is.a.location");
        pluginLogger.log(Level.INFO, str.toString());

        pluginLogger.log(Level.INFO, "Disabled Damage API");
        _plugin = null;
    }
}
