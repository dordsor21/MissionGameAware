package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.MeanTwist;
import me.dordsor21.MissionGameAware.twists.Twist;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class Blindness extends MeanTwist {

    @Override
    public void start() {
        new Thread(() -> {
            try (final Twist twist = Blindness.this) {
                for (int i = 0; i < 1; i++) {
                    blindAll();
                    Thread.sleep(500L);
                    blindAll();
                    Thread.sleep(1000L);
                    blindAll();
                    Thread.sleep(300L);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void blindAll() throws InterruptedException {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(21, 10));
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 2f, 2.0f);
            }
        });
        Thread.sleep(100L);
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 2f, 2.0f);
        }
    }
}
