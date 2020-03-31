package com.monstahhh.croniserver.plugin.mrworldwide.commands;

import com.monstahhh.croniserver.http.HttpClient;
import com.monstahhh.croniserver.http.HttpMethod;
import com.monstahhh.croniserver.http.HttpResponse;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class CountryFlag  {

    Random random = new Random();

    public static String name;
    public static EmbedBuilder builder;
    public static Long msgId;

    public void carryCommand (GuildMessageReceivedEvent event) {
        JSONArray countries = getAllCountries();
        assert countries != null;
        int totalCountryAmount = countries.length();

        JSONObject country = (JSONObject)countries.get(random.nextInt(totalCountryAmount));
        String countryName = country.getString("name");

        if (countryName.contains("(") && countryName.contains(")")) {
            carryCommand(event);
        }
        try {
            JSONArray blocs = country.getJSONArray("regionalBlocs");
            JSONObject bloc = (JSONObject)blocs.get(0);
            if (bloc.getString("acronym").equalsIgnoreCase("PA")) {
                carryCommand(event);
            }
        } catch (Exception ignored) { }

        String flagMsg = String.format(":flag_%s:", country.getString("alpha2Code").toLowerCase());

        name = countryName.toLowerCase();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.YELLOW);
        eb.addField("Flag", flagMsg, false);
        builder = eb;

        msgId = event.getChannel().sendMessage(eb.build()).complete().getIdLong();
        FlagListener flagListener = new FlagListener(event.getChannel());
        event.getJDA().addEventListener(flagListener);

        Bukkit.getScheduler().runTaskLater(MrWorldWide._plugin, () -> {
            event.getChannel().sendMessage("Timeout!").queue();
            event.getJDA().removeEventListener(flagListener);
        }, 400); //20 seconds

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

class FlagListener extends ListenerAdapter {
    private final long channelId;

    public FlagListener(MessageChannel channel) {
        this.channelId = channel.getIdLong();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (event.getChannel().getIdLong() != channelId) return;

        MessageChannel channel = event.getChannel();
        String content = event.getMessage().getContentDisplay();

        if (content.equalsIgnoreCase("flag")) return;
        if (content.toLowerCase().startsWith(CountryFlag.name)) {
            channel.sendMessage("Correct!").queue();
            event.getJDA().removeEventListener(this);
        }
        else {
            EmbedBuilder eb = CountryFlag.builder;
            eb.setFooter("Wrong!");
            event.getChannel().editMessageById(CountryFlag.msgId, eb.build()).queue();
        }
    }
}
