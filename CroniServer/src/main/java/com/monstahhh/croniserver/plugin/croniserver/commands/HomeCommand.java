package com.monstahhh.croniserver.plugin.croniserver.commands;

import com.monstahhh.croniserver.plugin.croniserver.CroniServer;
import com.monstahhh.croniserver.plugin.damageapi.configapi.Config;
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
        Config data = CroniServer.playerData;
        Object playerLocation = data.getConfig().get("players." + player.getDisplayName());
        if (playerLocation == null) {
            player.sendMessage(ChatColor.DARK_RED + "You have not set a home yet! Set one with " + ChatColor.AQUA + "/sethome");
        } else {
            player.teleport(locationFromString(playerLocation.toString()));
        }
    }

    private void callSetHome (Player player) {
        Config data = CroniServer.playerData;
        String username = player.getDisplayName();
        double x = player.getLocation().getX();
        double y = player.getLocation().getY();
        double z = player.getLocation().getZ();
        String world = player.getLocation().getWorld().getName();

        String resString = x + ":" + y + ":" + z + ":" + world;
        data.getConfig().set("players." + username, resString);
        player.sendMessage(resString);
        data.saveConfig();
    }

    private Location locationFromString (String string) {

        String[] locationData = string.split(":");
        System.out.println(string);
        double x = Double.parseDouble(locationData[0]);
        System.out.println(x);
        double y = Double.parseDouble(locationData[1]);
        System.out.println(y);
        double z = Double.parseDouble(locationData[2]);
        System.out.println(z);
        World world = Bukkit.getWorld(locationData[3]);
        System.out.println(world);

        return new Location(world, x, y, z);
    }

    //string format = 'players. {player}:100:150:0:world '
}
