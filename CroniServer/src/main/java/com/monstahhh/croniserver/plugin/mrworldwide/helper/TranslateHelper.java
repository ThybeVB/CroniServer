package com.monstahhh.croniserver.plugin.mrworldwide.helper;

import com.monstahhh.croniserver.http.HttpClient;
import com.monstahhh.croniserver.http.HttpMethod;
import com.monstahhh.croniserver.http.HttpResponse;
import com.monstahhh.croniserver.plugin.mrworldwide.City;
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

public class TranslateHelper {

    private final String baseLink = "http://api.openweathermap.org/data/2.5/weather";
    private final String params = "?q=%s&appid=%s&units=metric";

    private EmbedBuilder defaultError = new EmbedBuilder()
            .setTitle("Mr. Error")
            .setColor(Color.RED);
    private String weatherToken;
    private TextChannel textChannel;

    public TranslateHelper(String token, TextChannel channel) {
        weatherToken = token;
        textChannel = channel;
    }

    public City getWeatherFor(String providedLocation) {
        try {
            HttpClient client = new HttpClient();
            String formattedSend = String.format(params, providedLocation, weatherToken);
            HttpResponse result = client.request(HttpMethod.GET, new StringBuilder(baseLink).append(formattedSend).toString());

            return getCityObjectForJson(result.asString());

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

    private City getCityObjectForJson(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        City city = new City();

        String tempStr = object.getJSONObject("main").get("temp").toString();
        String minStr = object.getJSONObject("main").get("temp_min").toString();
        String maxStr = object.getJSONObject("main").get("temp_max").toString();

        city.temperature = Math.round(Float.parseFloat(tempStr));
        city.min = Math.round(Float.parseFloat(minStr));
        city.max = Math.round(Float.parseFloat(maxStr));

        city.cityName = object.getString("name");
        city.countryCode = object.getJSONObject("sys").getString("country");

        Object sunRise = object.getJSONObject("sys").get("sunrise");
        Date sunRiseDate = new Date(Long.parseLong(sunRise.toString()) * 1000L + (object.getInt("timezone") * 1000L));
        Object sunSet = object.getJSONObject("sys").get("sunset");
        Date sunSetDate = new Date(Long.parseLong(sunSet.toString()) * 1000L + (object.getInt("timezone") * 1000L));

        Date current = new Date();
        current.setTime(current.getTime() + (object.getInt("timezone") * 1000L));

        SimpleDateFormat simpleTime = new java.text.SimpleDateFormat("HH:mm");
        simpleTime.setTimeZone(TimeZone.getTimeZone("UTC"));

        city.sunRiseTime = simpleTime.format(sunRiseDate);
        city.sunSetTime = simpleTime.format(sunSetDate);
        city.currentTime = simpleTime.format(current);

        JSONArray currentWeatherArray = object.getJSONArray("weather");
        JSONObject currentWeather = currentWeatherArray.getJSONObject(0);

        city.currentWeatherTitle = currentWeather.getString("main");
        city.currentWeatherDescription = fixWeatherDescription(currentWeather.getString("description"));

        city.iconUrl = "http://openweathermap.org/img/w/" + currentWeather.getString("icon") + ".png";

        return city;
    }

    public MessageEmbed getEmbedFor(City city) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.ORANGE);

        eb.setTitle("Weather for " + city.cityName + ", " + city.countryCode);
        eb.addField("Temperature", city.temperature + "°C", false);

        if (city.min != city.max) {
            eb.addField("Minimum & Maximum", city.min + "°C | " + city.max + "°C", true);
        }

        eb.addField("Sunrise & Sunset", "Sunrise: " + city.sunRiseTime + " | Sunset: " + city.sunSetTime, false);

        eb.addField(city.currentWeatherTitle, city.currentWeatherDescription, false);

        eb.addField("Current Time", city.currentTime, false);

        eb.setThumbnail(city.iconUrl);
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
