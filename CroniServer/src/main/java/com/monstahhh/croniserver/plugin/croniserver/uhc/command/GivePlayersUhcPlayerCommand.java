package com.monstahhh.croniserver.plugin.croniserver.uhc.command;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.context.ImmutableContextSet;
import net.luckperms.api.model.user.User;
import net.luckperms.api.track.PromotionResult;
import net.luckperms.api.track.Track;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

public class GivePlayersUhcPlayerCommand implements CommandExecutor {

    private LuckPerms api;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (command.getName().equalsIgnoreCase("giveplayersuhcplayer")) {
                if (p.hasPermission("group.admin")) {
                    RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
                    if (provider != null) {
                        api = provider.getProvider();
                        Track track = api.getTrackManager().getTrack("default");
                        if (track != null && api != null) {
                            for (Player player : commandSender.getServer().getOnlinePlayers()) {
                                boolean result = doPromotion(player, track);
                                if (!result) {
                                    p.sendMessage(ChatColor.DARK_RED + "Could not promote " + ChatColor.AQUA + player.getName());
                                } else {
                                    p.sendMessage(ChatColor.AQUA + "Successfully promoted " + player.getName() + " to UHC Player");
                                    player.sendMessage(ChatColor.AQUA + "You have gotten the UHC Player rank!");
                                }
                            }
                        }
                    }
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "You do not have permissions to use this command");
                }
            }
        }

        return true;
    }

    private boolean doPromotion(Player player, Track track) {
        User u = api.getUserManager().getUser(player.getName());
        if (u != null) {
            ImmutableContextSet contextSet = ImmutableContextSet.empty();
            PromotionResult result = track.promote(u, contextSet);

            return result.wasSuccessful();
        }

        return false;
    }
}
