package com.monstahhh.croniserver.plugin.croniserver;

import com.monstahhh.croniserver.configapi.Config;
import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import com.monstahhh.croniserver.plugin.croniserver.commands.DistanceCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.HomeCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.WarpCommands;
import com.monstahhh.croniserver.plugin.croniserver.events.PlayerListener;
import com.monstahhh.croniserver.plugin.dangerapi.DangerAPI;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import com.monstahhh.croniserver.plugin.sleep.Sleep;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class CroniServer extends JavaPlugin {

    public static Config playerData;

    public static Logger logger;

    private DangerAPI dangerApi;
    private Sleep sleep;
    private CustomAdvancements customAdvancements;
    private MrWorldWide mrWorldWide;

    @Override
    public void onEnable() {
        logger = this.getLogger();
        playerData = new Config("plugins/CroniServer", "player_data.yml", this);

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
        Objects.requireNonNull(this.getCommand("lol")).setExecutor(new DistanceCommand());
    }

    private void enableExtensions() {
        mrWorldWide = new MrWorldWide(this);
        mrWorldWide.enable();

        customAdvancements = new CustomAdvancements(this);
        customAdvancements.enable();

        dangerApi = new DangerAPI(this);
        dangerApi.enable();

        sleep = new Sleep(this);
        sleep.enable();
    }

    @Override
    public void onDisable() {
        mrWorldWide.disable();
        customAdvancements.disable();
        dangerApi.disable();
        sleep.disable();

        System.out.println("[CroniServer] Disabled CroniServer");
    }
}
