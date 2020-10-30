package me.dordsor21.MissionGameAware.twists.WhatSimonSays;

import org.bukkit.entity.Player;

public interface WhatSimonSays {

    public String getMessage();

    public Thread doIt(boolean value);

    default void funBox(Player p) {

    }

}
