package com.monstahhh.croniserver.plugin.mrworldwide.commands;

import com.monstahhh.croniserver.http.HttpClient;
import com.monstahhh.croniserver.http.HttpMethod;
import com.monstahhh.croniserver.http.HttpResponse;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;

public class Currency {

    private EmbedBuilder defaultError = new EmbedBuilder()
            .setTitle("Mr. Error")
            .setColor(Color.RED);

    public void carryCommand(GuildMessageReceivedEvent event, String token, String strippedMsg) {
        try {
            String providedLoc = strippedMsg.substring(8);
            String[] words = providedLoc.split(" ");

            float amount = Float.parseFloat(words[0]);
            String base = words[1];
            String destination = words[2];

            if (base.equalsIgnoreCase(destination)) {
                EmbedBuilder eb = defaultError;
                eb.addField("Converter Error", "You can't enter the same currency twice!", false);
                eb.addField("Example", "convert 1.25 eur usd", false);
                event.getChannel().sendMessage(eb.build()).queue();
                return;
            }

            float[] prices = getValueFor(base, destination, amount, token);
            if (prices != null) {
                if (prices[0] == 503 && prices[1] == 0) {
                    EmbedBuilder eb = defaultError;
                    eb.addField("Server Error", "503: Currency Server was unable to be reached.", false);

                    event.getChannel().sendMessage(eb.build()).queue();
                } else {
                    MessageEmbed embed = getCurrencyEmbed(prices[0], prices[1], base, destination);
                    event.getChannel().sendMessage(embed).queue();
                }
            } else {
                EmbedBuilder eb = defaultError;
                eb.addField("Currency Error", "It seems that one or both of the currencies are wrong or don't exist.", false);
                eb.addField("Example", "convert 1.25 eur usd", false);
                event.getChannel().sendMessage(eb.build()).queue();
            }
        } catch (Exception e) {
            EmbedBuilder eb = defaultError;
            eb.addField("Argument Error", "It seems that you have not entered the currencies correctly.", false);
            eb.addField("Example", "convert 1.25 eur usd", false);
            event.getChannel().sendMessage(eb.build()).queue();
        }
    }

    private float[] getValueFor(String base, String destination, float amount, String token) {
        try {
            String params = "convert?q=%s_%s&compact=ultra&apiKey=%s";
            String formattedSend = String.format(params, base.toUpperCase(), destination.toUpperCase(), token);
            HttpResponse result = new HttpClient().request(HttpMethod.GET, ("https://free.currconv.com/api/v7/" + formattedSend));
            String res = result.asString();

            JSONObject obj = new JSONObject(res);
            String formedLanguageCode = base.toUpperCase() + "_" + destination.toUpperCase();

            float baseValue = Float.parseFloat(obj.get(formedLanguageCode).toString());
            float newPrice = baseValue * amount;

            return new float[]{amount, newPrice};

        } catch (Exception e) {
            if (e.getMessage().contains("503")) {
                return new float[]{503, 0};
            }
            MrWorldWide.debugLog("Currency#getValueFor Error:" + e.getMessage());
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