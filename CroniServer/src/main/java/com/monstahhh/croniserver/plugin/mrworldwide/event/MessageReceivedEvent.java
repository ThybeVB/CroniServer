package com.monstahhh.croniserver.plugin.mrworldwide.event;

import com.monstahhh.croniserver.configapi.Config;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.Currency;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.Translate;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.Weather;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.weather.ChangeClock;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.weather.SetCity;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceivedEvent extends ListenerAdapter {

    private int translateCount = 0;
    private int conversationCount = 0;
    private int weatherCount = 0;
    private int currencyCount = 0;

    private Config data = null;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        this.getUsageData();
        String message = event.getMessage().getContentRaw();

        if (message.toLowerCase().startsWith("weather")) {
            Weather weather = new Weather(MrWorldWide.weatherToken);
            data.getConfig().set("usage.weatherCommand", weatherCount + 1);
            weather.carryCommand(event);
        }

        if (message.toLowerCase().startsWith("setcity ")) {
            SetCity setCity = new SetCity();
            setCity.carryCommand(event, MrWorldWide.weatherToken);
        }

        if (message.toLowerCase().equalsIgnoreCase("changeclock")) {
            ChangeClock changeClock = new ChangeClock();
            changeClock.carryCommand(event);
        }

        if (event.getMessage().getMentions().size() > 0) {
            if ((event.getMessage().getMentions().get(0)).getIdLong() == 443510227380207646L) {
                if (event.getMessage().getContentRaw().contains("help")) {
                    String helpMsg = "```----- Mr. Worldwide Commands -----" +
                            "\n* <> = Required Field*" +
                            "\n> weather <cityname,countrycode>" +
                            "\n> weather <countryname>" +
                            "\n> weather *(If 'setcity' has been used)*" +
                            "\n> weather <@Debiller#7777>" +
                            "\n> setcity <cityname,countrycode>" +
                            "\n> changeclock" +
                            "\n> translate <originLanguage> <newLanguage> <message>" +
                            "\n> trs <originLanguage> <newLanguage> <message>" +
                            "\n> convert <amount> <originCurrency> <newCurrency>" +
                            "\n----------------------------------```";
                    event.getChannel().sendMessage(helpMsg).queue();
                }
            }
        }

        if (message.toLowerCase().startsWith("translate ")) {
            Translate translate = new Translate();
            data.getConfig().set("usage.translateCommand", translateCount + 1);
            translate.carryCommand(event);
        }

        if (message.toLowerCase().startsWith("trs ")) {
            Translate translate = new Translate();
            data.getConfig().set("usage.conversationCommand", conversationCount + 1);
            translate.carryConversationCommand(event);
        }

        if (message.toLowerCase().startsWith("convert ")) {
            Currency currency = new Currency();
            data.getConfig().set("usage.currencyCommand", currencyCount + 1);
            currency.carryCommand(event, MrWorldWide.currencyToken);
        }

        if (event.getMessage().getAuthor().getIdLong() == MrWorldWide.OwnerId) {
            if (message.toLowerCase().equals("usage")) {
                String dataString = "translateCount=" + translateCount + ";weatherCount=" + weatherCount + ";currencyCount=" + currencyCount + ";conversationTranslateCount=" + conversationCount;
                event.getChannel().sendMessage(dataString).queue();
            }

            if (message.toLowerCase().startsWith("rawweather ")) {
                Weather weather = new Weather(MrWorldWide.weatherToken);
                weather.carryRawCommand(event);
            }
        }


        data.saveConfig();
    }

    private void getUsageData() {
        if (data == null) {
            data = new Config("plugins/MrWorldWide", "data.yml");
        }

        this.translateCount = data.getConfig().getInt("usage.translateCommand");
        this.conversationCount = data.getConfig().getInt(("usage.conversationCommand"));
        this.weatherCount = data.getConfig().getInt("usage.weatherCommand");
        this.currencyCount = data.getConfig().getInt("usage.currencyCommand");
    }
}
