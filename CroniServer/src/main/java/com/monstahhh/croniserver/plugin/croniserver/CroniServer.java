package com.monstahhh.croniserver.plugin.croniserver;

import com.monstahhh.croniserver.plugin.croniserver.commands.DistanceCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.HomeCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.InfoCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.WarpCommands;
import com.monstahhh.croniserver.plugin.croniserver.events.PlayerListener;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import com.monstahhh.croniserver.plugin.sleep.Sleep;
import com.monstahhh.croniserver.plugin.dangerapi.DangerAPI;
import com.monstahhh.croniserver.plugin.dangerapi.configapi.Config;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public final class CroniServer extends JavaPlugin {

    public static Config playerData;

    public static String version = null;
    public static String author = null;
    public static int onlinePlayers = 0;
    public static int maxPlayers = 0;

    private DangerAPI dangerApi;
    private Sleep sleep;
    private MrWorldWide mrWorldWide;

    @Override
    public void onEnable() {

        playerData = new Config("plugins/CroniServer", "player_data.yml", this);

        dangerApi = new DangerAPI(this);
        dangerApi.enable();

        sleep = new Sleep(this);
        sleep.enable();

        mrWorldWide = new MrWorldWide(this);
        mrWorldWide.enable();

        version = this.getDescription().getVersion();
        author = (this.getDescription().getAuthors().toArray())[0].toString();
        onlinePlayers = this.getServer().getOnlinePlayers().size();
        maxPlayers = this.getServer().getMaxPlayers();

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getCommand("hub").setExecutor(new WarpCommands());
        this.getCommand("spawn").setExecutor(new WarpCommands());
        this.getCommand("home").setExecutor(new HomeCommand());
        this.getCommand("sethome").setExecutor(new HomeCommand());
        this.getCommand("distance").setExecutor(new DistanceCommand());
        this.getCommand("pdistance").setExecutor((new DistanceCommand()));
        this.getCommand("lol").setExecutor(new DistanceCommand());
        this.getCommand("crinfo").setExecutor(new InfoCommand());

        this.getLogger().log(new LogRecord(Level.INFO, "Loaded CroniServer v" + version));
    }

    @Override
    public void onDisable () {

        dangerApi.disable();
        sleep.disable();
        mrWorldWide.disable();

        this.getLogger().log(new LogRecord(Level.INFO, "Disabled CroniServer"));
    }
}
