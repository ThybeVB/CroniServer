package com.monstahhh.croniserver.plugin.mrworldwide;

import com.monstahhh.croniserver.plugin.dangerapi.configapi.Config;
import com.monstahhh.croniserver.plugin.mrworldwide.event.MessageReceivedEvent;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class MrWorldWide {

    public static Config botConfig;
    public static PluginLogger _logger;

    private JavaPlugin _plugin;
    private JDA _jda;

    public MrWorldWide (JavaPlugin plugin, PluginLogger logger) {
        _plugin = plugin;
        _logger = logger;
    }

    public void enable () {

        botConfig = new Config("plugins/MrWorldWide", "config.yml", _plugin);
        Object debugObj = botConfig.getConfig().get("token");
        if (debugObj == null) {
            botConfig.getConfig().set("token", "/");
            botConfig.saveConfig();

            _logger.log(Level.SEVERE, "Mr. Worldwide token is not provided.");
        } else {
            try {
                _jda = new JDABuilder(debugObj.toString())
                        .build();

                _jda.addEventListener(new MessageReceivedEvent());

                _jda.awaitReady();
            } catch (Exception e) {
                _plugin.getServer().getConsoleSender().sendMessage("[Mr. Worldwide] " + e.getMessage());
            }
        }
    }

    public static void debugLog(String str) {
        _logger.log(Level.INFO, str);
    }

    public void disable () {

    }
}
