package com.monstahhh.croniserver.plugin.mrworldwide.commands;

import com.monstahhh.croniserver.plugin.croniserver.CroniServer;
import com.monstahhh.croniserver.sqlite.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
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

    private final MessageEmbed noPermissionsErrorEmbed = new EmbedBuilder()
            .setTitle("Mr. Error")
            .setColor(Color.RED)
            .addField("Set prefix error", "This command requires the ``Administrator`` permission", false)
            .build();

    public void carryCommand(GuildMessageReceivedEvent event, String cmdStripped) {
        Member m = event.getMessage().getMember();
        if (m != null) {
            if (!m.hasPermission(Permission.ADMINISTRATOR)) {
                event.getChannel().sendMessage(noPermissionsErrorEmbed).queue();
                return;
            }
        }

        String prefix = cmdStripped.split(" ")[1];

        if (prefix.isEmpty() || prefix.isBlank()) {
            event.getChannel().sendMessage(noPrefixErrorEmbed).queue();
            return;
        }

        if (prefix.length() > 6) {
            event.getChannel().sendMessage(prefixTooLongErrorEmbed).queue();
        } else {
            Database db = CroniServer._db;
            db.setPrefix(event.getGuild().getIdLong(), prefix.toLowerCase());

            event.getChannel().sendMessage("Guild prefix has set to ``" + prefix + "``").queue();
        }
    }
}
