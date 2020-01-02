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

public class UhcPracticeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("uhcpractice")) {
            if (commandSender instanceof Player) {
                Player p = (Player) commandSender;
                if (p.hasPermission("croniserver.uhc.practice")) {
                    if (UhcPractice.playerCooldownManager.isPlaying(p)) {
                        p.sendMessage(ChatColor.DARK_RED + "You are already in-game");
                    } else {
                        if (!UhcClearPlayers.hasPlayed(p)) {
                            if (args.length == 0) {
                                p.sendMessage(ChatColor.BLUE + "Are you sure you want to start a UHC Session?");
                                p.sendMessage(ChatColor.BLUE + "Use " + ChatColor.GREEN + "/uhcpractice confirm" + ChatColor.BLUE + " to confirm.");
                                p.sendMessage(ChatColor.BLUE + "Note: You can only play once per day.");
                            } else if (args[0].equalsIgnoreCase("confirm")) {
                                World world = Bukkit.getWorld("uhcpractice");
                                if (world != null) {
                                    UhcPractice.playerCooldownManager.startGame(p);
                                }
                            }
                        } else {
                            p.sendMessage(ChatColor.DARK_RED + "You have already played today. Try again tommorow.");
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "You do not have permissions to go to this world.");
                    p.teleport(new Location(Bukkit.getWorld("hub"), 0, 100, 0));
                }
            } else {
                commandSender.sendMessage("Command can only be executed by Player");
            }
            return true;
        }
        return false;
    }
}