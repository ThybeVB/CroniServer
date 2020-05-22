package com.monstahhh.croniserver.sqlite;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Error {

    public static void close(JavaPlugin plugin, Exception ex) {
        plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
    }
}
