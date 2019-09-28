package com.monstahhh.croniserver.plugin.mrworldwide.commands.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class City {

    int temperature;
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

    Color embedColor;

    City getCityObjectForJson(String json, ChangeClock.Time time) throws JSONException {

        JSONObject object = new JSONObject(json);

        String tempStr = object.getJSONObject("main").get("temp").toString();
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

        SimpleDateFormat simpleTime;
        if (time == ChangeClock.Time.TWENTYFOURHOUR) {
            simpleTime = new java.text.SimpleDateFormat("HH:mm");
        } else {
            simpleTime = new java.text.SimpleDateFormat("hh:mm a");
        }
        simpleTime.setTimeZone(utc);

        SimpleDateFormat minutesTime = new java.text.SimpleDateFormat("m");
        simpleTime.setTimeZone(utc);

        SimpleDateFormat secondsTime = new java.text.SimpleDateFormat("s");
        simpleTime.setTimeZone(utc);

        JSONArray currentWeatherArray = object.getJSONArray("weather");
        JSONObject currentWeather = currentWeatherArray.getJSONObject(0);

        Date timeSinceRecording = new Date(current.getTime() - timeOfCalculationDate.getTime());

        double windKilometersPerHour = object.getJSONObject("wind").getDouble("speed") * 3.6;

        int temperature = Math.round(Float.parseFloat(tempStr));
        this.temperature = temperature;
        this.humidity = Math.round(Float.parseFloat(humidityStr));
        this.cityName = object.getString("name");
        this.countryCode = object.getJSONObject("sys").getString("country");
        this.sunRiseTime = simpleTime.format(sunRiseDate);
        this.sunSetTime = simpleTime.format(sunSetDate);
        this.currentTime = simpleTime.format(current);
        this.windSpeed = String.valueOf((int) windKilometersPerHour);
        this.currentWeatherTitle = currentWeather.getString("main");
        this.currentWeatherDescription = fixWeatherDescription(currentWeather.getString("description"));
        this.timeOfCalculation = new String[]{minutesTime.format(timeSinceRecording), secondsTime.format(timeSinceRecording)};

        String iconUrl = "http://openweathermap.org/img/w/%s.png";
        this.iconUrl = String.format(iconUrl, currentWeather.getString("icon"));

        this.embedColor = getEmbedColor(temperature);

        return this;
    }

    private Color getEmbedColor(int temperature) {
        if (temperature >= 40) {
            return Color.BLACK;
        }
        if (temperature >= 30) {
            return Color.RED;
        }
        if (temperature >= 15) {
            return Color.ORANGE;
        }
        if (temperature > 0) {
            return Color.CYAN;
        }
        if (temperature < 0) {
            return Color.BLUE;
        }

        return Color.BLUE;
    }

    private String fixWeatherDescription(String unfixedWeather) {
        String[] words = unfixedWeather.split(" ");

        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            char beginCharacter = Character.toUpperCase(word.charAt(0));
            String newWord = beginCharacter + word.substring(1) + " ";
            sb.append(newWord);
        }

        return sb.toString();
    }
}
