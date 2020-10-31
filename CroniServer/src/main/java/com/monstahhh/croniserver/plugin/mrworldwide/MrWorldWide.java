package com.monstahhh.croniserver.plugin.mrworldwide;

import com.monstahhh.croniserver.configapi.Config;
import com.monstahhh.croniserver.http.HttpClient;
import com.monstahhh.croniserver.http.HttpMethod;
import com.monstahhh.croniserver.plugin.croniserver.CroniServer;
import com.monstahhh.croniserver.plugin.mrworldwide.event.GuildUpdate;
import com.monstahhh.croniserver.plugin.mrworldwide.event.MessageReceivedEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.Compression;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class MrWorldWide {

    public static JavaPlugin _plugin = null;
    public static String weatherToken;
    public static String currencyToken;
    public static String apiToken;
    public static String serverToken;
    public static long OwnerId = 257247527630274561L;
    public static JSONObject JsonStats;
    public static JDA _jda = null;
    private boolean debug = false;

    public MrWorldWide(JavaPlugin plugin) {
        _plugin = plugin;
    }

    public static void debugLog(String str) {
        CroniServer.logger.log(Level.INFO, str);
    }

    public void enable() {
        Config botConfig = new Config("plugins/MrWorldWide", "config.yml");

        this.checkServices(botConfig);

        Object tokenObj = botConfig.getConfig().get("token");
        if (tokenObj == null) {
            botConfig.getConfig().set("token", "/");
            botConfig.saveConfig();

            CroniServer.logger.log(Level.SEVERE, "Mr. Worldwide token is not provided.");
        } else {
            try {
                _jda = JDABuilder.createDefault(tokenObj.toString())
                        .setAutoReconnect(true)
                        .addEventListeners(new MessageReceivedEvent(), new GuildUpdate())
                        .setCompression(Compression.ZLIB)
                        .setChunkingFilter(ChunkingFilter.NONE)
                        .setActivity(Activity.watching("the world"))
                        .setContextEnabled(false)
                        .build().awaitReady();

                _plugin.getServer().getConsoleSender().sendMessage("[Mr. Worldwide] Listening!");
                Objects.requireNonNull(Objects.requireNonNull(_jda.getGuildById(305792249877364738L))
                        .getTextChannelById(560486517043232768L))
                        .sendMessage("*dale!*")
                        .queue();

            } catch (Exception e) {
                _plugin.getServer().getConsoleSender().sendMessage("[Mr. Worldwide] " + e.getMessage());
            }
        }
    }

    private void checkServices(Config botConfig) {

        Object _serverToken = botConfig.getConfig().get("serverToken");
        if (_serverToken == null) {
            botConfig.getConfig().set("serverToken", "/");
            botConfig.saveConfig();

            CroniServer.logger.log(Level.SEVERE, "Server token is not provided.");
        } else {
            serverToken = _serverToken.toString();
        }

        Object _weatherToken = botConfig.getConfig().get("weatherToken");
        if (_weatherToken == null) {
            botConfig.getConfig().set("weatherToken", "/");
            botConfig.saveConfig();

            CroniServer.logger.log(Level.SEVERE, "Weather Data token is not provided.");
        } else {
            weatherToken = _weatherToken.toString();
        }

        Object _apiToken = botConfig.getConfig().get("apiToken");
        if (_apiToken == null) {
            botConfig.getConfig().set("apiToken", "/");
            botConfig.saveConfig();

            CroniServer.logger.log(Level.SEVERE, "API token is not provided.");
        } else {
            apiToken = _apiToken.toString();
        }

        Object _currencyToken = botConfig.getConfig().get("currencyToken");
        if (_currencyToken == null) {
            botConfig.getConfig().set("currencyToken", "/");
            botConfig.saveConfig();

            CroniServer.logger.log(Level.SEVERE, "Currency Converter token is not provided.");
        } else {
            currencyToken = _currencyToken.toString();
        }

        Object debugObj = botConfig.getConfig().get("debug");
        if (debugObj == null) {
            botConfig.getConfig().set("debug", false);
            botConfig.saveConfig();
        } else {
            if (debugObj.toString().equals("true")) {
                debug = true;
            }
        }
    }

    public void disable() {
        if (_jda != null) {
            TextChannel channel = Objects.requireNonNull(_jda.getGuildById(305792249877364738L))
                    .getTextChannelById(560486517043232768L);
            if (channel != null) {
                if (!debug) {
                    channel.sendMessage("Woaaaah i'm passing out!").complete();
                }
            }

            _jda.shutdown();
        }

        System.out.println("Mr. Worldwide has shut down!");
    }
}
