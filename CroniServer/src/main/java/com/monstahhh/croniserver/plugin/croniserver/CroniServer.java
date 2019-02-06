package com.monstahhh.croniserver.plugin.croniserver;

import com.monstahhh.croniserver.plugin.croniserver.commands.AttackCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.DistanceCommand;
import com.monstahhh.croniserver.plugin.croniserver.commands.WheatCommand;
import com.monstahhh.croniserver.plugin.croniserver.events.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class CroniServer extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        WheatCommand b = new WheatCommand();
        this.getCommand("distance").setExecutor(new DistanceCommand());
        this.getCommand("pdistance").setExecutor((new DistanceCommand()));
        this.getCommand("attack").setExecutor(new AttackCommand());
        this.getCommand("lol").setExecutor(new DistanceCommand());
        this.getCommand("setwheat").setExecutor(b);
        this.getCommand("monstahwheat").setExecutor(b);
        this.getCommand("guakawheat").setExecutor(b);
    }
}
