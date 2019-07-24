package com.monstahhh.croniserver.plugin.mrworldwide.commands;

import com.monstahhh.croniserver.plugin.mrworldwide.commands.weather.City;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.weather.WeatherHelper;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class Weather {

    public void carryCommand(GuildMessageReceivedEvent event, String weatherToken) {
        String providedLoc = (event.getMessage().getContentRaw()).substring(8);
        if (providedLoc.contains(",")) {
            if ((providedLoc.split(","))[1].length() > 2) {
                argError(event);
            } else {
                WeatherHelper helper = new WeatherHelper(weatherToken, event.getChannel());
                City city = helper.getWeatherFor(providedLoc);
                MessageEmbed embed = helper.getEmbedFor(city);

                if (embed != null) {
                    event.getChannel().sendMessage(embed).queue();
                }
            }
        } else {
            argError(event);
        }
    }

    private void argError(GuildMessageReceivedEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Mr. Error");
        eb.setColor(Color.RED);
        eb.addField("Argument Error", "It seems that you have not entered the location correctly.", false);
        eb.addField("Example", "weather kortrijk,be", false);

        event.getChannel().sendMessage(eb.build()).queue();
    }
}
