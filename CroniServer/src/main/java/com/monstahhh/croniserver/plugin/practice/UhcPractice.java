package com.monstahhh.croniserver.plugin.practice;

import com.monstahhh.croniserver.plugin.croniserver.CroniServer;
import com.monstahhh.croniserver.plugin.practice.commands.UhcClearPlayers;
import com.monstahhh.croniserver.plugin.practice.commands.UhcPracticeCommand;
import com.monstahhh.croniserver.plugin.practice.commands.UhcStopCommand;
import com.monstahhh.croniserver.plugin.practice.events.GameEvents;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

public class UhcPractice {

    public static PlayerCooldownManager playerCooldownManager = new PlayerCooldownManager();
    public static HashMap<Player, Integer> playerMobsKilled = new HashMap<>();
    public static HashMap<Player, Boolean> inDeathProcess = new HashMap<>();

    private static JavaPlugin plugin;

    public UhcPractice(JavaPlugin plugin) {
        UhcPractice.plugin = plugin;
    }

    public static void showEndcard(Player p, boolean survived) {
        p.sendMessage(ChatColor.GREEN + "--- Game has finished ---");
        if (survived) {
            p.sendMessage(ChatColor.BLUE + "> You Survived!");
        } else {
            p.sendMessage(ChatColor.RED + "> You Died!");
        }
        p.sendMessage(ChatColor.DARK_RED + "> Mobs Killed: " + playerMobsKilled.getOrDefault(p, 0));
        p.sendMessage(ChatColor.GREEN + "-------------------------");

        Bukkit.getScheduler().runTask(plugin, () -> {
            p.getInventory().clear();
            p.damage(p.getHealth());
            regenerateChunks(p);
        });
        playerMobsKilled.remove(p);
    }

    private static void regenerateChunks(Player p) {
        BukkitWorld world = new BukkitWorld(p.getWorld());
        double x = p.getLocation().getX();
        double z = p.getLocation().getZ();

        Region selection = new CuboidRegion(BlockVector3.at(x - 250, 0, z - 250), BlockVector3.at(x + 250, 256, z + 250));

        world.regenerate(selection, WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1));
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
