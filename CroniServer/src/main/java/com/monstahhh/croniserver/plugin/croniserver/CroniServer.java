package com.monstahhh.croniserver.plugin.croniserver;

import com.monstahhh.croniserver.configapi.Config;
import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import com.monstahhh.croniserver.plugin.croniserver.commands.DistanceCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.HomeCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.TpaCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.WarpCommands;
import com.monstahhh.croniserver.plugin.croniserver.events.PlayerListener;
import com.monstahhh.croniserver.plugin.dangerapi.DangerAPI;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
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
    private CustomAdvancements customAdvancements;
    private DangerAPI dangerApi;
    private Sleep sleep;
    private MrWorldWide mrWorldWide;

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
        TpaCommand tpaCommand = new TpaCommand();

        Objects.requireNonNull(this.getCommand("hub")).setExecutor(new WarpCommands());
        Objects.requireNonNull(this.getCommand("spawn")).setExecutor(new WarpCommands());
        Objects.requireNonNull(this.getCommand("survival")).setExecutor(new WarpCommands());
        Objects.requireNonNull(this.getCommand("creative")).setExecutor(new WarpCommands());
        Objects.requireNonNull(this.getCommand("home")).setExecutor(new HomeCommand());
        Objects.requireNonNull(this.getCommand("sethome")).setExecutor(new HomeCommand());
        Objects.requireNonNull(this.getCommand("tpaccept")).setExecutor(tpaCommand);
        Objects.requireNonNull(this.getCommand("tpdeny")).setExecutor(tpaCommand);
        Objects.requireNonNull(this.getCommand("tpa")).setExecutor(tpaCommand);
        Objects.requireNonNull(this.getCommand("distance")).setExecutor(new DistanceCommand());
        Objects.requireNonNull(this.getCommand("pdistance")).setExecutor((new DistanceCommand()));
    }

    private void enableExtensions() {
        customAdvancements = new CustomAdvancements(this);
        customAdvancements.enable();

        dangerApi = new DangerAPI(this);
        dangerApi.enable();

        sleep = new Sleep(this);
        sleep.enable();

        mrWorldWide = new MrWorldWide(this);
        mrWorldWide.enable();
    }

    @Override
    public void onDisable() {
        customAdvancements.disable();
        dangerApi.disable();
        sleep.disable();
        //practice.disable();
        mrWorldWide.disable();

        System.out.println("[CroniServer] Disabled CroniServer");
    }
}
