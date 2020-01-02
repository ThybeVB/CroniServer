package com.monstahhh.croniserver.plugin.practice.commands;

import com.google.gson.internal.$Gson$Preconditions;
import com.monstahhh.croniserver.configapi.Config;
import com.monstahhh.croniserver.plugin.practice.PlayerCooldownManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UhcClearPlayers implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (command.getName().equalsIgnoreCase("clearuhcplayers")) {
            if (commandSender.hasPermission("croniserver.uhc.clearplayers")) {
                if (getPlayed().file.delete()) {
                    commandSender.sendMessage(ChatColor.BLUE + "Players have been cleared");
                } else {
                    commandSender.sendMessage(ChatColor.DARK_RED + "Players could not be cleared");
                }
            }
        }
        return true;
    }

    private static Config getPlayed() {
        return new Config("plugins/CroniServer/uhcpractice", "players_played.yml");
    }

    public static void putPlayed(Player p) {
        Config conf = getPlayed();
        conf.getConfig().set("players." + p.getDisplayName(), true);
        conf.saveConfig();
    }

    public static boolean hasPlayed(Player p) {
        return getPlayed().getConfig().getBoolean("players." + p.getDisplayName());
    }
}
