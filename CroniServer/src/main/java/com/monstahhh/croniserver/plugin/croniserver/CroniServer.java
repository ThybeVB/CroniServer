package com.monstahhh.croniserver.plugin.croniserver;

import com.monstahhh.croniserver.plugin.croniserver.commands.DistanceCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.HubCommand;
import com.monstahhh.croniserver.plugin.croniserver.events.PlayerListener;
import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public final class CroniServer extends JavaPlugin {

    PluginLogger logger = new PluginLogger(this);

    @Override
    public void onEnable() {

        logger.log(new LogRecord(Level.INFO, "Loading CroniServer Build 25/5/19"));

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        this.getCommand("hub").setExecutor(new HubCommand());
        this.getCommand("spawn").setExecutor(new HubCommand());
        this.getCommand("distance").setExecutor(new DistanceCommand());
        this.getCommand("pdistance").setExecutor((new DistanceCommand()));
        this.getCommand("lol").setExecutor(new DistanceCommand());

    }
    @Override
    public void onDisable () {
        logger.log(new LogRecord(Level.INFO, "Disabling CroniServer"));
    }
}
