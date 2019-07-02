package com.monstahhh.croniserver.plugin.mrworldwide.helper;

import com.jafregle.http.HttpClient;
import com.jafregle.http.HttpMethod;
import com.jafregle.http.HttpResponse;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Weather {

    private final String baseLink = "http://api.openweathermap.org/data/2.5/weather";
    private final String params = "?q=%s&appid=%s&units=metric";

    public void carryCommand(GuildMessageReceivedEvent event, String weatherToken) {
        String providedLoc = (event.getMessage().getContentRaw()).substring(8);
        if (providedLoc.contains(",")) {
            event.getChannel().sendMessage(getWeatherFor(providedLoc, weatherToken, event)).queue();
        } else {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Mr. Error");
            eb.setColor(Color.RED);
            eb.addField("Argument Error", "It seems that you have not entered the location correctly.", false);
            eb.addField("Example", "weather Kortrijk,BE", false);

            event.getChannel().sendMessage(eb.build()).queue();
        }
    }

    private MessageEmbed getWeatherFor(String providedLocation, String token, GuildMessageReceivedEvent event) {
        try {
            HttpClient client = new HttpClient();
            String formattedSend = String.format(params, providedLocation, token);
            HttpResponse result = client.request(HttpMethod.GET, new StringBuilder(baseLink).append(formattedSend).toString());

            return getEmbedForLocationJson(result.asString());

        } catch (Exception e) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Mr. Error");
            eb.setColor(Color.RED);
            eb.addField("Exception", e.getMessage(), false);

            event.getChannel().sendMessage(eb.build()).queue();
        }

        return null;
    }

    private MessageEmbed getEmbedForLocationJson(String json) throws JSONException {
        JSONObject object = new JSONObject(json);

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.ORANGE);
        eb.setTitle("Weather for " + object.getString("name"));
        eb.addField("Temperature", object.getJSONObject("main").get("temp").toString() + "°C", false);
        eb.addField("Minimum", object.getJSONObject("main").get("temp_min").toString() + "°C", true);
        eb.addField("Maximum", object.getJSONObject("main").get("temp_max").toString() + "°C", true);

        Object sunRise = object.getJSONObject("sys").get("sunrise");
        Date sunRiseDate = new Date(Long.parseLong(sunRise.toString())*1000L);
        Object sunSet = object.getJSONObject("sys").get("sunset");
        Date sunSetDate = new Date(Long.parseLong(sunSet.toString())*1000L);

        SimpleDateFormat simpleRise = new java.text.SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat simpleSet = new java.text.SimpleDateFormat("HH:mm:ss");

        eb.addField("Sunrise", simpleRise.format(sunRiseDate), false);
        eb.addField("Sunset", simpleSet.format(sunSetDate), true);

        eb.setFooter("Crafted with lots of love by Monstahhh and OpenWeather API", null);

        return eb.build();
    }
}
