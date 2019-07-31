package com.monstahhh.croniserver.plugin.mrworldwide.event;

import com.monstahhh.croniserver.plugin.dangerapi.configapi.Config;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.Currency;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.weather.SetCity;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.Translate;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.Weather;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageReceivedEvent extends ListenerAdapter {

    private int translateCount = 0;
    private int weatherCount = 0;
    private int currencyCount = 0;

    private Config data = null;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        this.getUsageData();
        String message = event.getMessage().getContentRaw();

        if (message.toLowerCase().startsWith("translate ")) {
            Translate translate = new Translate();
            data.getConfig().set("usage.translateCommand", translateCount + 1);
            translate.carryCommand(event);
        }

        if (message.toLowerCase().startsWith("weather")) {
            Weather weather = new Weather();
            data.getConfig().set("usage.weatherCommand", weatherCount + 1);
            weather.carryCommand(event, MrWorldWide.weatherToken);
        }

        if (message.toLowerCase().startsWith("setcity ")) {
            SetCity setCity = new SetCity();
            setCity.carryCommand(event);
        }

        if (message.toLowerCase().startsWith("convert ")) {
            Currency currency = new Currency();
            data.getConfig().set("usage.currencyCommand", currencyCount + 1);
            currency.carryCommand(event, MrWorldWide.currencyToken);
        }

        if (message.toLowerCase().equals("usage") && event.getMessage().getAuthor().getIdLong() == 257247527630274561L) {
            event.getChannel().sendMessage("translateCount=" + translateCount + ";weatherCount=" + weatherCount + ";currencyCount=" + currencyCount).queue();
        }

        data.saveConfig();
    }

    private void getUsageData() {
        if (data == null) {
            data = new Config("plugins/MrWorldWide", "data.yml", MrWorldWide._plugin);
        }

        translateCount = data.getConfig().getInt("usage.translateCommand");
        weatherCount = data.getConfig().getInt("usage.weatherCommand");
        currencyCount = data.getConfig().getInt("usage.currencyCommand");
    }
}
