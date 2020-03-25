package com.monstahhh.croniserver.plugin.croniserver.commands;

import com.monstahhh.croniserver.plugin.croniserver.CroniServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class TpaCommand implements CommandExecutor {

    HashMap<Player, Player> tpa = new HashMap();
    ArrayList<Player> tpaSent = new ArrayList();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_RED + "This command can only be executed by players");
            return true;
        }

        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("tpa")) {
            if (this.tpaSent.contains(p)) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lTpa > &7You have to wait for a new TPA request"));
            } else {
                if (args.length == 0) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lTpa > &7Please specify a player!"));
                    return true;
                }
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lTpa > &7That player doesn't exist!"));
                    return true;
                }
                if (p.getWorld() == target.getWorld()) {

                    this.tpa.put(target, p);
                    if (!p.isOp()) {
                        this.tpaSent.add(p);
                    }
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', ("&6&lTpa > &aTPA request sent to &6/target/").replaceAll("/target/", target.getDisplayName())));
                    target.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "_____________________________________________");
                    target.sendMessage(" ");
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', ("&6&lTpa > &aTPA request from &6/sender/").replaceAll("/sender/", p.getDisplayName())));
                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aWrite &b&l/tpaccept &ato accept or &c&l/tpdeny &ato deny"));
                    target.sendMessage(" ");
                    target.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "_____________________________________________");
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(CroniServer._plugin, () -> tpaSent.remove(p), (20 * 20));
                    return true;
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lTpa > &7You can't teleport to a player into another world!"));
                return true;
            }

            return true;
        }
        if (cmd.getName().equalsIgnoreCase("tpaccept")) {
            if (this.tpa.get(p) == null) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lTpa > &7No request to accept!"));
                return true;
            }
            if (this.tpa.get(p) != null) {
                (this.tpa.get(p)).teleport(p);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', ("&6/sender/ &ahas been teleported to you!").replaceAll("/sender/", this.tpa.get(p).getDisplayName())));
                (this.tpa.get(p)).sendMessage(ChatColor.translateAlternateColorCodes('&', ("&6/target/ &aaccepted the teleport!").replaceAll("/target/", p.getDisplayName())));
                if (!(this.tpa.get(p)).isOp()) {
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(CroniServer._plugin, new Runnable() {
                        public void run() {
                            tpaSent.remove(tpa.get(p));
                        }
                    }, (20 * 20));
                }
                this.tpa.put(p, null);
                return true;
            }
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("tpdeny")) {
            if (this.tpa.get(p) == null) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lTpa > &7No request to deny!"));
                return true;
            }
            if (this.tpa.get(p) != null) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lTpa > &7You denied the teleport"));
                (this.tpa.get(p)).sendMessage(ChatColor.translateAlternateColorCodes('&', ("&c&lTpa > &6/target/ &7denied the teleport ").replaceAll("/target/", p.getDisplayName())));
                if (!(this.tpa.get(p)).isOp()) {
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(CroniServer._plugin, () -> tpaSent.remove(tpa.get(p)), (20 * 20));
                }
                this.tpa.put(p, null);
                return true;
            }
            return true;
        }
        return true;
    }
}
