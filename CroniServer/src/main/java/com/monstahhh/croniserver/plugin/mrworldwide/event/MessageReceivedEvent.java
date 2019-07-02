package com.monstahhh.croniserver.plugin.mrworldwide.event;

import com.monstahhh.croniserver.plugin.mrworldwide.helper.Translate;
import com.monstahhh.croniserver.plugin.mrworldwide.helper.Weather;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageReceivedEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (message.toLowerCase().startsWith("translate")) {
            Translate translate = new Translate();
            translate.carryCommand(event);
        }

        if (message.toLowerCase().startsWith("weather")) {
            Weather weather = new Weather();
            weather.carryCommand(event);
        }
    }


}
