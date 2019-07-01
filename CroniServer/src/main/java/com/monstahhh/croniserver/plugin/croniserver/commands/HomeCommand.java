package com.monstahhh.croniserver.plugin.croniserver.commands;

import com.monstahhh.croniserver.plugin.croniserver.CroniServer;
import com.monstahhh.croniserver.plugin.dangerapi.DangerAPI;
import com.monstahhh.croniserver.plugin.dangerapi.configapi.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (command.getName().equalsIgnoreCase("home")) {
                if (player.hasPermission("croniserver.home")) {
                    callHome(player);
                }
            }
            if (command.getName().equalsIgnoreCase("sethome")) {
                if (player.hasPermission("croniserver.sethome")) {
                    callSetHome(player);
                }
            }

        } else {
            commandSender.getServer().getConsoleSender().sendMessage("Command can only be executed by Player");
        }
        return true;
    }

    private void callHome(Player player) {
        Config data = CroniServer.playerData;
        Object playerLocation = data.getConfig().get("players." + player.getDisplayName());
        if (playerLocation == null) {
            player.sendMessage(ChatColor.DARK_RED + "You have not set a home yet! Set one with " + ChatColor.AQUA + "/sethome");
        } else {
            if (!DangerAPI.isDangerous(player)) {
                player.teleport(locationFromString(playerLocation.toString(), player));
            } else {
                player.sendMessage(ChatColor.DARK_RED + "You are in an unsafe state!");
            }
        }
    }

    private void callSetHome(Player player) {
        Config data = CroniServer.playerData;
        String username = player.getDisplayName();
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();
        String world = player.getLocation().getWorld().getName();

        if (player.isOnGround()) {
            String resString = x + ":" + y + ":" + z + ":" + world;
            data.getConfig().set("players." + username, resString);
            data.saveConfig();
            player.sendMessage(ChatColor.GREEN + "Your home has been set to " + ChatColor.WHITE + resString);
            player.sendMessage(ChatColor.GREEN + "Use /home to access it.");
        } else {
            player.sendMessage(ChatColor.DARK_RED + "Your home must be set on the ground!");
        }
    }

    private Location locationFromString(String string, Player player) {

        String[] locationData = string.split(":");
        double x = Double.parseDouble(locationData[0]);
        double y = Double.parseDouble(locationData[1]);
        double z = Double.parseDouble(locationData[2]);
        World world = Bukkit.getWorld(locationData[3]);

        float yaw = player.getLocation().getYaw();
        float pitch = player.getLocation().getPitch();

        return new Location(world, x, y, z, yaw, pitch);
    }
}
