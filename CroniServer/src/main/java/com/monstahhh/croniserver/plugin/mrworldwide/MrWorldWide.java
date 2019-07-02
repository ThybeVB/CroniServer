package com.monstahhh.croniserver.plugin.mrworldwide;

import com.monstahhh.croniserver.plugin.croniserver.CroniServer;
import com.monstahhh.croniserver.plugin.dangerapi.configapi.Config;
import com.monstahhh.croniserver.plugin.mrworldwide.event.MessageReceivedEvent;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class MrWorldWide {

    private static JavaPlugin _plugin;
    private JDA _jda;

    public static String weatherToken = "";

    public MrWorldWide(JavaPlugin plugin) {
        _plugin = plugin;
    }

    public void enable() {
        Config botConfig;
        botConfig = new Config("plugins/MrWorldWide", "config.yml", _plugin);
        Object tokenObj = botConfig.getConfig().get("token");
        if (tokenObj == null) {
            botConfig.getConfig().set("token", "/");
            botConfig.saveConfig();

            CroniServer.logger.log(Level.SEVERE, "Mr. Worldwide token is not provided.");
        } else {
            try {
                _jda = new JDABuilder(AccountType.BOT)
                        .setToken(tokenObj.toString())
                        .setAudioEnabled(false)
                        .setAutoReconnect(true)
                        .addEventListener(new MessageReceivedEvent())
                        .setGame(Game.watching("the world"))
                        .setContextEnabled(false)
                        .build().awaitReady();

            } catch (Exception e) {
                _plugin.getServer().getConsoleSender().sendMessage("[Mr. Worldwide] " + e.getMessage());
            }
        }
        Object _weatherToken = botConfig.getConfig().get("weatherToken");
        if (_weatherToken == null) {
            botConfig.getConfig().set("weatherToken", "/");
            botConfig.saveConfig();

            CroniServer.logger.log(Level.SEVERE, "Weather Data token is not provided.");
        } else {
            weatherToken = _weatherToken.toString();
        }
    }

    public static void debugLog(String str) {
        CroniServer.logger.log(Level.INFO, str);
    }

    public void disable() {
        if (_jda != null) {
            _jda.shutdownNow();
        }

        System.out.println("Mr. Worldwide has shut down!");
    }
}
