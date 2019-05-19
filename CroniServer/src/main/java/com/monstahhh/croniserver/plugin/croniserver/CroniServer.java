package com.monstahhh.croniserver.plugin.croniserver;

import com.monstahhh.croniserver.plugin.croniserver.commands.DistanceCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.HubCommand;
import com.monstahhh.croniserver.plugin.croniserver.events.PlayerListener;
import net.minecraft.server.v1_14_R1.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.LogRecord;

public final class CroniServer extends JavaPlugin {

    @Override
    public void onEnable() {

        PluginLogger logger = new PluginLogger(this);

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        this.getCommand("hub").setExecutor(new HubCommand());
        this.getCommand("spawn").setExecutor(new HubCommand());
        this.getCommand("distance").setExecutor(new DistanceCommand());
        this.getCommand("pdistance").setExecutor((new DistanceCommand()));
        this.getCommand("lol").setExecutor(new DistanceCommand());

        timer();
        logger.log(new LogRecord(Level.INFO, "Loaded TPS Recorder"));
        logger.log(new LogRecord(Level.INFO, "Loaded CroniServer Build 19/05/19"));
    }

    public void timer()
    {
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
                {
                    public void run()
                    {
                        for (double tps : MinecraftServer.getServer().recentTps)
                        {
                            if (tps <= 18.75) {
                                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                                    if (p.hasPermission("croniserver.admin")) {
                                        p.sendMessage(ChatColor.DARK_RED + "SERVER TPS UNDER 18.75 GUAKA FIX IT");
                                    }
                                }
                            }
                        }
                    }
                }
                , 300, 300);
    }
}
