package com.monstahhh.croniserver.plugin.croniserver.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DistanceCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("croniserver.command.distance")) {
            if (command.getName().equalsIgnoreCase("distance")) {
                if (sender instanceof Player) {

                    Player player = (Player) sender;

                    if (args == null || args[0] == null) {
                        sender.sendMessage(ChatColor.RED + "You need to provide Coordinates. Example: /distance 1 2 3");
                        return true;
                    }

                    int desiredX = Integer.parseInt(args[0]);
                    int desiredY = Integer.parseInt(args[1]);
                    int desiredZ = Integer.parseInt(args[2]);
                    double distance = getDistance(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()), new Location(player.getWorld(), desiredX, desiredY, desiredZ));

                    sender.sendMessage(ChatColor.GREEN + "You are " + Math.round(distance) + " blocks away from that location.");

                } else {
                    sender.sendMessage(ChatColor.RED + "Only valid players can use this command.");
                }
            }

            if (command.getName().equalsIgnoreCase("pdistance")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (args[0] != null) {

                        Player player1 = Bukkit.getServer().getPlayer(args[0]);

                        assert player1 != null;
                        if (player.getWorld().getEnvironment() == player1.getWorld().getEnvironment() && player.getWorld() == player1.getWorld()) {

                            double originX = player.getLocation().getX();
                            double originY = player.getLocation().getY();
                            double originZ = player.getLocation().getZ();

                            double desiredX = player1.getLocation().getX();
                            double desiredY = player1.getLocation().getY();
                            double desiredZ = player1.getLocation().getZ();

                            double distance = getDistance(new Location(player.getWorld(), originX, originY, originZ), new Location(player1.getWorld(), desiredX, desiredY, desiredZ));

                            player.sendMessage(ChatColor.GREEN + "You are " + Math.round(distance) + " blocks away from " + ChatColor.LIGHT_PURPLE + player1.getDisplayName() + ChatColor.GREEN + ".");
                        } else {
                            player.sendMessage(ChatColor.RED + "You need to be in the same world!");
                        }

                    } else {
                        player.sendMessage(ChatColor.RED + "You need to give a playername.");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Only valid players can use this command.");
                }
            }
        }

        return true;
    }

    private double getDistance(Location origin, Location desired) {

        //sqrt( (x1 - x2) ^ 2 + (y1 - y2) ^ 2 + (z1 - z2) ^ 2 )
        double xpower = Math.pow((origin.getX() - desired.getX()), 2);
        double ypower = Math.pow((origin.getY() - desired.getY()), 2);
        double zpower = Math.pow((origin.getZ() - desired.getZ()), 2);
        double togetherPower = xpower + ypower + zpower;

        return Math.sqrt(togetherPower);
    }
}
