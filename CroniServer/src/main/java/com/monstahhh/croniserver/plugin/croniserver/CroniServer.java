package com.monstahhh.croniserver.plugin.croniserver;

import com.monstahhh.croniserver.plugin.croniserver.commands.DistanceCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.InfoCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.WarpCommands;
import com.monstahhh.croniserver.plugin.croniserver.events.PlayerListener;
import com.monstahhh.croniserver.plugin.damageapi.DamageAPI;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public final class CroniServer extends JavaPlugin {

    PluginLogger logger = new PluginLogger(this);

    public static String version = null;
    public static String author = null;
    public static int onlinePlayers = 0;
    public static int maxPlayers = 0;

    private DamageAPI damageAPI;

    @Override
    public void onEnable() {

        PluginLogger logger = new PluginLogger(this);
        damageAPI = new DamageAPI(this, logger);

        version = this.getDescription().getVersion();
        author = (this.getDescription().getAuthors().toArray())[0].toString();
        onlinePlayers = this.getServer().getOnlinePlayers().size();
        maxPlayers = this.getServer().getMaxPlayers();

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getCommand("hub").setExecutor(new WarpCommands());
        this.getCommand("spawn").setExecutor(new WarpCommands());
        this.getCommand("distance").setExecutor(new DistanceCommand());
        this.getCommand("pdistance").setExecutor((new DistanceCommand()));
        this.getCommand("lol").setExecutor(new DistanceCommand());
        this.getCommand("crinfo").setExecutor(new InfoCommand());

        logger.log(new LogRecord(Level.INFO, "Loaded CroniServer v" + version));
    }

    @Override
    public void onDisable () {

        damageAPI.disable();

        onlinePlayers = 0;
        maxPlayers = 0;
        version = null;
        author = null;
        logger = null;

        logger.log(new LogRecord(Level.INFO, "Disabled CroniServer"));
    }
}
