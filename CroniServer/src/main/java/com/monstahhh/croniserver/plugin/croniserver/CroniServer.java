package com.monstahhh.croniserver.plugin.croniserver;

import com.monstahhh.croniserver.configapi.Config;
import com.monstahhh.croniserver.plugin.advancements.CustomAdvancements;
import com.monstahhh.croniserver.plugin.croniserver.commands.DistanceCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.HomeCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.WarpCommands;
import com.monstahhh.croniserver.plugin.croniserver.events.PlayerListener;
import com.monstahhh.croniserver.plugin.dangerapi.DangerAPI;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import com.monstahhh.croniserver.plugin.practice.UhcPractice;
import com.monstahhh.croniserver.plugin.sleep.Sleep;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class CroniServer extends JavaPlugin {

    public static Config playerData;

    public static Logger logger;

    private CustomAdvancements customAdvancements;
    private DangerAPI dangerApi;
    private Sleep sleep;
    private UhcPractice practice;
    private MrWorldWide mrWorldWide;

    @Override
    public void onEnable() {
        logger = this.getLogger();
        playerData = new Config("plugins/CroniServer", "player_data.yml");

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
        customAdvancements = new CustomAdvancements(this);
        customAdvancements.enable();

        dangerApi = new DangerAPI(this);
        dangerApi.enable();

        sleep = new Sleep(this);
        sleep.enable();

        practice = new UhcPractice(this);
        practice.enable();

        mrWorldWide = new MrWorldWide(this);
        mrWorldWide.enable();
    }

    @Override
    public void onDisable() {
        customAdvancements.disable();
        dangerApi.disable();
        sleep.disable();
        practice.disable();
        mrWorldWide.disable();

        System.out.println("[CroniServer] Disabled CroniServer");
    }
}
