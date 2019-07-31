package com.monstahhh.croniserver.plugin.mrworldwide.commands.weather;

import com.monstahhh.croniserver.plugin.dangerapi.configapi.Config;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class SetCity {

    public void carryCommand(GuildMessageReceivedEvent event) {
        String providedLoc = (event.getMessage().getContentRaw()).substring(8);
        if (providedLoc.contains(",")) {
            if ((providedLoc.split(","))[1].length() > 2) {
                argError(event, providedLoc);
            } else {
                this.setCityForUser(providedLoc.trim(), event.getAuthor().getIdLong());

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.ORANGE);
                eb.addField("User City", "City for " + event.getAuthor().getName() + " has been set to " + providedLoc, false);

                event.getChannel().sendMessage(eb.build()).queue();
            }
        } else {
            argError(event, providedLoc);
        }
    }

    private void setCityForUser(String location, long userId) {
        Config data = new Config("plugins/MrWorldWide", "users.yml", MrWorldWide._plugin);
        data.getConfig().set("locations.users." + userId, location);
        data.saveConfig();
    }

    private void argError(GuildMessageReceivedEvent event, String input) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Mr. Error");
        eb.setColor(Color.RED);
        eb.addField("Argument Error", "It seems that you have not entered the location correctly.", false);
        eb.addField("Example", "weather kortrijk,be", false);
        eb.setFooter("Your Input: weather " + input, null);

        event.getChannel().sendMessage(eb.build()).queue();
    }
}
