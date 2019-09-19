package com.monstahhh.croniserver.plugin.mrworldwide.commands;

import com.monstahhh.croniserver.configapi.Config;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.weather.City;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.weather.WeatherHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class Weather {

    private final EmbedBuilder errorEmbed = new EmbedBuilder()
            .setTitle("Mr. Error")
            .setColor(Color.RED);

    private final EmbedBuilder invalidLocError = new EmbedBuilder()
            .setTitle("Mr. Error")
            .setColor(Color.RED)
            .addField("Argument Error", "It seems that you did not enter a valid location", false)
            .addField("Notice", "If you want to set your own city, use the setcity command.", false)
            .setFooter("Example: weather london,uk", null);

    private final String weatherToken;

    public Weather(String weatherToken) {
        this.weatherToken = weatherToken;
    }

    public void carryCommand(GuildMessageReceivedEvent event) {
        if (event.getMessage().getMentions().size() > 0) {
            this.carryMentionCommand(event);
        } else {
            if ((event.getMessage().getContentRaw().substring(7)).contains(",")) {
                this.carryCommandWithParams(event);
            } else {
                this.carryCommandWithNoParams(event);
            }
        }
    }

    private void carryMentionCommand(GuildMessageReceivedEvent event) {
        User mentionedUser = event.getMessage().getMentionedUsers().get(0);
        Member guildMember = event.getGuild().getMember(mentionedUser);
        assert guildMember != null;
        if (guildMember.getOnlineStatus() == OnlineStatus.OFFLINE) {
            EmbedBuilder eb = errorEmbed;
            eb.addField("Weather Error", "Mentioning an offline user is not allowed. Fight Me.", false);
            eb.setFooter("Your Input: " + event.getMessage().getContentRaw(), null);
            event.getChannel().sendMessage(eb.build()).queue();
            event.getMessage().delete().queue();
        } else {
            String possibleCity = this.checkForCity(mentionedUser);
            try {
                if (!possibleCity.isEmpty()) {
                    WeatherHelper helper = new WeatherHelper(this.weatherToken, event.getChannel());
                    City city = helper.getWeatherFor(possibleCity);
                    MessageEmbed embed = helper.getEmbedFor(city);

                    if (embed != null) {
                        event.getChannel().sendMessage(embed).queue();
                    }
                } else {
                    event.getChannel().sendMessage(mentionedUser.getName() + " has not set a city.").queue();
                }
            } catch (NullPointerException e) {
                event.getChannel().sendMessage(invalidLocError.build()).queue();
            }
        }
    }

    private void carryCommandWithParams(GuildMessageReceivedEvent event) {
        String providedLoc = (event.getMessage().getContentRaw()).substring(7);
        if ((providedLoc.split(","))[1].length() > 2) {
            argError(event, providedLoc);
        } else {
            WeatherHelper helper = new WeatherHelper(weatherToken, event.getChannel());
            City city = helper.getWeatherFor(providedLoc.trim().toLowerCase());
            MessageEmbed embed = helper.getEmbedFor(city);

            if (embed != null) {
                event.getChannel().sendMessage(embed).queue();
            }
        }

    }

    private void carryCommandWithNoParams(GuildMessageReceivedEvent event) {
        String providedLoc = (event.getMessage().getContentRaw()).substring(7);
        String possibleCity = this.checkForCity(event.getAuthor());
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
            event.getChannel().sendMessage(invalidLocError.build()).queue();
        }
    }

    private String checkForCity(User user) {
        Config userData = new Config("plugins/MrWorldWide", "users.yml", MrWorldWide._plugin);
        return userData.getConfig().getString("locations.users." + user.getIdLong());
    }

    private void argError(GuildMessageReceivedEvent event, String input) {
        EmbedBuilder eb = errorEmbed;
        eb.addField("Argument Error", "It seems that you have not entered the location correctly.", false);
        eb.addField("Example", "weather kortrijk,be", false);
        eb.setFooter("Your Input: weather " + input, null);

        event.getChannel().sendMessage(eb.build()).queue();
    }
}
