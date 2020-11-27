package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.MeanTwist;
import me.dordsor21.MissionGameAware.twists.Twist;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FakeNuke extends MeanTwist {

    private static final List<Player> escaped = new ArrayList<>();
    private Thread t = null;

    @Override
    public void escapePlayer(Player p) {
        escaped.add(p);
    }

    @Override
    public void start() {
        System.out.println(this.getClass().getSimpleName() + " twist started.");
        escaped.clear();
        t = new Thread(() -> {
            try (final Twist twist = FakeNuke.this) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1.0f, 0.5f);
                }
                Thread.sleep(51);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 0.5f);
                }
                for (int i = 0; i < 3; i++) {
                    Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (escaped.contains(p)) {
                                continue;
                            }
                            for (int x = -30; x <= 30; x += 15) {
                                for (int z = -30; z <= 30; z += 15) {
                                    TNTPrimed e = (TNTPrimed) p.getWorld().spawnEntity(
                                        new Location(p.getWorld(), p.getLocation().getBlockX() + x,
                                            Math.min(p.getLocation().getY() + 32, 255), p.getLocation().getBlockZ() + z),
                                        EntityType.PRIMED_TNT);
                                    e.setIsIncendiary(false);
                                    e.setYield(0);
                                }
                            }
                        }
                    });
                    Thread.sleep(new Random().nextInt(5000) + 2500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    @Override
    public void cancel() {
        escaped.clear();
        if (t != null) {
            t.stop();
        }
        super.cancel();
    }
}
