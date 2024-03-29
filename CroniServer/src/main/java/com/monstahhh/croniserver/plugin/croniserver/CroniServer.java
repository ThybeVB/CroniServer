package com.monstahhh.croniserver.plugin.croniserver;

import com.monstahhh.croniserver.configapi.Config;
import com.monstahhh.croniserver.plugin.croniserver.commands.DistanceCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.HomeCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.WarpCommands;
import com.monstahhh.croniserver.plugin.croniserver.events.PlayerListener;
import com.monstahhh.croniserver.plugin.croniserver.uhc.UHC;
import com.monstahhh.croniserver.plugin.dangerapi.DangerAPI;
import com.monstahhh.croniserver.plugin.sleep.Sleep;
import com.monstahhh.croniserver.sqlite.Database;
import com.monstahhh.croniserver.sqlite.SQLite;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class CroniServer extends JavaPlugin {

    public static Config playerData;
    public static JavaPlugin _plugin;
    public static Logger logger;
    public static Database _db;
    private DangerAPI dangerApi;
    private Sleep sleep;
    private UHC uhc;

    @Override
    public void onEnable() {
        logger = this.getLogger();
        playerData = new Config("plugins/CroniServer", "player_data.yml");

        _db = new SQLite(this);
        _db.load();

        _plugin = this;
        this.enableExtensions();

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.registerCommands();

        System.out.println("[CroniServer] Loaded CroniServer v" + this.getDescription().getVersion());
    }

    private void registerCommands() {
        Objects.requireNonNull(this.getCommand("hub")).setExecutor(new WarpCommands());
        Objects.requireNonNull(this.getCommand("spawn")).setExecutor(new WarpCommands());
        Objects.requireNonNull(this.getCommand("survival")).setExecutor(new WarpCommands());
        Objects.requireNonNull(this.getCommand("creative")).setExecutor(new WarpCommands());
        Objects.requireNonNull(this.getCommand("home")).setExecutor(new HomeCommand());
        Objects.requireNonNull(this.getCommand("sethome")).setExecutor(new HomeCommand());
        Objects.requireNonNull(this.getCommand("distance")).setExecutor(new DistanceCommand());
        Objects.requireNonNull(this.getCommand("pdistance")).setExecutor((new DistanceCommand()));
    }

    private void enableExtensions() {
        dangerApi = new DangerAPI(this);
        dangerApi.enable();

        sleep = new Sleep(this);
        sleep.enable();

        //uhc = new UHC(this);
        //uhc.enable();
    }

    @Override
    public void onDisable() {
        dangerApi.disable();
        sleep.disable();
        //uhc.disable();

        System.out.println("[CroniServer] Disabled CroniServer");
    }
}
