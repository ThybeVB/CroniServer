package com.monstahhh.croniserver.plugin.croniserver.commands;

import com.monstahhh.croniserver.plugin.dangerapi.DangerAPI;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("croniserver.command.warp")) {
                Player p = (Player) sender;
                if (p.getWorld().getName().equalsIgnoreCase("world")) {
                    if (p.getGameMode() == GameMode.SURVIVAL) {
                        if (DangerAPI.isDangerous(p)) {
                            sender.sendMessage(ChatColor.DARK_RED + "You are in an unsafe state!");
                        } else {
                            this.doTp(p, command);
                        }
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
        if (command.getName().equalsIgnoreCase("spawn")) {
            player.teleportAsync(new Location(player.getWorld(), 3, 69, -2));
        }
        if (command.getName().equalsIgnoreCase("survival")) {
            if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
                player.teleportAsync(new Location(Bukkit.getWorld("world_nether"), 0, 120, -4));
            }
            player.teleportAsync(new Location(Bukkit.getWorld("world"), 0, 69, 0));
        }
        if (command.getName().equalsIgnoreCase("creative")) {
            player.teleportAsync(new Location(Bukkit.getWorld("creative"), 0, 4, 4));
            player.setFlying(true);
        }
    }
}