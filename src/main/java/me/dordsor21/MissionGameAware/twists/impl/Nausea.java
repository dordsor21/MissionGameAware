package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.twists.EvilTwist;
import me.dordsor21.MissionGameAware.twists.Twist;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Nausea extends EvilTwist {
    @Override
    public void start() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.addPotionEffect(PotionEffectType.CONFUSION.createEffect(200, 2));
        }
        new Thread(() -> {
            try (final Twist twist = Nausea.this) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.BLOCK_WET_GRASS_BREAK, SoundCategory.MUSIC, 2.0f, 0.2f);
                }
                Thread.sleep(50L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.BLOCK_WET_GRASS_FALL, SoundCategory.MUSIC, 2.0f, 0.2f);
                }
                Thread.sleep(50L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.BLOCK_WET_GRASS_HIT, SoundCategory.MUSIC, 2.0f, 0.2f);
                }
                Thread.sleep(50L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.BLOCK_WET_GRASS_PLACE, SoundCategory.MUSIC, 2.0f, 0.2f);
                }
                Thread.sleep(50L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.BLOCK_WET_GRASS_STEP, SoundCategory.MUSIC, 2.0f, 0.2f);
                }
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
