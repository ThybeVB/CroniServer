package com.monstahhh.croniserver.plugin.mrworldwide;

import com.jafregle.http.HttpClient;
import com.jafregle.http.HttpMethod;
import com.jafregle.http.HttpResponse;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class City {

    private final String baseLink = "http://api.openweathermap.org/data/2.5/weather";
    private final String params = "?q=%s&appid=%s&units=metric";
    EmbedBuilder defaultError = new EmbedBuilder()
            .setTitle("Mr. Error")
            .setColor(Color.RED);
    private String weatherToken;
    private TextChannel textChannel;

    public City(String token, TextChannel channel) {
        weatherToken = token;
        textChannel = channel;
    }

    public MessageEmbed getWeatherFor(String providedLocation) {
        try {
            HttpClient client = new HttpClient();
            String formattedSend = String.format(params, providedLocation, weatherToken);
            HttpResponse result = client.request(HttpMethod.GET, new StringBuilder(baseLink).append(formattedSend).toString());

            return getEmbedForLocationJson(result.asString());

        } catch (Exception e) {
            EmbedBuilder eb = defaultError;
            if (e.getMessage().contains("error: 404")) {
                eb.addField("Error 404", "The provided city could not be found.", false);
            } else {
                eb.addField("Exception", e.getMessage(), false);
            }

            textChannel.sendMessage(eb.build()).queue();

            return null;
        }
    }

    private MessageEmbed getEmbedForLocationJson(String json) throws JSONException {
        JSONObject object = new JSONObject(json);

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.ORANGE);

        String tempStr = object.getJSONObject("main").get("temp").toString();
        String minStr = object.getJSONObject("main").get("temp_min").toString();
        String maxStr = object.getJSONObject("main").get("temp_max").toString();

        int temp = Math.round(Float.parseFloat(tempStr));
        int min = Math.round(Float.parseFloat(minStr));
        int max = Math.round(Float.parseFloat(maxStr));

        eb.setTitle("Weather for " + object.getString("name") + ", " + object.getJSONObject("sys").getString("country"));
        eb.addField("Temperature", temp + "°C", false);

        if (min != max) {
            eb.addField("Minimum & Maximum", min + "°C | " + max + "°C", true);
        }

        Object sunRise = object.getJSONObject("sys").get("sunrise");
        Date sunRiseDate = new Date(Long.parseLong(sunRise.toString()) * 1000L + (object.getInt("timezone") * 1000L));
        Object sunSet = object.getJSONObject("sys").get("sunset");
        Date sunSetDate = new Date(Long.parseLong(sunSet.toString()) * 1000L + (object.getInt("timezone") * 1000L));

        Date current = new Date();
        current.setTime(current.getTime() + (object.getInt("timezone") * 1000L));

        SimpleDateFormat simpleTime = new java.text.SimpleDateFormat("HH:mm");
        simpleTime.setTimeZone(TimeZone.getTimeZone("UTC"));

        eb.addField("Sunrise & Sunset", "Sunrise: " + simpleTime.format(sunRiseDate) + " | Sunset: " + simpleTime.format(sunSetDate), false);
        eb.addField("Current Time", simpleTime.format(current), false);

        JSONArray currentWeatherArray = object.getJSONArray("weather");
        JSONObject currentWeather = currentWeatherArray.getJSONObject(0);
        eb.addField(currentWeather.getString("main"), fixWeatherDescription(currentWeather.getString("description")), false);

        eb.setThumbnail("http://openweathermap.org/img/w/" + currentWeather.getString("icon") + ".png");
        eb.setFooter("Crafted with lots of love by Pitbull and OpenWeather API", null);

        return eb.build();
    }

    private String fixWeatherDescription(String unfixedWeather) {
        String[] words = unfixedWeather.split(" ");

        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            char c = Character.toUpperCase(word.charAt(0));
            word = word.substring(1);
            String newWord = c + word + " ";
            sb.append(newWord);
        }

        return sb.toString();
    }
}
