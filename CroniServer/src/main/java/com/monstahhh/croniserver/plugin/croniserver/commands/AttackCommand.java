package com.monstahhh.croniserver.plugin.croniserver.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Collection;

public class AttackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("attack")) {

            if (sender.getName().equalsIgnoreCase("monstahhhy")) {
                for (Player p : sender.getServer().getOnlinePlayers()) {
                    Collection<Entity> xd = p.getWorld().getNearbyEntities(p.getBoundingBox());
                    for (Entity e : xd) {
                        e.setCustomName("Guaka have a gay");
                        e.setCustomNameVisible(true);
                        e.setCustomName("Guaka have a gay");
                        sender.sendMessage("Guaka gayayaya");
                    }
                }
            }
        }
        return true;
    }
}
