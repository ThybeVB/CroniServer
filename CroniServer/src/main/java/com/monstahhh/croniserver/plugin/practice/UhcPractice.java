package com.monstahhh.croniserver.plugin.practice;

import com.monstahhh.croniserver.plugin.croniserver.CroniServer;
import com.monstahhh.croniserver.plugin.practice.commands.UhcClearPlayers;
import com.monstahhh.croniserver.plugin.practice.commands.UhcPracticeCommand;
import com.monstahhh.croniserver.plugin.practice.commands.UhcStopCommand;
import com.monstahhh.croniserver.plugin.practice.events.GameEvents;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

public class UhcPractice {

    public static PlayerCooldownManager playerCooldownManager = new PlayerCooldownManager();
    public static HashMap<Player, Integer> playerMobsKilled = new HashMap<>();

    private static JavaPlugin plugin;

    public UhcPractice(JavaPlugin plugin) {
        UhcPractice.plugin = plugin;
    }

    public static void showEndcard(Player p, boolean survived) {
        TextChannel serverChat = DiscordSRV.getPlugin().getMainTextChannel();

        p.sendMessage(ChatColor.GREEN + "--- Game has finished ---");
        if (survived) {
            p.sendMessage(ChatColor.BLUE + "> You Survived!");
            serverChat.sendMessage(":clap: **" + p.getDisplayName() + " just finished a UHC Practice game!**").queue();
        } else {
            p.sendMessage(ChatColor.RED + "> You Died!");
            serverChat.sendMessage(":clap: **" + p.getDisplayName() + " just finished a UHC Practice game, but didn't survive.**").queue();
        }
        p.sendMessage(ChatColor.DARK_RED + "> Mobs Killed: " + playerMobsKilled.getOrDefault(p, 0));
        p.sendMessage(ChatColor.GREEN + "-------------------------");

        playerMobsKilled.remove(p);

        p.teleport(new Location(Bukkit.getWorld("hub"), 0, 100, 0));
    }

    public void enable() {
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
