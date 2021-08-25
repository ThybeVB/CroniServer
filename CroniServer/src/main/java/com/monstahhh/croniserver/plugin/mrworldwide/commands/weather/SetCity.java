package com.monstahhh.croniserver.plugin.mrworldwide.commands.weather;

import com.monstahhh.croniserver.configapi.Config;
import com.monstahhh.croniserver.http.HttpClient;
import com.monstahhh.croniserver.http.HttpMethod;
import com.monstahhh.croniserver.http.HttpResponse;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;

public class SetCity {

    public void carryCommand(GuildMessageReceivedEvent event, String weatherToken, String cmdStripped) {
        String providedLoc = cmdStripped.substring(8);
        if (providedLoc.contains(",")) {
            if ((providedLoc.split(","))[1].length() > 2) {
                argError(event, providedLoc);
            } else {
                if (cityExists(providedLoc, weatherToken)) {
                    this.setCityForUser(providedLoc.trim(), event.getAuthor().getIdLong());

                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(Color.ORANGE);
                    eb.addField("User City", "City for " + event.getAuthor().getName() + " has been set to " + providedLoc, false);

                    event.getChannel().sendMessageEmbeds(eb.build()).queue();
                } else {
                    event.getChannel().sendMessage("City could not be found.").queue();
                }
            }
        } else {
            argError(event, providedLoc);
        }
    }

    private boolean cityExists(String location, String weatherToken) {
        try {
            String formattedSend = String.format("?q=%s&appid=%s&units=metric", location, weatherToken);
            HttpResponse result = new HttpClient().request(HttpMethod.GET, ("http://api.openweathermap.org/data/2.5/weather" + formattedSend));

            String resultStr = result.asString();
            JSONObject obj = new JSONObject(resultStr);
            int statusCode = obj.getInt("cod");

            return statusCode == 200;
        } catch (IOException exception) {
            if (exception.getMessage().contains("404")) {
                return false;
            }
            System.out.println("cityExists Error: " + exception.getMessage());
            return false;
        }
    }

    private void setCityForUser(String location, long userId) {
        Config data = new Config("plugins/MrWorldWide", "users.yml");
        data.getConfig().set("locations.users." + userId, location.toLowerCase().trim());
        data.saveConfig();
    }

    private void argError(GuildMessageReceivedEvent event, String input) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Mr. Error");
        eb.setColor(Color.RED);
        eb.addField("Argument Error", "It seems that you have not entered the location correctly.", false);
        eb.addField("Example", "weather kortrijk,be", false);
        eb.setFooter("Your Input: weather " + input, null);

        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }
}
