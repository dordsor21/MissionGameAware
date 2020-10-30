package me.dordsor21.MissionGameAware.twists.WhatSimonSays;

import me.dordsor21.MissionGameAware.MissionGameAware;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface WhatSimonSays {

    String getMessage();

    Thread doIt(boolean value);

    default void funBox(List<Player> players) {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            for (Player p : players) {
                p.sendTitle("Failed!", "", 0, 40, 10);
            }
        });
    }

    default void good(List<Player> players) {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            for (Player p : players) {
                p.sendTitle("Passed!", "", 0, 40, 10);
            }
        });
    }

    default void checkListAgainstShouldContain(LinkedList<Player> playersWhoDid, boolean value) {
        List<Player> fail = new ArrayList<>();
        List<Player> pass = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (playersWhoDid.contains(p) != value) {
                fail.add(p);
            } else {
                pass.add(p);
            }
        }
        funBox(fail);
        good(pass);
    }

}
