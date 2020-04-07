package com.monstahhh.croniserver.plugin.uhc4;

import com.monstahhh.croniserver.plugin.croniserver.commands.WarpCommands;
import com.monstahhh.croniserver.plugin.uhc4.commands.GivePlayersUhcPlayerCommand;
import com.monstahhh.croniserver.plugin.uhc4.events.DeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class UHC {

    private JavaPlugin _plugin;

    public UHC(JavaPlugin plugin) {
        this._plugin = plugin;
    }

    public void enable() {
        _plugin.getServer().getPluginManager().registerEvents(new DeathEvent(), _plugin);
        Objects.requireNonNull(_plugin.getCommand("giveplayersuhcplayer")).setExecutor(new GivePlayersUhcPlayerCommand());
        System.out.println("[CroniServer] Enabled UHC");
    }

    public void disable() {
        System.out.println("[CroniServer] Disabled UHC");
    }
}
