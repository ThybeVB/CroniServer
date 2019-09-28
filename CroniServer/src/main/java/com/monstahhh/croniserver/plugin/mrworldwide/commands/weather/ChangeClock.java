package com.monstahhh.croniserver.plugin.mrworldwide.commands.weather;

import com.monstahhh.croniserver.configapi.Config;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ChangeClock {

    public enum Time {
        TWENTYFOURHOUR,
        TWELVEHOUR
    }

    public void carryCommand(GuildMessageReceivedEvent event) {
        long userId = event.getAuthor().getIdLong();

        Time currentTime = this.getUserTimeSetting(userId);
        Time newTime = getNewTime(currentTime);

        setUserTimeSetting(userId, newTime);
        event.getChannel().sendMessage("Time has been changed from `"  + currentTime + "` to `" + newTime + "` for " + event.getAuthor().getAsMention()).queue();
    }

    private void setUserTimeSetting(long userId, Time time) {
        Config data = new Config("plugins/MrWorldWide", "users.yml", MrWorldWide._plugin);
        data.getConfig().set("times.users." + userId, time.toString());
        data.saveConfig();
    }

    Time getUserTimeSetting(long userId) {
        Config data = new Config("plugins/MrWorldWide", "users.yml", MrWorldWide._plugin);
        String timeObj = data.getConfig().getString("times.users." + userId);
        if (timeObj == null) {
            return Time.TWENTYFOURHOUR;
        }

        return Time.valueOf(timeObj);
    }

    private Time getNewTime (Time currentTime) {
        Time newTime;

        switch (currentTime) {
            case TWELVEHOUR:
                newTime = Time.TWENTYFOURHOUR;
                break;
            case TWENTYFOURHOUR:
                newTime = Time.TWELVEHOUR;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currentTime);
        }

        return newTime;
    }
}
