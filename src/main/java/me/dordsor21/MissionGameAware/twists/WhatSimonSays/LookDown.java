package me.dordsor21.MissionGameAware.twists.WhatSimonSays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LookDown implements WhatSimonSays {
    @Override
    public String getMessage() {
        return "Look down!";
    }

    @Override
    public Thread doIt(boolean isSimonSaying) {
        return new Thread(() -> {
            try {
                Thread.sleep(500L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getLocation().getPitch() < -50 != isSimonSaying) {
                        funBox(p);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
