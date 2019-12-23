package com.monstahhh.croniserver.plugin.practice;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.internal.annotation.Selection;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import sun.jvm.hotspot.opto.Block;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerCooldownManager {

    public static HashMap<Player, Boolean> playersPlayedMap = new HashMap<>();
    private static HashMap<Player, Timer> playerCooldownMap = new HashMap<>();

    public static void killPlayer(Player p) {
        Timer timer = playerCooldownMap.getOrDefault(p, null);
        if (timer != null) {
            timer.cancel();
            timer.purge();
            playerCooldownMap.remove(p, timer);
            playersPlayedMap.put(p, true);
            regenerateChunks(p);
            UhcPractice.showEndcard(p, false);
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
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    timer.cancel();
                    timer.purge();
                    playerCooldownMap.remove(p, timer);
                    playersPlayedMap.put(p, true);
                    regenerateChunks(p);
                    UhcPractice.showEndcard(p, true);
                }
            }, 3600000, 3600000); //3,600,000 == 2HOURS
        }
    }

    private static void regenerateChunks(Player p) {
        BukkitWorld world = new BukkitWorld(p.getWorld());
        double x = p.getLocation().getX();
        double z = p.getLocation().getZ();

        Region selection = new CuboidRegion(BlockVector3.at(x - 250, 0, z - 250), BlockVector3.at(x + 250, 256, z + 250));

        world.regenerate(selection, WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1));
    }

    public boolean isPlaying(Player p) {
        return playerCooldownMap.get(p) != null;
    }
}
