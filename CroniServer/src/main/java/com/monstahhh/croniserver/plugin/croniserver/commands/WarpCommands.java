package com.monstahhh.croniserver.plugin.croniserver.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (sender.hasPermission("croniserver.command.warp")) {
                    if (command.getName().equalsIgnoreCase("hub")) {
                        Player player = (Player) sender;
                        player.teleport(new Location(Bukkit.getWorld("hub"), 0, 100, 0));
                    }
                    if (command.getName().equalsIgnoreCase("spawn")) {
                        Player player = (Player) sender;
                        player.teleport(new Location(player.getWorld(), 3, 69, -2));
                    }
            } else {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.DARK_RED + "Nice try, but no :)");
            }
        }

        return true;
    }
}
