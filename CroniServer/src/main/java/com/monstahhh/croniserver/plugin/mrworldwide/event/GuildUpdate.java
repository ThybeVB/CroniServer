package com.monstahhh.croniserver.plugin.mrworldwide.event;

import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.discordbots.api.client.DiscordBotListAPI;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class GuildUpdate extends ListenerAdapter {

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        update(event.getJDA());
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        update(event.getJDA());
    }

    public void update(JDA _jda) {
        int userCount = 0;
        for (Guild g : _jda.getGuilds()) {
            userCount += g.getMemberCount();
        }

        JSONObject stats = new JSONObject();
        stats.put("guild_count", _jda.getGuilds().size());
        stats.put("user_count", userCount);
        MrWorldWide.JsonStats = stats;

        DiscordBotListAPI api = new DiscordBotListAPI.Builder()
                .token(MrWorldWide.dblToken)
                .botId(_jda.getSelfUser().getId())
                .build();

        api.setStats(_jda.getGuilds().size());

        //new HttpClient().requestToGateway(HttpMethod.POST, "http://localhost:3000/api/post_status", stats);
    }
}
