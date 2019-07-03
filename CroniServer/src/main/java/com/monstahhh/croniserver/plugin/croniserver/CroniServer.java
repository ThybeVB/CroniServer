package com.monstahhh.croniserver.plugin.croniserver;

import com.monstahhh.croniserver.plugin.croniserver.commands.DistanceCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.HomeCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.InfoCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.WarpCommands;
import com.monstahhh.croniserver.plugin.croniserver.events.PlayerListener;
import com.monstahhh.croniserver.plugin.dangerapi.DangerAPI;
import com.monstahhh.croniserver.plugin.dangerapi.configapi.Config;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import com.monstahhh.croniserver.plugin.sleep.Sleep;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class CroniServer extends JavaPlugin {

    public static Config playerData;

    public static String version = null;
    public static String author = null;

    private DangerAPI dangerApi;
    private Sleep sleep;
    private MrWorldWide mrWorldWide;

    public static Logger logger;

    @Override
    public void onEnable() {

        logger = this.getLogger();

        version = this.getDescription().getVersion();
        author = (this.getDescription().getAuthors().toArray())[0].toString();

        playerData = new Config("plugins/CroniServer", "player_data.yml", this);

        this.enableExtensions();

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        this.registerCommands();

        System.out.println("[CroniServer] Loaded CroniServer v" + version);
    }

    private void registerCommands() {
        this.getCommand("hub").setExecutor(new WarpCommands());
        this.getCommand("spawn").setExecutor(new WarpCommands());
        this.getCommand("home").setExecutor(new HomeCommand());
        this.getCommand("sethome").setExecutor(new HomeCommand());
        this.getCommand("distance").setExecutor(new DistanceCommand());
        this.getCommand("pdistance").setExecutor((new DistanceCommand()));
        this.getCommand("lol").setExecutor(new DistanceCommand());
        this.getCommand("crinfo").setExecutor(new InfoCommand());
    }

    private void enableExtensions() {
        dangerApi = new DangerAPI(this);
        dangerApi.enable();

        mrWorldWide = new MrWorldWide(this);
        mrWorldWide.enable();

        sleep = new Sleep(this);
        sleep.enable();
    }

    @Override
    public void onDisable() {

        dangerApi.disable();
        mrWorldWide.disable();
        sleep.disable();

        System.out.println("[CroniServer] Disabled CroniServer");
    }
}
