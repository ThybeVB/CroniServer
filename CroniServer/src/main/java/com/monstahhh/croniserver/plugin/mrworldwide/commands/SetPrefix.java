package com.monstahhh.croniserver.plugin.mrworldwide.commands;

import com.monstahhh.croniserver.plugin.croniserver.CroniServer;
import com.monstahhh.croniserver.sqlite.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class SetPrefix {

    private final MessageEmbed prefixTooLongErrorEmbed = new EmbedBuilder()
            .setTitle("Mr. Error")
            .setColor(Color.RED)
            .addField("Set prefix error", "The entered prefix is too long. Please try a shorter prefix", false)
            .build();

    private final MessageEmbed noPrefixErrorEmbed = new EmbedBuilder()
            .setTitle("Mr. Error")
            .setColor(Color.RED)
            .addField("Set prefix error", "Please enter a prefix", false)
            .build();

    public void carryCommand(GuildMessageReceivedEvent event, String cmdStripped) {
        String prefix = cmdStripped.split(" ")[1];

        if (prefix.isEmpty() || prefix.isBlank()) {
            event.getChannel().sendMessage(noPrefixErrorEmbed).queue();
            return;
        }

        if (prefix.length() > 6) {
            event.getChannel().sendMessage(prefixTooLongErrorEmbed).queue();
        } else {
            Database db = CroniServer._db;
            db.setPrefix(event.getGuild().getIdLong(), prefix);

            event.getChannel().sendMessage("Guild prefix has set to ``" + prefix + "``").queue();
        }
    }
}
