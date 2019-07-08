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
            City city = new City(weatherToken, event.getChannel());
            MessageEmbed embed = city.getWeatherFor(providedLoc);
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
