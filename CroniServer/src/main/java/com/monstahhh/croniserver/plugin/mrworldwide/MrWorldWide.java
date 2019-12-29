package com.monstahhh.croniserver.plugin.mrworldwide;

import com.monstahhh.croniserver.configapi.Config;
import com.monstahhh.croniserver.plugin.croniserver.CroniServer;
import com.monstahhh.croniserver.plugin.mrworldwide.event.MessageReceivedEvent;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.utils.Compression;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;

public class MrWorldWide {

    public static JavaPlugin _plugin = null;
    public static String weatherToken;
    public static String currencyToken;
    public static long OwnerId = 257247527630274561L;
    private static long guildId = 305792249877364738L;
    private static long statusChannelId = 560486517043232768L;
    private JDA _jda = null;
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
                _jda = new JDABuilder(AccountType.BOT)
                        .setToken(tokenObj.toString())
                        .setAutoReconnect(true)
                        .addEventListeners(new MessageReceivedEvent())
                        .setCompression(Compression.NONE)
                        .setActivity(Activity.watching("the world"))
                        .setContextEnabled(false)
                        .build().awaitReady();

                _plugin.getServer().getConsoleSender().sendMessage("[Mr. Worldwide] Listening!");
                if (debug) {
                    Objects.requireNonNull(Objects.requireNonNull(_jda.getGuildById(guildId))
                            .getTextChannelById(statusChannelId))
                            .sendMessage("Worldwide detected on Local Machine, giving priority...")
                            .queue();
                } else {
                    Objects.requireNonNull(Objects.requireNonNull(_jda.getGuildById(guildId))
                            .getTextChannelById(statusChannelId))
                            .sendMessage("*dale!*")
                            .queue();
                }

            } catch (Exception e) {
                _plugin.getServer().getConsoleSender().sendMessage("[Mr. Worldwide] " + e.getMessage());
            }
        }
    }

    private void checkServices(Config botConfig) {

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
            TextChannel channel = Objects.requireNonNull(_jda.getGuildById(guildId))
                    .getTextChannelById(statusChannelId);
            if (channel != null) {
                if (!debug) {
                    channel.sendMessage("Woaaaah i'm passing out!").complete();
                } else {
                    channel.sendMessage("Worldwide giving control back to server").complete();
                }
            }

            _jda.shutdown();
        }

        System.out.println("Mr. Worldwide has shut down!");
    }
}
