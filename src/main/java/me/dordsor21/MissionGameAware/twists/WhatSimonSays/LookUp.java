package me.dordsor21.MissionGameAware.twists.WhatSimonSays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LookUp implements WhatSimonSays {
    @Override
    public String getMessage() {
        return "Look up!";
    }

    @Override
    public Thread doIt(boolean value) {
        return new Thread(() -> {
            try {
                Thread.sleep(500L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getLocation().getPitch() > 50 != value) {
                        funBox(p);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
