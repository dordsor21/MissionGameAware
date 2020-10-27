package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.MeanTwist;
import me.dordsor21.MissionGameAware.twists.Twist;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public class RandomTeleport extends MeanTwist {

    private Thread t = null;

    @Override
    public void start() {
        t = new Thread(() -> {
            try (final Twist twist = RandomTeleport.this) {
                for (int i = 0; i < 10; i++) {
                    Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            double d = Math.random();
                            p.teleport(p.getLocation().add(new Vector((d - 0.5) * 30, 0, (d - 0.5) * 30)));
                        }
                    });
                    Thread.sleep(new Random().nextInt(1000) + 700);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    @Override
    public void cancel() {
        if (t != null) {
            t.stop();
        }
        super.cancel();
    }
}
