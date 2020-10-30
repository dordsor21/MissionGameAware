package me.dordsor21.MissionGameAware.twists.WhatSimonSays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LookUp implements WhatSimonSays {
    @Override
    public String getMessage() {
        return "Look up!";
    }

    @Override
    public Thread doIt(boolean value) {
        return new Thread(() -> {
            try {
                Thread.sleep(3000L);
                List<Player> fail = new ArrayList<>();
                List<Player> pass = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    System.out.println(p.getLocation().getPitch());
                    if (p.getLocation().getPitch() < -50 != value) {
                        fail.add(p);
                    } else {
                        pass.add(p);
                    }
                }
                funBox(fail);
                good(pass);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
