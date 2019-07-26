package com.monstahhh.croniserver.plugin.mrworldwide.commands;

import com.monstahhh.croniserver.http.HttpClient;
import com.monstahhh.croniserver.http.HttpMethod;
import com.monstahhh.croniserver.http.HttpResponse;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translate {

    private final String GOOGLE_URL_API = "https://translate.googleapis.com/translate_a/";
    private final String GOOGLE_PARAMS = "single?client=gtx&sl=%s&tl=%s-CN&ie=UTF-8&oe=UTF-8&dt=t&dt=rm&q=%s";
    private final String PATTERN = "\"(.*?)\"";

    public void carryCommand(GuildMessageReceivedEvent event) {
        String origin, destination, msg;

        String[] args = event.getMessage().getContentRaw().split(" ");
        try {
            origin = args[1];
            destination = args[2];
            if (origin.equalsIgnoreCase(destination)) {
                event.getChannel().sendMessage("You have to translate one language to the other.\nExample: *'translate id en kontol'*").queue();
                return;
            } else {
                if (destination.length() > 2) {
                    destination = "en";
                    msg = event.getMessage().getContentRaw().substring(13);
                } else {
                    msg = event.getMessage().getContentRaw().substring(16);
                }

                msg = msg.replaceAll("\n", " ");
                msg = msg.replaceAll("\\?", ".");
                msg = msg.replaceAll("!", ".");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            event.getChannel().sendMessage("You failed to provide one of the arguments\nExample: *'translate id en kontol'*").queue();
            return;
        }

        String result = getTranslation(origin, destination, msg);

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Google Translate");
        eb.addField(origin + " -> " + destination, result, false);
        eb.setColor(Color.PINK);

        event.getChannel().sendMessage(eb.build()).queue();
    }

    private String getTranslation(String origin, String destination, String msg) {
        String result = "null";

        try {
            result = requestTranslation(msg, origin, destination);
        } catch (IOException e) {
            MrWorldWide.debugLog(e.getMessage());
        }

        return result;
    }

    private String requestTranslation(String textToTranslate, String from, String to) throws IOException {

        if (textToTranslate.contains(". ")) {
            String[] sentences = textToTranslate.split("\\.");

            StringBuilder sb = new StringBuilder();
            for (String sentence : sentences) {
                String singleResult = castResult(doTranslateFor(sentence, from, to).asString());
                sb.append(singleResult);
                sb.append(". ");
            }

            return sb.toString();
        } else {
            return castResult(doTranslateFor(textToTranslate, from, to).asString());
        }
    }

    private HttpResponse doTranslateFor(String textToTranslate, String from, String to) throws IOException {
        String encodedText = java.net.URLEncoder.encode(textToTranslate, "UTF-8");
        String params = String.format(GOOGLE_PARAMS, from, to, encodedText);

        return new HttpClient().request(HttpMethod.GET, new StringBuilder(GOOGLE_URL_API).append(params).toString());
    }

    private String castResult(String result) {
        Pattern pat = Pattern.compile(PATTERN);
        List<String> allMatches = new ArrayList<String>();

        Matcher matcher = pat.matcher(result);
        while (matcher.find()) {
            allMatches.add(matcher.group().replace("\"", ""));
        }

        return allMatches.get(0);
    }
}
