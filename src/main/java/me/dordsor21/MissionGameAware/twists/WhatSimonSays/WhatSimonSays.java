package me.dordsor21.MissionGameAware.twists.WhatSimonSays;

import org.bukkit.entity.Player;

import java.util.concurrent.Future;

public interface WhatSimonSays {

    public String getMessage();

    public Thread doIt(boolean isSimonSaying);

    default void funBox(Player p) {

    }

}
