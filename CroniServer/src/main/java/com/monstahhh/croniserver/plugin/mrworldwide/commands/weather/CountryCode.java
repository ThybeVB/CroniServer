package com.monstahhh.croniserver.plugin.mrworldwide.commands.weather;

import com.monstahhh.croniserver.http.HttpClient;
import com.monstahhh.croniserver.http.HttpMethod;
import com.monstahhh.croniserver.http.HttpResponse;
import github.scarsz.discordsrv.dependencies.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONArray;

import java.io.IOException;

public class CountryCode {

    public void carryCommand(GuildMessageReceivedEvent event, String cmdStripped) {
        String country = cmdStripped.substring(12);
        String countryCode = getCountryCode(country).toLowerCase();

        event.getChannel().sendMessage(country + " = " + countryCode).queue();
    }

    private String getCountryCode(String countryName) {
        try {
            HttpResponse result = new HttpClient().request(HttpMethod.GET, ("https://restcountries.eu/rest/v2/name/" + countryName));
            JSONArray arr = new JSONArray(result.asString());

            return arr.getJSONObject(0).getString("countryCode");
        } catch (IOException exception) {
            return "This country could not be found.";
        }
    }
}
