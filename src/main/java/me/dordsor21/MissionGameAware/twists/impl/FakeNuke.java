package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.twists.MeanTwist;
import me.dordsor21.MissionGameAware.twists.Twist;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

import java.util.Random;

public class FakeNuke extends MeanTwist {
    @Override
    public void start() {
        new Thread(() -> {
            try (final Twist twist = FakeNuke.this) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.MUSIC, 1.0f, 1.0f);
                }
                Thread.sleep(51);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MUSIC, 1.0f, 1.0f);
                }
                for (int i = 0; i < 3; i++) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        for (int x = -10; x <= 10; x += 5) {
                            for (int z = -10; z <= 10; z += 5) {
                                TNTPrimed e = p.getWorld().spawn(
                                    new Location(p.getWorld(), p.getLocation().getBlockX() + x, p.getWorld().getHighestBlockYAt(p.getLocation()) + 64,
                                        p.getLocation().getBlockZ() + z), TNTPrimed.class);
                                e.setIsIncendiary(false);
                                e.setYield(0);
                            }
                        }
                    }
                    Thread.sleep(new Random().nextInt(5000) + 2500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
