package com.monstahhh.croniserver.plugin.mrworldwide.commands.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class City {

    public int temperature;
    public int min;
    public int max;
    public String cityName;
    public String countryCode;
    public String sunRiseTime;
    public String sunSetTime;
    public String currentTime;
    public String currentWeatherTitle;
    public String currentWeatherDescription;
    public String iconUrl;

    public City getCityObjectForJson(String json) throws JSONException {
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
