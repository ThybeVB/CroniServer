package com.monstahhh.croniserver.plugin.mrworldwide.commands.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class City {

    int temperature;
    int min;
    int max;
    int humidity;

    String cityName;
    String countryCode;
    String sunRiseTime;
    String sunSetTime;
    String currentTime;
    String windSpeed;
    String currentWeatherTitle;
    String currentWeatherDescription;
    String[] timeOfCalculation;
    String iconUrl;

    City getCityObjectForJson(String json) throws JSONException {

        JSONObject object = new JSONObject(json);

        String tempStr = object.getJSONObject("main").get("temp").toString();
        String minStr = object.getJSONObject("main").get("temp_min").toString();
        String maxStr = object.getJSONObject("main").get("temp_max").toString();
        String humidityStr = object.getJSONObject("main").get("humidity").toString();

        Object sunRise = object.getJSONObject("sys").get("sunrise");
        Date sunRiseDate = new Date(Long.parseLong(sunRise.toString()) * 1000L + (object.getInt("timezone") * 1000L));
        Object sunSet = object.getJSONObject("sys").get("sunset");
        Date sunSetDate = new Date(Long.parseLong(sunSet.toString()) * 1000L + (object.getInt("timezone") * 1000L));
        Object timeOfCalculation = object.get("dt");
        Date timeOfCalculationDate = new Date(Long.parseLong(timeOfCalculation.toString()) * 1000L + (object.getInt("timezone") * 1000L));

        Date current = new Date();
        current.setTime(current.getTime() + (object.getInt("timezone") * 1000L));

        TimeZone utc = TimeZone.getTimeZone("UTC");

        SimpleDateFormat simpleTime = new java.text.SimpleDateFormat("HH:mm");
        simpleTime.setTimeZone(utc);

        SimpleDateFormat minutesTime = new java.text.SimpleDateFormat("m");
        simpleTime.setTimeZone(utc);

        SimpleDateFormat secondsTime = new java.text.SimpleDateFormat("s");
        simpleTime.setTimeZone(utc);

        JSONArray currentWeatherArray = object.getJSONArray("weather");
        JSONObject currentWeather = currentWeatherArray.getJSONObject(0);

        Date timeSinceRecording = new Date(current.getTime() - timeOfCalculationDate.getTime());

        double windKilometersPerHour = object.getJSONObject("wind").getDouble("speed") * 3.6;

        this.windSpeed = String.valueOf((int)windKilometersPerHour);
        this.temperature = Math.round(Float.parseFloat(tempStr));
        this.min = Math.round(Float.parseFloat(minStr));
        this.max = Math.round(Float.parseFloat(maxStr));
        this.humidity = Math.round(Float.parseFloat(humidityStr));
        this.cityName = object.getString("name");
        this.countryCode = object.getJSONObject("sys").getString("country");
        this.sunRiseTime = simpleTime.format(sunRiseDate);
        this.sunSetTime = simpleTime.format(sunSetDate);
        this.currentTime = simpleTime.format(current);
        this.currentWeatherTitle = currentWeather.getString("main");
        this.currentWeatherDescription = fixWeatherDescription(currentWeather.getString("description"));
        this.timeOfCalculation = new String[]{minutesTime.format(timeSinceRecording), secondsTime.format(timeSinceRecording)};

        String iconUrl = "http://openweathermap.org/img/w/%s.png";
        this.iconUrl = String.format(iconUrl, currentWeather.getString("icon"));

        return this;
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
