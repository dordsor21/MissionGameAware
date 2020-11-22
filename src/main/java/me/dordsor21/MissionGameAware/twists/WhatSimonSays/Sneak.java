package me.dordsor21.MissionGameAware.twists.WhatSimonSays;

import me.dordsor21.MissionGameAware.twists.impl.SimonSaysFunBoxTime;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Sneak implements WhatSimonSays {

    @Override
    public String getMessage() {
        return "Sneak!";
    }

    @Override
    public Thread doIt(boolean value) {
        return new Thread(() -> {
            try {
                ArrayList<Player> pass = new ArrayList<>();
                ArrayList<Player> fail = new ArrayList<>();
                Thread.sleep(2500L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (SimonSaysFunBoxTime.escaped.contains(p)) {
                        continue;
                    }
                    if (p.isSneaking() == value) {
                        pass.add(p);
                    } else {
                        fail.add(p);
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
