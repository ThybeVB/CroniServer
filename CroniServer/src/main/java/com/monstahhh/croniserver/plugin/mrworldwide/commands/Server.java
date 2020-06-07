package com.monstahhh.croniserver.plugin.mrworldwide.commands;

import com.monstahhh.croniserver.http.HttpMethod;
import java.io.IOException;

public class Server {

    private String token;
    private final String baseUrl = "http://croniserver.epizy.com/";

    public Server(String _token) {
        token = _token;
    }

    public void carryStartServer() {
        try {
            String params = "?token=%s";
            String formattedSend = String.format(params, token);
            com.monstahhh.croniserver.http.HttpResponse result = new com.monstahhh.croniserver.http.HttpClient().request(HttpMethod.GET, (baseUrl + "startModdedServer.php" + formattedSend));
            String res = result.asString();
            System.out.println(res);
        } catch (IOException exception) {
            System.out.println(exception.toString());
        }
    }

    public void carryStopServer() {
        try {
            String params = "?token=%s";
            String formattedSend = String.format(params, token);
            com.monstahhh.croniserver.http.HttpResponse result = new com.monstahhh.croniserver.http.HttpClient().request(HttpMethod.GET, (baseUrl + "stopServerModded.php" + formattedSend));
        } catch (IOException exception) {
            System.out.println(exception.toString());
        }
    }

    public void carryRestartServer() {
        try {
            String params = "?token=%s";
            String formattedSend = String.format(params, token);
            com.monstahhh.croniserver.http.HttpResponse result = new com.monstahhh.croniserver.http.HttpClient().request(HttpMethod.GET, (baseUrl + "restartServerModded.php" + formattedSend));
        } catch (IOException exception) {
            System.out.println(exception.toString());
        }
    }

    public void carrySendCommand(String command) {
        try {
            String newUrl = command.replaceAll(" ","%20");
            String params = "?token=%s&command=%s";
            String formattedSend = String.format(params, token, newUrl);
            com.monstahhh.croniserver.http.HttpResponse result = new com.monstahhh.croniserver.http.HttpClient().request(HttpMethod.GET, (baseUrl + "sendCommandModded.php" + formattedSend));
        } catch (IOException exception) {
            System.out.println(exception.toString());
        }
    }
}
