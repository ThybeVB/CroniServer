package com.monstahhh.croniserver.plugin.practice;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerCooldownManager {

    public static HashMap<Player, Boolean> playersPlayedMap = new HashMap<>();
    private static HashMap<Player, Timer> playerCooldownMap = new HashMap<>();

    public static void endPlayer(Player p, boolean survived) {
        Timer timer = playerCooldownMap.getOrDefault(p, null);
        if (timer != null) {
            timer.cancel();
            timer.purge();
            playerCooldownMap.remove(p, timer);
            playersPlayedMap.put(p, true);
            UhcPractice.showEndcard(p, survived);
        }
    }

    public static boolean hasPlayed(Player p) {
        return playersPlayedMap.get(p) != null;
    }

    public void startGame(Player p) {
        if (!isPlaying(p)) {
            Timer timer = new Timer();
            playerCooldownMap.put(p, timer);
            p.sendMessage(ChatColor.BLUE + "Starting UHC Practice! (2 Hours)");
            p.sendMessage(ChatColor.BLUE + "You can use /stopuhc at any time to stop the session.");
            p.sendMessage(ChatColor.BLUE + "Note: You can only play once per day.");
            preparePlayer(p);

            TextChannel serverChat = DiscordSRV.getPlugin().getMainTextChannel();
            serverChat.sendMessage(":triangular_flag_on_post: **"+ p.getDisplayName() + " has just begun a UHC Practice!**").queue();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    endPlayer(p, true);
                }
            }, 3600000, 3600000); //3,600,000 == 2HOURS
        }
    }

    private void preparePlayer(Player p) {
        p.getInventory().clear();
        p.setExp(0);
        p.setExhaustion(0);
        p.setSaturation(2.5F);
        p.setHealthScale(20.0F);
        p.setHealth(Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getDefaultValue());
    }

    public boolean isPlaying(Player p) {
        return playerCooldownMap.get(p) != null;
    }
}
