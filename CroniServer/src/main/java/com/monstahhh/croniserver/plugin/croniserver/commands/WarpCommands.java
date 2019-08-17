package com.monstahhh.croniserver.plugin.croniserver.commands;

import com.monstahhh.croniserver.plugin.dangerapi.DangerAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WarpCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            if (sender.hasPermission("croniserver.command.warp")) {
                if (((Player) sender).getWorld().getName().equalsIgnoreCase("world")) {
                    if (DangerAPI.isDangerous((Player) sender)) {
                        sender.sendMessage(ChatColor.DARK_RED + "You are in an unsafe state!");
                    } else {
                        this.doTp((Player) sender, command);
                    }
                } else {
                    this.doTp((Player) sender, command);
                }
            } else {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.DARK_RED + "Nice try, but no :)");
            }
        }

        return true;
    }

    private void doTp(Player player, Command command) {
        if (command.getName().equalsIgnoreCase("hub")) {
            player.teleport(new Location(Bukkit.getWorld("hub"), 0, 100, 0));
        }
        if (command.getName().equalsIgnoreCase("spawn")) {
            player.teleport(new Location(player.getWorld(), 3, 69, -2));
        }
    }
}