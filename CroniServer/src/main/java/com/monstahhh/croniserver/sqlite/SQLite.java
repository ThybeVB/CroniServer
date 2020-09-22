package com.monstahhh.croniserver.sqlite;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class SQLite extends Database {
    public String SQLiteCreateTokensTable = "CREATE TABLE IF NOT EXISTS guild_prefix (" +
            "`guild_id` int(8) NOT NULL," +
            "`prefix` varchar(32) NOT NULL," +
            "PRIMARY KEY (`guild_id`)" +
            ");";

    private final String dbName;

    public SQLite(JavaPlugin instance) {
        super(instance);
        dbName = plugin.getConfig().getString("SQLite.Filename", "guild_prefix");
    }

    public Connection getSQLConnection() {
        File dataFolder = new File("plugins/MrWorldWide/", dbName + ".db");
        if (!dataFolder.exists()) {
            try {
                boolean success = dataFolder.createNewFile();
                if (!success) {
                    plugin.getLogger().log(Level.SEVERE, "Could not create data folder!");
                }

            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "File write error: " + dbName + ".db");
            }
        }
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
            return connection;
        } catch (SQLException ex) {
            plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
        } catch (ClassNotFoundException ex) {
            plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
        }
        return null;
    }

    public void load() {
        connection = getSQLConnection();
        try {
            Statement s = connection.createStatement();
            s.executeUpdate(SQLiteCreateTokensTable);
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialize();
    }
}

