package com.monstahhh.croniserver.plugin.mrworldwide.helper;

import com.jafregle.Jafregle.Jafregle;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.io.IOException;

public class Translate {

    public void carryCommand(GuildMessageReceivedEvent event) {
        String origin, destination, msg;

        String[] args = event.getMessage().getContentRaw().split(" ");
        try {
            origin = args[1];
            destination = args[2];
            msg = event.getMessage().getContentRaw().substring(16);
            msg = msg.replaceAll("\n", " ");
        } catch (ArrayIndexOutOfBoundsException e) {
            event.getChannel().sendMessage("You failed to provide one of the arguments\nExample: *'translate id en kontol'*").queue();
            return;
        }

        String result = getTranslation(origin, destination, msg);

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Google Translate");
        eb.addField(origin + " -> " + destination, result, false);
        eb.setColor(Color.PINK);

        event.getChannel().sendMessage(eb.build()).queue();
    }

    private String getTranslation(String origin, String destination, String msg) {
        String result = "null";

        try {
            Jafregle jafregle = new Jafregle(origin, destination);
            result = jafregle.translate(msg);
        } catch (IOException e) {
            MrWorldWide.debugLog(e.getMessage());
        }

        return result;
    }
}
