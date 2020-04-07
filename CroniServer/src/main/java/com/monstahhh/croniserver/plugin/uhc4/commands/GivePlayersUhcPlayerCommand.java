package com.monstahhh.croniserver.plugin.uhc4.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GivePlayersUhcPlayerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!commandSender.hasPermission("uhc4.giveroles")) return false;

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (command.getName().equalsIgnoreCase("giveplayersuhcplayer")) {
                //luckperms part
            }
        }

        return false;
    }
}
