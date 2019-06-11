package com.monstahhh.croniserver.plugin.damageapi;

import org.bukkit.entity.Player;

import java.util.Timer;

public class TimedPlayer {
    private Player player;
    private Timer pTimer;

    private TimedPlayer (Player _player, Timer _timer) {
        player = _player;
        pTimer = _timer;
    }
}
