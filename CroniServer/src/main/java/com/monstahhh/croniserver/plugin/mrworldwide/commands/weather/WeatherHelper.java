package com.monstahhh.croniserver.plugin.mrworldwide.commands.weather;

import com.monstahhh.croniserver.http.HttpClient;
import com.monstahhh.croniserver.http.HttpMethod;
import com.monstahhh.croniserver.http.HttpResponse;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.MessageEmbed;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;

public class WeatherHelper {

    private final EmbedBuilder defaultError = new EmbedBuilder()
            .setTitle("Mr. Error")
            .setColor(Color.RED);
    private final String weatherToken;
    private final TextChannel textChannel;

    public WeatherHelper(String token, TextChannel channel) {
        this.weatherToken = token;
        this.textChannel = channel;
    }

    public City getWeatherFor(String providedLocation, long userId) {
        try {
            HttpClient client = new HttpClient();
            String formattedSend = String.format("?q=%s&appid=%s&units=metric", providedLocation, weatherToken);
            HttpResponse result = client.request(HttpMethod.GET, ("http://api.openweathermap.org/data/2.5/weather" + formattedSend));

            String resultStr = result.asString();
            JSONObject obj = new JSONObject(resultStr);
            int statusCode = obj.getInt("cod");

            if (statusCode == 200) {
                return new City().getCityObjectForJson(resultStr, new ChangeClock().getUserTimeSetting(userId));
            } else {
                throw new Exception("Server returned code " + statusCode);
            }
        } catch (Exception e) {
            EmbedBuilder eb = defaultError;
            if (e.getMessage().contains("404")) {
                eb.addField("Error 404", "The provided city could not be found", false);
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
            eb.setColor(city.embedColor);
            eb.setThumbnail(city.iconUrl);

            if (city.temperature >= 40) {
                eb.setTitle("Weather for " + city.cityName + ", " + getCountryName(city.countryCode) + " <:40DEGREESFUCK:617781121236860963>");
            } else {
                eb.setTitle("Weather for " + city.cityName + ", " + getCountryName(city.countryCode));
            }
            eb.addField("Temperature", city.temperature + "°C", false);

            eb.addField("Horizon Events", "Sunrise: " + city.sunRiseTime + " | Sunset: " + city.sunSetTime, false);

            if (city.currentTime.charAt(0) == '0' && (city.currentTime.contains("AM") || city.currentTime.contains("PM"))) {
                eb.addField("Current Time", city.currentTime.substring(1), false);
            } else {
                eb.addField("Current Time", city.currentTime, false);
            }

            eb.addField(city.currentWeatherTitle, city.currentWeatherDescription + "at " + city.windSpeed + "km/h with " + city.humidity + "% humidity", false);

            String responsePrefix = "Made by Pitbull, ";
            String footer;
            if (city.timeOfCalculation[0].equals("0") && city.timeOfCalculation[1].equals("0")) {
                footer = responsePrefix + "Recorded just now";
            } else if (city.timeOfCalculation[0].equals("0")) {
                footer = responsePrefix + "Recorded " + city.timeOfCalculation[1] + " seconds ago";
            } else {
                if (city.timeOfCalculation[1].equalsIgnoreCase("0")) {
                    footer = responsePrefix + "Recorded " + city.timeOfCalculation[0] + " minute ago";
                } else if (city.timeOfCalculation[1].equalsIgnoreCase("1")) {
                    footer = responsePrefix + "Recorded " + city.timeOfCalculation[0] + " minutes and " + city.timeOfCalculation[1] + " second ago";
                } else {
                    footer = responsePrefix + "Recorded " + city.timeOfCalculation[0] + " minutes and " + city.timeOfCalculation[1] + " seconds ago";
                }
            }
            eb.setFooter(footer + ". FT: " + city.feelTemperature + "°C", null);

            return eb.build();
        } catch (Exception e) {
            if (e instanceof NullPointerException || e.getMessage() == null) {
                return null;
            }

            EmbedBuilder failEmbed = defaultError;
            failEmbed.addField("City Error", "Embed Creation failed: " + e.getCause().toString(), false);

            return failEmbed.build();
        }
    }

    private JSONObject getCountryInformation(String countryCode) {
        try {
            HttpResponse result = new HttpClient().request(HttpMethod.GET, ("https://restcountries.eu/rest/v2/alpha/" + countryCode));
            String res = result.asString();

            return new JSONObject(res);
        } catch (IOException exception) {
            return null;
        }
    }

    private String getCountryName(String countryCode) {
        JSONObject obj = getCountryInformation(countryCode);
        assert obj != null;
        return obj.getString("name");
    }
}
