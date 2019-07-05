package com.monstahhh.croniserver.plugin.mrworldwide.helper;

import com.jafregle.http.HttpClient;
import com.jafregle.http.HttpMethod;
import com.jafregle.http.HttpResponse;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;

public class Currency {

    private final String baseLink = "https://free.currconv.com/api/v7/";
    private final String params = "convert?q=%s_%s&compact=ultra&apiKey=%s";

    public void carryCommand(GuildMessageReceivedEvent event, String token) {
        try {
            String providedLoc = event.getMessage().getContentRaw().substring(8);
            String[] words = providedLoc.split(" ");

            Float amount = Float.parseFloat(words[0]);
            String base = words[1];
            String destination = words[2];

            float[] prices = getValueFor(base, destination, amount, token);
            MessageEmbed embed = getCurrencyEmbed(prices[0], prices[1], base, destination);

            event.getChannel().sendMessage(embed).queue();
        } catch (Exception e) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Mr. Error");
            eb.setColor(Color.RED);
            eb.addField("Argument Error", "It seems that you have not entered the currencies correctly.", false);
            eb.addField("Example", "convert 1.25 eur usd", false);
            event.getChannel().sendMessage(eb.build()).queue();
        }
    }

    private float[] getValueFor(String base, String destination, float amount, String token) {
        try {
            HttpClient client = new HttpClient();
            String formattedSend = String.format(params, base.toUpperCase(), destination.toUpperCase(), token);
            HttpResponse result = client.request(HttpMethod.GET, new StringBuilder(baseLink).append(formattedSend).toString());

            String res = result.asString();
            JSONObject obj = new JSONObject(res);
            String formedLanguageCode = base.toUpperCase() + "_" + destination.toUpperCase();

            float baseValue = Float.parseFloat(obj.get(formedLanguageCode).toString());
            float newPrice = baseValue * amount;

            return new float[]{amount, newPrice};

        } catch (Exception e) {
            return null;
        }
    }

    private MessageEmbed getCurrencyEmbed(float old, float newPrice, String origin, String destination) {
        String title = origin.toUpperCase() + " -> " + destination.toUpperCase();
        String priceStr = String.format("%.2f", newPrice);
        String finalStr = old + " " + origin.toUpperCase() + " -> " + priceStr + " " + destination.toUpperCase();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.GREEN);
        eb.setTitle("Currency Converter");
        eb.addField(title, finalStr, true);

        return eb.build();
    }
}