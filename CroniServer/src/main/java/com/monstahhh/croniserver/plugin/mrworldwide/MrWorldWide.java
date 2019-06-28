package com.monstahhh.croniserver.plugin.mrworldwide;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public class MrWorldWide{

    private JavaPlugin _plugin;

    public MrWorldWide (JavaPlugin plugin) {
        _plugin = plugin;
    }

    public void enable () {
        try {
            JDA jda = new JDABuilder("token").build();
        } catch (LoginException e) {

        }
    }

    public void disable () {

    }
}
