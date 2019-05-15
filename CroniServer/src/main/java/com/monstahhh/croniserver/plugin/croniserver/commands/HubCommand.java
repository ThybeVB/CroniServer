package com.monstahhh.croniserver.plugin.croniserver.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("hub")) {
            Player player = (Player)sender;
            player.teleport(new Location(Bukkit.getWorld("hub"), 0, 100, 0));
        }

        if (command.getName().equalsIgnoreCase("spawn")) {
            Player player = (Player)sender;
            player.teleport(new Location(Bukkit.getWorld("world"), 3, 69, -2));
        }

        return true;
    }
}
