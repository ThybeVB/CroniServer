package com.monstahhh.croniserver.plugin.practice.commands;

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
                    World world = Bukkit.getWorld("uhcpractice");
                    if (world != null) {
                        int x = random.nextInt(5000 + 1 - -5000) + -5000;
                        int z = random.nextInt(5000 + 1 - -5000) + -5000;
                        UhcPractice.playerCooldownManager.startGame(p);
                        p.teleport(new Location(world, x, world.getHighestBlockAt(x, z).getY(), z));
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
}