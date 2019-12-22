package com.monstahhh.croniserver.plugin.practice.commands;

import com.monstahhh.croniserver.plugin.practice.PlayerCooldownManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class UhcClearPlayers implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (command.getName().equalsIgnoreCase("clearuhcplayers")) {
            if (commandSender.hasPermission("croniserver.uhc.clearplayers")) {
                PlayerCooldownManager.playersPlayedMap.clear();
            }
        }
        return true;
    }
}
