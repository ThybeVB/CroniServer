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
                argError(event, providedLoc, 1);
            } else {
                String[] letters = providedLoc.split(",");
                if (letters.length >= 2) {
                    argError(event, providedLoc, 0);
                } else {
                    WeatherHelper helper = new WeatherHelper(weatherToken, event.getChannel());
                    City city = helper.getWeatherFor(providedLoc);
                    MessageEmbed embed = helper.getEmbedFor(city);

                    if (embed != null) {
                        event.getChannel().sendMessage(embed).queue();
                    }
                }
            }
        } else {
            argError(event, providedLoc, 0);
        }
    }

    private void argError(GuildMessageReceivedEvent event, String input, int code) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Mr. Error");
        eb.setColor(Color.RED);
        if (code == 1) {
            eb.addField("Argument Error", "You seem to have entered 2 lines.", false);
        } else {
            eb.addField("Argument Error", "It seems that you have not entered the location correctly.", false);
        }
        eb.addField("Example", "weather kortrijk,be", false);
        eb.setFooter("Your Input: weather " + input, null);

        event.getChannel().sendMessage(eb.build()).queue();
    }
}
