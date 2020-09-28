package com.monstahhh.croniserver.plugin.mrworldwide.event;

import com.monstahhh.croniserver.configapi.Config;
import com.monstahhh.croniserver.plugin.croniserver.CroniServer;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.*;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.weather.ChangeClock;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.weather.CountryCode;
import com.monstahhh.croniserver.plugin.mrworldwide.commands.weather.SetCity;
import com.monstahhh.croniserver.sqlite.Database;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceivedEvent extends ListenerAdapter {

    public static boolean inMaintenance = false;
    private int translateCount = 0;
    private int conversationCount = 0;
    private int weatherCount = 0;
    private int currencyCount = 0;
    private boolean enabled = true;
    private Config data = null;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay();

        String prefix = getPrefix(event.getGuild().getIdLong());

        if (event.getMessage().getMentions().size() > 0) {
            if ((event.getMessage().getMentions().get(0)).getIdLong() == 443510227380207646L) {
                if (event.getMessage().getContentRaw().toLowerCase().contains("help")) {
                    String helpMsg = "```----- Mr. Worldwide Commands -----" +
                            "\n<> = Required Field - Prefix: " + prefix +
                            "\nUse the prefix before the command. eg." + prefix + "weather" +
                            "\n> weather <cityname,countrycode>" +
                            "\n> weather <countryname>" +
                            "\n> weather *(If 'setcity' has been used)*" +
                            "\n> weather <@Guaka25#4852>" +
                            "\n> setcity <cityname,countrycode>" +
                            "\n> changeclock" +
                            "\n> translate <originLanguage> <newLanguage> <message>" +
                            "\n> trs <originLanguage> <newLanguage> <message>" +
                            "\n> convert <amount> <originCurrency> <newCurrency>" +
                            "\n----------------------------------```" +
                            "\nSupport Server: https://discord.gg/CrZ7FZ7";
                    event.getChannel().sendMessage(helpMsg).queue();
                }
            }
        }

        if (!event.getMessage().getContentDisplay().startsWith(prefix)) return;

        this.getUsageData();

        String cmdStripped = message.substring(prefix.length());

        if (event.getMessage().getAuthor().getIdLong() == 257247527630274561L) {
            if (cmdStripped.equalsIgnoreCase("togglestate")) {
                enabled = !enabled;
                event.getChannel().sendMessage("TOGGLED STATE").queue();
            }

            if (cmdStripped.equalsIgnoreCase("testmode")) {
                inMaintenance = !inMaintenance;
                event.getChannel().sendMessage("SWAPPED MODES").queue();
            }
        }

        if (!enabled) {
            return;
        }

        long userId = event.getMessage().getAuthor().getIdLong();
        if (userId == 257247527630274561L || userId == 283305771624693771L || userId == 217896474602635264L) {
            Server server = new Server(MrWorldWide.apiToken);
            if (cmdStripped.toLowerCase().startsWith("startmoddedserver")) {
                event.getChannel().sendMessage("Server start command sent!").queue();
                server.carryStartServer();
            }

            if (cmdStripped.toLowerCase().startsWith("stopmoddedserver")) {
                event.getChannel().sendMessage("Server stop command sent!").queue();
                server.carryStopServer();
            }

            if (cmdStripped.toLowerCase().startsWith("restartmoddedserver")) {
                event.getChannel().sendMessage("Server restart command sent!").queue();
                server.carryRestartServer();
            }

            if (cmdStripped.toLowerCase().startsWith("sendcommandmodded ")) {
                String cmd = cmdStripped.substring(18);
                event.getChannel().sendMessage("Sent command ``" + cmd + "``!").queue();
                server.carrySendCommand(cmd);
            }
        }

        if (cmdStripped.toLowerCase().startsWith("weather")) {
            Weather weather = new Weather(MrWorldWide.weatherToken);
            data.getConfig().set("usage.weatherCommand", weatherCount + 1);
            weather.carryCommand(event, cmdStripped);
        }

        if (cmdStripped.toLowerCase().startsWith("countrycode")) {
            CountryCode countryCode = new CountryCode();
            countryCode.carryCommand(event, cmdStripped);
        }

        if (cmdStripped.toLowerCase().startsWith("setcity ")) {
            SetCity setCity = new SetCity();
            setCity.carryCommand(event, MrWorldWide.weatherToken, cmdStripped);
        }

        if (cmdStripped.toLowerCase().equalsIgnoreCase("changeclock")) {
            ChangeClock changeClock = new ChangeClock();
            changeClock.carryCommand(event);
        }

        if (cmdStripped.toLowerCase().startsWith("translate ")) {
            Translate translate = new Translate();
            data.getConfig().set("usage.translateCommand", translateCount + 1);
            translate.carryCommand(event, cmdStripped);
        }

        if (cmdStripped.toLowerCase().startsWith("trs ")) {
            Translate translate = new Translate();
            data.getConfig().set("usage.conversationCommand", conversationCount + 1);
            translate.carryConversationCommand(event, cmdStripped);
        }

        if (cmdStripped.toLowerCase().startsWith("convert ")) {
            Currency currency = new Currency();
            data.getConfig().set("usage.currencyCommand", currencyCount + 1);
            currency.carryCommand(event, MrWorldWide.currencyToken, cmdStripped);
        }

        if (cmdStripped.toLowerCase().startsWith("setprefix ")) {
            SetPrefix setPrefix = new SetPrefix();
            setPrefix.carryCommand(event, cmdStripped);
        }

        if (event.getMessage().getAuthor().getIdLong() == MrWorldWide.OwnerId) {
            if (cmdStripped.toLowerCase().equalsIgnoreCase("usage")) {
                String dataString = "translateCount=" + translateCount + ";weatherCount=" + weatherCount + ";currencyCount=" + currencyCount + ";conversationTranslateCount=" + conversationCount;
                event.getChannel().sendMessage(dataString).queue();
            }

            if (cmdStripped.toLowerCase().startsWith("rawweather ")) {
                Weather weather = new Weather(MrWorldWide.weatherToken);
                weather.carryRawCommand(event, cmdStripped);
            }
        }

        data.saveConfig();
    }

    private String getPrefix(long serverId) {
        Database database = CroniServer._db;
        String prefix = database.getPrefix(serverId);
        if (prefix == null) {
            String defaultPrefix = "mr!";
            database.setPrefix(serverId, defaultPrefix);
            return defaultPrefix;
        } else {
            return prefix;
        }
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
