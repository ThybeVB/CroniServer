package com.monstahhh.croniserver.configapi;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    public File file;
    private FileConfiguration fileConfig;

    /**
     * Creates a new config at the path, with the fileName, and uses the Plugin
     */
    public Config(String path, String fileName) {
        if (!fileName.contains(".yml")) {
            fileName = fileName + ".yml";
        }
        file = new File(path, fileName);
        fileConfig = YamlConfiguration.loadConfiguration(file);

        if (!file.exists()) {
            fileConfig.options().copyDefaults(true);
            try {
                fileConfig.save(file);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Get the Configuration section
     */
    public FileConfiguration getConfig() {
        return fileConfig;
    }

    /**
     * Save the config
     */
    public void saveConfig() {
        try {
            fileConfig.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}

