package com.monstahhh.croniserver.plugin.mrworldwide.event;

import com.jafregle.Jafregle.Jafregle;
import com.jafregle.Jafregle.Language;
import com.jafregle.Jafregle.Translator;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageReceivedEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (message.toLowerCase().startsWith("translate")) {
            carryCommand(event);
        }
    }

    private void carryCommand (GuildMessageReceivedEvent event) {
        try {
            Jafregle jafregle = new Jafregle("nl", "en");
            String result = jafregle.translate("dit is een test");
            event.getChannel().sendMessage(result).queue();
        } catch (Exception e) {

        }
    }
}
