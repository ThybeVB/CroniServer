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
        if (command.getName().equalsIgnoreCase("distance")) {
            if (sender instanceof Player) {

                if (args == null || args[0] == null) {
                    sender.sendMessage(ChatColor.RED + "You need to provide Coordinates. Example: /distance 1 2 3");
                    return true;
                }

                Player player = (Player)sender;
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

                if (args[0] instanceof String) {

                    Player player = Bukkit.getServer().getPlayer(args[0]);

                    if (((Player) sender).getWorld().getEnvironment() == player.getWorld().getEnvironment()) {

                        double originX = ((Player) sender).getLocation().getX();
                        double originY = ((Player) sender).getLocation().getY();
                        double originZ = ((Player) sender).getLocation().getZ();

                        double desiredX = player.getLocation().getX();
                        double desiredY = player.getLocation().getY();
                        double desiredZ = player.getLocation().getZ();

                        double distance = getDistance(new Location(((Player) sender).getWorld(), originX, originY, originZ), new Location(player.getWorld(), desiredX, desiredY, desiredZ));

                        sender.sendMessage(ChatColor.GREEN + "You are " + Math.round(distance) + " blocks away from " + ChatColor.LIGHT_PURPLE + player.getDisplayName() + ChatColor.GREEN + ".");
                    } else {
                        sender.sendMessage(ChatColor.RED + "You need to be in the same world!");
                    }

                } else {
                    sender.sendMessage(ChatColor.RED + "You need to give a playername.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Only valid players can use this command.");
            }
        }

        if (command.getName().equalsIgnoreCase("lol")) {
            Bukkit.broadcastMessage(ChatColor.BLUE + "guaka have big gay jaja xd");
        }


            return true;
    }

    private double getDistance(Location origin, Location desired) {

        //d = sqrt( (x1 - x2)² + (y1 - y2)² + (z1 - z2)² )
        double xpower = Math.pow( (origin.getX() - desired.getX()) , 2);
        double ypower = Math.pow( (origin.getY() - desired.getY()) , 2);
        double zpower = Math.pow( (origin.getZ() - desired.getZ()) , 2);
        double togetherPower = xpower + ypower + zpower;
        double result = Math.sqrt(togetherPower);

        return result;
    }
}
