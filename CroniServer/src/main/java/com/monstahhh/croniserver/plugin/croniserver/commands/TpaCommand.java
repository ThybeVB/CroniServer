package com.monstahhh.croniserver.plugin.croniserver.commands;

import com.monstahhh.croniserver.plugin.croniserver.CroniServer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class TpaCommand implements CommandExecutor {

    Map<Player, Long> tpaCooldown = new HashMap<>();
    Map<Player, Player> currentRequest = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        Player p = null;
        if (sender instanceof Player) {
            p = (Player) sender;
        }

        if (cmd.getName().equalsIgnoreCase("tpa")) {
            if (!(p == null)) {
                if (!p.hasPermission("croniserver.tpa.overridecooldown")) {
                    int cooldown = 20;
                    if (tpaCooldown.containsKey(p)) {
                        long diff = (System.currentTimeMillis() - tpaCooldown.get(sender)) / 1000;
                        if (diff < cooldown) {
                            p.sendMessage(ChatColor.RED + "Error: You must wait a " + cooldown + " second cooldown in between teleport requests!");
                            return false;
                        }
                    }
                }

                if (args.length > 0) {
                    final Player target = sender.getServer().getPlayer(args[0]);

                    if (target == null) {
                        sender.sendMessage(ChatColor.RED + "Error: You can only send a teleport request to online players!");
                        return false;
                    }

                    if (target == p) {
                        sender.sendMessage(ChatColor.RED + "Error: You can't teleport to yourself!");
                        return false;
                    }

                    if (!p.hasPermission("croniserver.tpa")) {
                        sender.sendMessage("You do not have permission to use tpa");
                        return false;
                    }

                    sendRequest(p, target);
                    tpaCooldown.put(p, System.currentTimeMillis());
                    sender.getServer().getScheduler().scheduleSyncDelayedTask(CroniServer._plugin, () -> killRequest(target), 30*20);
                } else {
                    p.sendMessage("Send a teleport request to a player.");
                    p.sendMessage("/tpa <player>");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Error: The console can't teleport to people");
                return false;
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("tpaccept")) {
            if (!(p == null)) {
                if (currentRequest.containsKey(p)) {

                    Player heIsGoingOutOnADate = currentRequest.get(p);
                    currentRequest.remove(p);

                    if (!(heIsGoingOutOnADate == null)) {
                        heIsGoingOutOnADate.teleport(p);
                        p.sendMessage(ChatColor.GRAY + "Teleporting...");
                        heIsGoingOutOnADate.sendMessage(ChatColor.GRAY + "Teleporting...");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Error: It appears that the person trying to teleport to you doesn't exist anymore. WHOA!");
                        return false;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Error: It doesn't appear that there are any current tp requests. Maybe it timed out?");
                    return false;
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Error: The console can't accept teleport requests, silly!");
                return false;
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("tpdeny")) {
            if (!(p == null)) {
                if (currentRequest.containsKey(p)) {
                    Player poorRejectedGuy = currentRequest.get(p);
                    currentRequest.remove(p);

                    if (!(poorRejectedGuy == null)) {
                        poorRejectedGuy.sendMessage(ChatColor.RED + p.getName() + " rejected your teleport request! :(");
                        p.sendMessage(ChatColor.GRAY + poorRejectedGuy.getName() + " was rejected!");
                        return true;
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Error: It doesn't appear that there are any current tp requests. Maybe it timed out?");
                    return false;
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Error: The console can't deny teleport requests, silly!");
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean killRequest(Player key) {
        if (currentRequest.containsKey(key)) {
            Player loser = currentRequest.get(key);
            if (!(loser == null)) {
                loser.sendMessage(ChatColor.RED + "Your teleport request timed out.");
            }

            currentRequest.remove(key);

            return true;
        } else {
            return false;
        }
    }

    public void sendRequest(Player sender, Player recipient) {
        sender.sendMessage("Sending a teleport request to " + recipient.getName() + ".");

        String sendtpaccept = "";
        String sendtpdeny = "";

        if (recipient.hasPermission("croniserver.tpa.tpaccept")) {
            sendtpaccept = " To accept the teleport request, type " + ChatColor.RED + "/tpaccept" + ChatColor.RESET + ".";
        } else {
            sendtpaccept = "";
        }

        if (recipient.hasPermission("croniserver.tpa.tpdeny")) {
            sendtpdeny = " To deny the teleport request, type " + ChatColor.RED + "/tpdeny" + ChatColor.RESET + ".";
        } else {
            sendtpdeny = "";
        }

        recipient.sendMessage(ChatColor.RED + sender.getName() + ChatColor.RESET + " has sent a request to teleport to you." + sendtpaccept + sendtpdeny);
        currentRequest.put(recipient, sender);
    }
}
