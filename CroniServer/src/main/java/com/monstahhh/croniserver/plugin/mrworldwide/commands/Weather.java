package com.monstahhh.croniserver.plugin.mrworldwide.commands;

import com.monstahhh.croniserver.plugin.dangerapi.configapi.Config;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.weather.City;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.weather.WeatherHelper;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class Weather {

    private final EmbedBuilder errorEmbed = new EmbedBuilder()
            .setTitle("Mr. Error")
            .setColor(Color.RED);

    public void carryCommand(GuildMessageReceivedEvent event, String weatherToken) {
        String providedLoc = (event.getMessage().getContentRaw()).substring(7);

        if (providedLoc.contains(",")) {
            if ((providedLoc.split(","))[1].length() > 2) {
                argError(event, providedLoc);
            } else {
                WeatherHelper helper = new WeatherHelper(weatherToken, event.getChannel());
                City city = helper.getWeatherFor(providedLoc.trim());
                MessageEmbed embed = helper.getEmbedFor(city);

                if (embed != null) {
                    event.getChannel().sendMessage(embed).queue();
                }
            }
        } else {
            String possibleCity = this.checkForCity(event);
            try {
                if (!possibleCity.isEmpty()) {
                    WeatherHelper helper = new WeatherHelper(weatherToken, event.getChannel());
                    City city = helper.getWeatherFor(possibleCity);
                    MessageEmbed embed = helper.getEmbedFor(city);

                    if (embed != null) {
                        event.getChannel().sendMessage(embed).queue();
                    } else {
                        argError(event, providedLoc);
                    }
                } else {
                    argError(event, providedLoc);
                }
            } catch (NullPointerException e) {
                EmbedBuilder eb = errorEmbed;
                eb.addField("Argument Error", "It seems that you did not enter a valid location", false);
                eb.addField("Notice", "If you want to set your own city, use the setcity command.", false);
                eb.setFooter("Example: weather london,uk", null);
                event.getChannel().sendMessage(eb.build()).queue();
            }
        }
    }

    private String checkForCity(GuildMessageReceivedEvent event) {
        Config userData = new Config("plugins/MrWorldWide", "users.yml", MrWorldWide._plugin);
        return userData.getConfig().getString("locations.users." + event.getAuthor().getIdLong());
    }

    private void argError(GuildMessageReceivedEvent event, String input) {
        EmbedBuilder eb = errorEmbed;
        eb.addField("Argument Error", "It seems that you have not entered the location correctly.", false);
        eb.addField("Example", "weather kortrijk,be", false);
        eb.setFooter("Your Input: weather " + input, null);

        event.getChannel().sendMessage(eb.build()).queue();
    }
}
