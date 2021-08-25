package com.monstahhh.croniserver.plugin.mrworldwide.commands.weather;

import com.monstahhh.croniserver.configapi.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ChangeClock {

    private final Config data = new Config("plugins/MrWorldWide", "users.yml");

    public void carryCommand(GuildMessageReceivedEvent event) {
        long userId = event.getAuthor().getIdLong();

        Time currentTime = getUserTimeSetting(userId);
        Time newTime = getNewTime(currentTime);

        setUserTimeSetting(userId, newTime);
        event.getChannel().sendMessage("Time has been changed from `" + currentTime + "` to `" + newTime + "` for " + event.getAuthor().getAsMention()).queue();
    }

    private void setUserTimeSetting(long userId, Time time) {
        data.getConfig().set("times.users." + userId, time.toString());
        data.saveConfig();
    }

    Time getUserTimeSetting(long userId) {
        String timeObj = data.getConfig().getString("times.users." + userId);
        if (timeObj == null) {
            return Time.TWENTYFOURHOUR;
        }

        return Time.valueOf(timeObj);
    }

    private Time getNewTime(Time currentTime) {

        return switch (currentTime) {
            case TWELVEHOUR -> Time.TWENTYFOURHOUR;
            case TWENTYFOURHOUR -> Time.TWELVEHOUR;
        };
    }

    public enum Time {
        TWENTYFOURHOUR,
        TWELVEHOUR
    }
}
