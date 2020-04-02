package com.monstahhh.croniserver.plugin.mrworldwide.commands;

import com.monstahhh.croniserver.http.HttpClient;
import com.monstahhh.croniserver.http.HttpMethod;
import com.monstahhh.croniserver.http.HttpResponse;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CountryFlag {

    Random random = new Random();
    private EmbedBuilder eb;

    public void carryCommand(GuildMessageReceivedEvent event) {
        JSONArray countries = getAllCountries();
        assert countries != null;
        int totalCountryAmount = countries.length();

        JSONObject country = (JSONObject) countries.get(random.nextInt(totalCountryAmount));
        String countryName = country.getString("name");

        if (countryName.contains("(")) {
            carryCommand(event);
        }

        try {
            JSONArray blocs = country.getJSONArray("regionalBlocs");
            JSONObject bloc = (JSONObject) blocs.get(0);
            if (bloc.getString("acronym").equalsIgnoreCase("PA")) {
                carryCommand(event);
            }
        } catch (Exception ignored) {
            //Country does not have regional bloc
        }

        String flagMsg = String.format(":flag_%s:", country.getString("alpha2Code").toLowerCase());

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.YELLOW);
        eb.addField("Guess the flag", flagMsg, false);
        event.getChannel().sendMessage(eb.build()).queue();
        this.eb = eb;

        guess(event, countryName);
    }

    private void guess(GuildMessageReceivedEvent event, String countryName) {
        MrWorldWide.eventWaiter.waitForEvent(MessageReceivedEvent.class,
                e -> e.getAuthor().equals(event.getAuthor())
                        && e.getChannel().equals(event.getChannel())
                        && !e.getAuthor().isBot()
                        && !e.getMessage().getContentRaw().equalsIgnoreCase("flag")
                        && !e.getMessage().equals(event.getMessage()),
                e -> evaluate(event, countryName),
                15, TimeUnit.SECONDS, () -> event.getChannel().sendMessage("Sorry, you took too long").queue());
    }

    private void evaluate(GuildMessageReceivedEvent event, String countryName) {
        MessageChannel channel = event.getChannel();
        String content = event.getMessage().getContentRaw();
        if (content.toLowerCase().startsWith(countryName.toLowerCase())) {
            channel.sendMessage("Correct!").queue();
        } else {
            event.getChannel().sendMessage("Wrong").queue();
        }
        event.getChannel().sendMessage(content).queue();
    }

    private JSONArray getAllCountries() {
        try {
            HttpResponse result = new HttpClient().request(HttpMethod.GET, ("https://restcountries.eu/rest/v2/all"));
            String res = result.asString();

            return new JSONArray(res);
        } catch (IOException exception) {
            return null;
        }
    }
}