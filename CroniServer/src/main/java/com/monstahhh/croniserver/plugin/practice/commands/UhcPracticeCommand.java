package com.monstahhh.croniserver.plugin.practice.commands;

import com.monstahhh.croniserver.plugin.practice.PlayerCooldownManager;
import com.monstahhh.croniserver.plugin.practice.UhcPractice;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class UhcPracticeCommand implements CommandExecutor {

    private Random random = new Random();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (command.getName().equalsIgnoreCase("uhcpractice")) {
            Player p = (Player) commandSender;
            if (p.hasPermission("croniserver.uhc.practice")) {
                if (UhcPractice.playerCooldownManager.isPlaying(p)) {
                    p.sendMessage(ChatColor.DARK_RED + "You are already in-game");
                } else {
                    if (!PlayerCooldownManager.hasPlayed(p)) {
                        World world = Bukkit.getWorld("uhcpractice");
                        if (world != null) {
                            UhcPractice.playerCooldownManager.startGame(p);
                            p.teleport(getSpawnLocation(world));
                        }
                    } else {
                        p.sendMessage(ChatColor.DARK_RED + "You have already played today. Try again tommorow.");
                    }
                }
            } else {
                p.sendMessage(ChatColor.DARK_RED + "You do not have permissions to go to this world.");
                p.teleport(new Location(Bukkit.getWorld("hub"), 0, 100, 0));
            }
            return true;
        }
        return false;
    }

    private Location getSpawnLocation (World world) {
        int x = getRandomCoord();
        int z = getRandomCoord();
        Location loc = new Location(world, x, world.getHighestBlockAt(x, z).getY(), z);
        if (loc.getBlock().getBiome().name().endsWith("OCEAN")) {
            getSpawnLocation(world);
        }
        return loc;
    }

    private int getRandomCoord () {
        return random.nextInt(5000 + 1 - -5000) + -5000;
    }
}