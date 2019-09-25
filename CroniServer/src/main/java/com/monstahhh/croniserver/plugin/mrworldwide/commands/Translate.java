package com.monstahhh.croniserver.plugin.mrworldwide.commands;

import com.monstahhh.croniserver.http.HttpClient;
import com.monstahhh.croniserver.http.HttpMethod;
import com.monstahhh.croniserver.http.HttpResponse;
import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Translate {

    private final String GOOGLE_URL_API = "https://translate.googleapis.com/translate_a/";
    private final String GOOGLE_PARAMS = "single?client=gtx&sl=%s&tl=%s-CN&ie=UTF-8&oe=UTF-8&dt=t&dt=rm&q=%s";

    public void carryConversationCommand(GuildMessageReceivedEvent event) {
        try {
            String[] results = doTranslate(event, true);
            if (results != null) {
                event.getChannel().sendMessage(event.getAuthor().getName() + " (" + results[2].toUpperCase() + "): " + results[0]).queue();
                event.getMessage().delete().queue();
            }
        } catch (Exception e) {
            event.getChannel().sendMessage(e.getMessage()).queue();
        }
    }

    public void carryCommand(GuildMessageReceivedEvent event) {
        String[] results = doTranslate(event, false);

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Google Translate");
        if (results != null) {
            eb.addField(results[1] + " -> " + results[2], results[0], false);
        } else {
            eb.addField("Google Translate", "A null has occured in the Translate Command. Also guaka is gay", false);
        }
        eb.setColor(Color.PINK);

        event.getChannel().sendMessage(eb.build()).queue();
    }

    private String[] doTranslate(GuildMessageReceivedEvent event, boolean conversationVersion) {
        String origin, destination, msg;

        String[] args = event.getMessage().getContentRaw().split(" ");
        try {
            origin = args[1];
            destination = args[2];
            if (origin.equalsIgnoreCase(destination)) {
                if (conversationVersion) {
                    event.getChannel().sendMessage("You failed to provide one of the arguments\nExample: *'trs id en kontol'*").queue();
                } else {
                    event.getChannel().sendMessage("You failed to provide one of the arguments\nExample: *'translate id en kontol'*").queue();
                }
                return null;
            } else {
                if (origin.length() != 2) {
                    if (conversationVersion) {
                        event.getChannel().sendMessage("You failed to provide one of the arguments\nExample: *'trs id en kontol'*").queue();
                    } else {
                        event.getChannel().sendMessage("You failed to provide one of the arguments\nExample: *'translate id en kontol'*").queue();
                    }
                    return null;
                } else {
                    if (destination.length() > 2) {
                        destination = "en";
                        if (conversationVersion) {
                            msg = event.getMessage().getContentRaw().substring(6);
                        } else {
                            msg = event.getMessage().getContentRaw().substring(13);
                        }
                    } else {
                        if (destination.length() != 2) {
                            destination = "en";
                        }
                        if (conversationVersion) {
                            msg = event.getMessage().getContentRaw().substring(9);
                        } else {
                            msg = event.getMessage().getContentRaw().substring(16);
                        }
                    }
                }

                msg = msg.replaceAll("\n", " ");
                msg = msg.replaceAll("\\?", ".");
                msg = msg.replaceAll("!", ".");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            if (conversationVersion) {
                event.getChannel().sendMessage("You failed to provide one of the arguments\nExample: *'trs id en kontol'*").queue();
            } else {
                event.getChannel().sendMessage("You failed to provide one of the arguments\nExample: *'translate id en kontol'*").queue();
            }
            return null;
        }
        String[] strArray = new String[3];
        strArray[0] = getTranslation(origin, destination, msg);
        strArray[1] = origin;
        strArray[2] = destination;

        return strArray;
    }

    private String getTranslation(String origin, String destination, String msg) {
        String result = null;

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

        return new HttpClient().request(HttpMethod.GET, (GOOGLE_URL_API + params));
    }

    private String castResult(String result) {
        Pattern pat = Pattern.compile("\"(.*?)\"");
        List<String> allMatches = new ArrayList<>();

        Matcher matcher = pat.matcher(result);
        while (matcher.find()) {
            allMatches.add(matcher.group().replace("\"", ""));
        }

        return allMatches.get(0);
    }
}
