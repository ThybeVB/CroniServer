package com.monstahhh.croniserver.plugin.mrworldwide.helper;

import com.jafregle.http.HttpClient;
import com.jafregle.http.HttpMethod;
import com.jafregle.http.HttpResponse;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.io.IOException;

public class Weather {

    private final String baseLink = "http://api.openweathermap.org/data/2.5/weather";
    private final String params = "?q=%s&appid=%s&units=metric";

    public void carryCommand(GuildMessageReceivedEvent event, String weatherToken) {
        String providedLoc = (event.getMessage().getContentRaw()).substring(8);
        if (providedLoc.contains(",")) {
            getWeatherFor(providedLoc, weatherToken, event);
        } else {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Mr. Error");
            eb.setColor(Color.RED);
            eb.addField("Argument Error", "It seems that you have not entered the location correctly.", false);
            eb.addField("Example", "weather Kortrijk,BE", false);

            event.getChannel().sendMessage(eb.build()).queue();
        }
    }

    private void getWeatherFor(String providedLocation, String token, GuildMessageReceivedEvent event) {
        try {
            HttpClient client = new HttpClient();
            String formattedSend = String.format(params, providedLocation, token);

            HttpResponse result = client.request(HttpMethod.GET, new StringBuilder(baseLink).append(formattedSend).toString());
            event.getChannel().sendMessage(result.asString()).queue();

        } catch (IOException e) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Mr. Error");
            eb.setColor(Color.RED);
            eb.addField("IO Error", e.getMessage(), false);

            event.getChannel().sendMessage(eb.build()).queue();
        }
    }
}
