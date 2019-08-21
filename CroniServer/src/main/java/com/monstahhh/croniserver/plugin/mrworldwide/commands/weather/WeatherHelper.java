package com.monstahhh.croniserver.plugin.mrworldwide.commands.weather;

import com.monstahhh.croniserver.http.HttpClient;
import com.monstahhh.croniserver.http.HttpMethod;
import com.monstahhh.croniserver.http.HttpResponse;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import org.json.JSONObject;

import java.awt.*;

public class WeatherHelper {

    private final String baseLink = "http://api.openweathermap.org/data/2.5/weather";
    private final String params = "?q=%s&appid=%s&units=metric";

    private EmbedBuilder defaultError = new EmbedBuilder()
            .setTitle("Mr. Error")
            .setColor(Color.RED);
    private String weatherToken;
    private TextChannel textChannel;

    public WeatherHelper(String token, TextChannel channel) {
        weatherToken = token;
        textChannel = channel;
    }

    public City getWeatherFor(String providedLocation) {
        try {
            HttpClient client = new HttpClient();
            String formattedSend = String.format(params, providedLocation, weatherToken);
            HttpResponse result = client.request(HttpMethod.GET, (baseLink + formattedSend));

            String resultStr = result.asString();
            JSONObject obj = new JSONObject(resultStr);
            int statusCode = obj.getInt("cod");

            if (statusCode == 200) {
                return new City().getCityObjectForJson(resultStr);
            } else {
                throw new Exception("Server returned code " + statusCode);
            }
        } catch (Exception e) {
            EmbedBuilder eb = defaultError;
            if (e.getMessage().contains("404")) {
                eb.addField("Error 404", "The provided city could not be found.", false);
            } else {
                eb.addField("Exception", e.getMessage(), false);
            }

            textChannel.sendMessage(eb.build()).queue();

            return null;
        }
    }

    public MessageEmbed getEmbedFor(City city) {
        try {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.ORANGE);

            eb.setThumbnail(city.iconUrl);

            eb.setTitle("Weather for " + city.cityName + ", " + city.countryCode);
            eb.addField("Temperature", city.temperature + "°C", false);

            if (city.min != city.max) {
                eb.addField("Minimum & Maximum", city.min + "°C | " + city.max + "°C", true);
            }

            eb.addField("Sunrise & Sunset", "Sunrise: " + city.sunRiseTime + " | Sunset: " + city.sunSetTime, false);
            eb.addField("Current Time", city.currentTime, false);
            eb.addField(city.currentWeatherTitle, city.currentWeatherDescription + "at " + city.windSpeed + "km/h with " + city.humidity + "% humidity", false);

            if (city.timeOfCalculation[0].equals("0") && city.timeOfCalculation[1].equals("0")) {
                eb.setFooter("Made by Pitbull, Recorded just now", null);
            } else if (city.timeOfCalculation[0].equals("0")) {
                eb.setFooter("Made by Pitbull, Recorded " + city.timeOfCalculation[1] + " seconds ago", null);
            } else {
                eb.setFooter("Made by Pitbull, Recorded " + city.timeOfCalculation[0] + " minutes and " + city.timeOfCalculation[1] + " seconds ago", null);
            }

            return eb.build();
        } catch (Exception e) {
            if (e instanceof NullPointerException || e.getMessage() == null) {
                return null;
            }

            EmbedBuilder failEmbed = defaultError;
            failEmbed.addField("City Error", "Embed Creation failed: " + e.getMessage(), false);

            return failEmbed.build();
        }
    }
}
