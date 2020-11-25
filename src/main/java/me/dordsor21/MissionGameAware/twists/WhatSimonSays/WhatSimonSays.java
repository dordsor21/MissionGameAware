package me.dordsor21.MissionGameAware.twists.WhatSimonSays;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.impl.SimonSaysFunBoxTime;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface WhatSimonSays {

    String getMessage();

    Thread doIt(boolean value);

    default void funBox(List<Player> players) {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            for (Player p : players) {
                if (SimonSaysFunBoxTime.escaped.contains(p)) {
                    continue;
                }
                if (!p.hasMetadata("wssFailed")) {
                    p.setMetadata("wssFailed", new FixedMetadataValue(MissionGameAware.plugin, 1));
                    p.sendTitle("Failed! 1/3 in a row...", "", 0, 40, 10);
                } else {
                    int failed = p.getMetadata("wssFailed").get(0).asInt();
                    if (failed == 2) {
                        p.removeMetadata("wssFailed", MissionGameAware.plugin);
                        p.setMetadata("wssFailed", new FixedMetadataValue(MissionGameAware.plugin, 2));
                        p.sendTitle("Failed! 2/3 in a row...", "", 0, 40, 10);
                        return;
                    }
                    p.removeMetadata("wssFailed", MissionGameAware.plugin);
                    p.sendTitle("You failed 3 in a row! Meet the funbox!", "", 0, 40, 10);
                    final Location back = p.getLocation().clone();
                    Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, () -> {
                        p.teleport(SimonSaysFunBoxTime.funBoxLoc);
                    }, 10L);
                    Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, () -> {
                        p.teleport(back);
                    }, 10 * 20L);
                }
            }
        });
    }

    default void good(List<Player> players) {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            for (Player p : players) {
                if (SimonSaysFunBoxTime.escaped.contains(p)) {
                    continue;
                }
                p.sendTitle("Passed!", "", 0, 40, 10);
            }
        });
    }

    default void checkListAgainstShouldContain(LinkedList<Player> playersWhoDid, boolean value) {
        List<Player> fail = new ArrayList<>();
        List<Player> pass = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (SimonSaysFunBoxTime.escaped.contains(p)) {
                continue;
            }
            if (p.hasMetadata("wssFailed")) {
                p.removeMetadata("wssFailed", MissionGameAware.plugin);
            }
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
