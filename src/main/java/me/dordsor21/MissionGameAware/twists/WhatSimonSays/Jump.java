package me.dordsor21.MissionGameAware.twists.WhatSimonSays;

import me.dordsor21.MissionGameAware.twists.impl.SimonSaysFunBoxTime;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public class Jump implements WhatSimonSays {

    @Override
    public String getMessage() {
        return "Jump!";
    }

    @Override
    public Thread doIt(boolean value) {
        return new Thread(() -> {
            try {
                LinkedList<Player> pass = new LinkedList<>();
                Thread.sleep(500L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (SimonSaysFunBoxTime.escaped.contains(p)) {
                        continue;
                    }
                    if (!p.isOnGround()) {
                        pass.add(p);
                    }
                }
                Thread.sleep(300L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (SimonSaysFunBoxTime.escaped.contains(p)) {
                        continue;
                    }
                    if (!p.isOnGround()) {
                        pass.add(p);
                    }
                }
                Thread.sleep(200L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (SimonSaysFunBoxTime.escaped.contains(p)) {
                        continue;
                    }
                    if (!p.isOnGround()) {
                        pass.add(p);
                    }
                }
                Thread.sleep(400L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (SimonSaysFunBoxTime.escaped.contains(p)) {
                        continue;
                    }
                    if (!p.isOnGround()) {
                        pass.add(p);
                    }
                }
                Thread.sleep(200L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (SimonSaysFunBoxTime.escaped.contains(p)) {
                        continue;
                    }
                    if (!p.isOnGround()) {
                        pass.add(p);
                    }
                }
                checkListAgainstShouldContain(pass, value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
