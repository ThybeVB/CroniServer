package com.monstahhh.croniserver.plugin.mrworldwide;

import com.monstahhh.croniserver.plugin.croniserver.CroniServer;
import com.monstahhh.croniserver.plugin.dangerapi.configapi.Config;
import com.monstahhh.croniserver.plugin.mrworldwide.event.MessageReceivedEvent;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class MrWorldWide {

    private static JavaPlugin _plugin;
    private JDA _jda;

    public static String weatherToken = "";
    public static String currencyToken = "";

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

                _jda.getGuildById(305792249877364738L)
                        .getTextChannelById(560486517043232768L)
                        .sendMessage("Oh damn i'm back!")
                        .queue();

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

        Object _currencyToken = botConfig.getConfig().get("currencyToken");
        if (_currencyToken == null) {
            botConfig.getConfig().set("currencyToken", "/");
            botConfig.saveConfig();

            CroniServer.logger.log(Level.SEVERE, "Currency Converter token is not provided.");
        } else {
            currencyToken = _currencyToken.toString();
        }
    }

    public static void debugLog(String str) {
        CroniServer.logger.log(Level.INFO, str);
    }

    public void disable() {
        if (_jda != null) {
            _jda.getGuildById(305792249877364738L)
                    .getTextChannelById(560486517043232768L)
                    .sendMessage("Woaaaah i'm passing out!")
                    .queue();
        }

        System.out.println("Mr. Worldwide has shut down!");
    }
}
