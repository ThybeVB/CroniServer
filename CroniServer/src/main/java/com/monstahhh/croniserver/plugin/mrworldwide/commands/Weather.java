package com.monstahhh.croniserver.plugin.mrworldwide.commands;

import com.monstahhh.croniserver.configapi.Config;
import com.monstahhh.croniserver.http.HttpClient;
import com.monstahhh.croniserver.http.HttpMethod;
import com.monstahhh.croniserver.http.HttpResponse;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.weather.City;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.weather.WeatherHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
        Message msg = event.getMessage();
        if (event.getChannel().getIdLong() != 316310737419108354L) {
            event.getChannel().sendMessage("Use <#316310737419108354> for weather reports!").queue((m) -> m.delete().queueAfter(10, TimeUnit.SECONDS));
        } else {
            if (msg.getMentions().size() > 0) {
                this.carryMentionCommand(event);
            } else {
                if ((msg.getContentRaw().substring(7)).contains(",")) {
                    this.carryCommandWithParams(event);
                } else {
                    if (!msg.getContentRaw().substring(7).isEmpty()) {
                        this.carryCommandWithCountry(event);
                    } else {
                        this.carryCommandWithNoParams(event);
                    }
                }
            }
        }
    }

    private void carryCommandWithCountry(GuildMessageReceivedEvent event) {
        String stripped = event.getMessage().getContentRaw().substring(8);
        if (stripped.toCharArray().length > 2) {
            JSONObject country = getCountryInformation(stripped);
            if (country == null) {
                event.getChannel().sendMessage("Could not find this country.").queue();
            } else {
                WeatherHelper helper = new WeatherHelper(this.weatherToken, event.getChannel());
                String cityStr = String.format("%s,%s", country.getString("capital"), country.getString("alpha2Code"));
                City city = helper.getWeatherFor(cityStr, event.getAuthor().getIdLong());
                MessageEmbed embed = helper.getEmbedFor(city);

                if (embed != null) {
                    event.getChannel().sendMessage(embed).queue();
                } else {
                    event.getChannel().sendMessage("Capital of this country does not have a weather station").queue();
                }
            }
        }
    }

    private JSONObject getCountryInformation(String countryName) {
        try {
            String formattedSend = String.format("https://restcountries.eu/rest/v2/name/%s", countryName);
            HttpResponse result = new HttpClient().request(HttpMethod.GET, formattedSend);

            String resultStr = result.asString();
            JSONArray arr = new JSONArray(resultStr);

            return arr.getJSONObject(0);
        } catch (IOException e) {
            return null;
        }
    }

    public void carryRawCommand(GuildMessageReceivedEvent event) {
        try {
            String formattedSend = String.format("?q=%s&appid=%s&units=metric", event.getMessage().getContentRaw().substring(10), weatherToken);
            HttpResponse result = new HttpClient().request(HttpMethod.GET, ("http://api.openweathermap.org/data/2.5/weather" + formattedSend));

            String resultStr = result.asString();
            JSONObject obj = new JSONObject(resultStr);

            event.getChannel().sendMessage(obj.toString()).queue();
        } catch (IOException e) {
            event.getChannel().sendMessage(e.getMessage()).queue();
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
                    City city = helper.getWeatherFor(possibleCity, guildMember.getIdLong());
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
            City city = helper.getWeatherFor(providedLoc.trim().toLowerCase(), event.getAuthor().getIdLong());
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
                City city = helper.getWeatherFor(possibleCity, event.getAuthor().getIdLong());
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
