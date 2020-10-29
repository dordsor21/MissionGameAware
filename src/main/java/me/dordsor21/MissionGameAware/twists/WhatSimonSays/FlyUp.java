package me.dordsor21.MissionGameAware.twists.WhatSimonSays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FlyUp implements WhatSimonSays {
    @Override
    public String getMessage() {
        return "Fly up!";
    }

    @Override
    public Thread doIt(boolean isSimonSaying) {
        return new Thread(() -> {
            try {
                Thread.sleep(500L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getVelocity().getY() > 0 != isSimonSaying) {
                        funBox(p);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
