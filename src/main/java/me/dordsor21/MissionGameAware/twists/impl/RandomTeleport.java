package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.twists.MeanTwist;
import me.dordsor21.MissionGameAware.twists.Twist;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Random;

public class RandomTeleport extends MeanTwist {

    @Override
    public void start() {
        new Thread(() -> {
            try (final Twist twist = RandomTeleport.this) {
                for (int i = 0; i < 10; i++) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        double d = Math.random();
                        p.teleport(p.getLocation().add(p.getLocation().getDirection().rotateAroundY(Math.toRadians(d * 360)).multiply(2 + d * 2)));
                    }
                    Thread.sleep(new Random().nextInt(1000) + 700);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
