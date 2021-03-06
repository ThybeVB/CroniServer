package com.monstahhh.croniserver.plugin.practice.commands;

import com.monstahhh.croniserver.plugin.practice.PlayerCooldownManager;
import com.monstahhh.croniserver.plugin.practice.UhcPractice;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UhcStopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("stopuhc")) {
            if (commandSender instanceof Player) {
                Player p = (Player) commandSender;
                if (UhcPractice.playerCooldownManager.isPlaying(p)) {
                    if (args.length == 0) {
                        p.sendMessage(ChatColor.BLUE + "Are you sure you want to stop this UHC Session?");
                        p.sendMessage(ChatColor.BLUE + "Type " + ChatColor.GREEN + "/stopuhc confirm" + ChatColor.BLUE + " to continue.");
                    } else if (args[0].equalsIgnoreCase("confirm")) {
                        PlayerCooldownManager.endPlayer(p, false);
                    }
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "You are not currently in a UHC Session.");
                    p.sendMessage(ChatColor.BLUE + "Use " + ChatColor.GREEN + "/uhcpractice" + ChatColor.BLUE + " to start one.");
                }
            } else {
                commandSender.sendMessage("Command can only be executed by Player");
            }
            return true;
        }
        return false;
    }
}
