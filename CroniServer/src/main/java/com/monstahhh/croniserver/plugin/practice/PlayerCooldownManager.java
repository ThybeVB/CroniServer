package com.monstahhh.croniserver.plugin.practice;

import com.monstahhh.croniserver.plugin.practice.commands.UhcClearPlayers;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerCooldownManager {

    private static final HashMap<Player, Timer> playerCooldownMap = new HashMap<>();
    private final Random random = new Random();

    public static void endPlayer(Player p, boolean survived) {
        Timer timer = playerCooldownMap.getOrDefault(p, null);
        if (timer != null) {
            timer.cancel();
            timer.purge();
            playerCooldownMap.remove(p, timer);
            UhcClearPlayers.putPlayed(p);
            UhcPractice.showEndcard(p, survived);
        }
    }

    public void startGame(Player p) {
        if (!isPlaying(p)) {
            Timer timer = new Timer();
            playerCooldownMap.put(p, timer);
            p.sendMessage(ChatColor.BLUE + "Starting UHC Practice! (2 Hours)");
            p.sendMessage(ChatColor.BLUE + "You can use /stopuhc at any time to stop the session.");
            p.teleportAsync(getSpawnLocation(Bukkit.getWorld("uhcpractice")));
            preparePlayer(p);

            TextChannel serverChat = DiscordSRV.getPlugin().getMainTextChannel();
            serverChat.sendMessage(":triangular_flag_on_post: **" + p.getDisplayName() + " has just begun a UHC Practice!**").queue();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    endPlayer(p, true);
                }
            }, 7200000, 7200000); //7,200,000 == 2 HOURS
        }
    }

    private void preparePlayer(Player p) {
        p.setExp(0);
        p.setExhaustion(0);
        p.setSaturation(2.5F);
        p.setHealthScale(20.0F);
        p.setFoodLevel(20);
        p.setHealth(Objects.requireNonNull(p.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getDefaultValue());
        p.getInventory().clear();
    }

    private Location getSpawnLocation(World world) {
        int x = getRandomCoord();
        int z = getRandomCoord();
        Location loc = new Location(world, x, world.getHighestBlockAt(x, z).getY(), z);
        if (loc.getBlock().getBiome().name().endsWith("OCEAN")) {
            return getSpawnLocation(world);
        }
        return loc;
    }

    private int getRandomCoord() {
        return random.nextInt(5000 + 1 - -5000) + -5000;
    }

    public boolean isPlaying(Player p) {
        return playerCooldownMap.get(p) != null;
    }
}
