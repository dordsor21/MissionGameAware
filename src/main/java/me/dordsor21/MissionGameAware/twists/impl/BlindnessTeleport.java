package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.twists.MeanTwist;
import me.dordsor21.MissionGameAware.twists.Twist;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class BlindnessTeleport extends MeanTwist {

    @Override
    public void start() {
        new Thread(() -> {
            try (final Twist twist = BlindnessTeleport.this) {
                for (int i = 0; i < 6; i++) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(2, 10));
                    }
                    Thread.sleep(20L);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, SoundCategory.MUSIC, 2.0f, 2.0f);
                        double d = Math.random();
                        p.teleport(p.getLocation().add(p.getLocation().getDirection().rotateAroundY(Math.toRadians(d < 0.5 ? 90 : -90)).multiply(3)));
                    }
                    Thread.sleep(80L);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.removePotionEffect(PotionEffectType.BLINDNESS);
                    }
                    Thread.sleep(new Random().nextInt(2000) + 1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
