package com.monstahhh.croniserver.plugin.croniserver;

import com.monstahhh.croniserver.plugin.croniserver.commands.DistanceCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.HubCommand;
import com.monstahhh.croniserver.plugin.croniserver.events.PlayerListener;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public final class CroniServer extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        this.getCommand("hub").setExecutor(new HubCommand());
        this.getCommand("distance").setExecutor(new DistanceCommand());
        this.getCommand("pdistance").setExecutor((new DistanceCommand()));
        this.getCommand("lol").setExecutor(new DistanceCommand());

        PluginLogger logger = new PluginLogger(this);
        logger.log(new LogRecord(Level.INFO, "Loaded CroniServer Build 14/05/19"));
    }
}
