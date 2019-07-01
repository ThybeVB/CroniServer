package com.monstahhh.croniserver.plugin.croniserver.commands;

import com.monstahhh.croniserver.plugin.croniserver.CroniServer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class InfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("crinfo")) {
            if (sender.hasPermission("croniserver.command.info") || sender instanceof ConsoleCommandSender) {
                sender.sendMessage(ChatColor.DARK_PURPLE + "====== CroniServer Info ======");
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "Plugin Version: v" + CroniServer.version);
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "Spigot/Bukkit Version: " + sender.getServer().getBukkitVersion());
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "Author: " + CroniServer.author);
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "Hooked into AuthMe");
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "Online Players: " + sender.getServer().getOnlinePlayers().size() + "/" + sender.getServer().getMaxPlayers());
            }
        }

        return true;
    }
}
