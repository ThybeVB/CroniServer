package com.monstahhh.croniserver.plugin.croniserver.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player)commandSender;

            if (command.getName().equalsIgnoreCase("home")) {
                callHome(player);
            }

            if (command.getName().equalsIgnoreCase("sethome")) {
                callSetHome(player);
            }

        } else {
            commandSender.getServer().getConsoleSender().sendMessage("Command can only be executed by Player");
        }
        return true;
    }

    private void callHome (Player player) {

    }

    private void callSetHome (Player player) {

    }
}
