package com.monstahhh.croniserver.plugin.mrworldwide.helper;

import com.monstahhh.croniserver.plugin.mrworldwide.City;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class Weather {

    public void carryCommand(GuildMessageReceivedEvent event, String weatherToken) {
        String providedLoc = (event.getMessage().getContentRaw()).substring(8);
        if (providedLoc.contains(",")) {
            TranslateHelper helper = new TranslateHelper(weatherToken, event.getChannel());
            City city = helper.getWeatherFor(providedLoc);
            MessageEmbed embed = helper.getEmbedFor(city);

            if (embed != null) {
                event.getChannel().sendMessage(embed).queue();
            }
        } else {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Mr. Error");
            eb.setColor(Color.RED);
            eb.addField("Argument Error", "It seems that you have not entered the location correctly.", false);
            eb.addField("Example", "weather kortrijk,be", false);

            event.getChannel().sendMessage(eb.build()).queue();
        }
    }
}
