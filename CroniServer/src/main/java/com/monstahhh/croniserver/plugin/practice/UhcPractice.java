package com.monstahhh.croniserver.plugin.practice;

import com.monstahhh.croniserver.plugin.croniserver.CroniServer;
import com.monstahhh.croniserver.plugin.practice.commands.UhcClearPlayers;
import com.monstahhh.croniserver.plugin.practice.commands.UhcPracticeCommand;
import com.monstahhh.croniserver.plugin.practice.commands.UhcStopCommand;
import com.monstahhh.croniserver.plugin.practice.events.GameEvents;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

public class UhcPractice {

    public static PlayerCooldownManager playerCooldownManager = null;
    public static HashMap<Player, Integer> playerMobsKilled = new HashMap<>();
    private JavaPlugin plugin;

    public UhcPractice(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void enable() {
        playerCooldownManager = new PlayerCooldownManager(plugin);
        plugin.getServer().getPluginManager().registerEvents(new GameEvents(), plugin);

        Objects.requireNonNull(plugin.getCommand("clearuhcplayers")).setExecutor(new UhcClearPlayers());
        Objects.requireNonNull(plugin.getCommand("uhcpractice")).setExecutor(new UhcPracticeCommand());
        Objects.requireNonNull(plugin.getCommand("stopuhc")).setExecutor(new UhcStopCommand());

        CroniServer.logger.log(Level.INFO, "Enabled Practice");
    }

    public void disable() {
        System.out.println("Disabled Practice");
    }
}
