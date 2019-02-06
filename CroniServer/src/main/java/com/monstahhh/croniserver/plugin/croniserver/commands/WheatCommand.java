package com.monstahhh.croniserver.plugin.croniserver.commands;

import com.monstahhh.croniserver.plugin.croniserver.events.PlayerListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WheatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("setwheat")) {
            if (sender.getName().equalsIgnoreCase("monstahhhy")) {
                PlayerListener.monstahWheat = Integer.parseInt(args[0]);
            } else if (sender.getName().equalsIgnoreCase("Guaka25")) {
                PlayerListener.guakaWheat = Integer.parseInt(args[0]);
            }
        }

        if (command.getName().equalsIgnoreCase("monstahwheat")) {
            sender.sendMessage(ChatColor.GREEN + "monstahhhy has harvested " + PlayerListener.monstahWheat + " wheat.");
        } else {
            sender.sendMessage(ChatColor.GREEN + "Guaka25 has harvested " + PlayerListener.guakaWheat + " wheat.");
        }
        return true;
    }
}
