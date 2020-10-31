package com.monstahhh.croniserver.plugin.mrworldwide.event;

import com.monstahhh.croniserver.http.HttpClient;
import com.monstahhh.croniserver.http.HttpMethod;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

public class GuildUpdate extends ListenerAdapter {

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        update(event.getJDA());
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        update(event.getJDA());
    }

    private void update (JDA _jda) {
        int userCount = 0;
        for (Guild g : _jda.getGuilds()) {
            userCount += g.getMemberCount();
        }

        JSONObject stats = new JSONObject();
        stats.put("guild_count", _jda.getGuilds().size());
        stats.put("user_count", userCount);
        MrWorldWide.JsonStats = stats;

        try {
            new HttpClient().requestToGateway(HttpMethod.POST, "http://localhost:3000/api/post_status", stats);
        } catch (IOException exception) {
            System.out.println(exception.toString());
        }
    }
}
