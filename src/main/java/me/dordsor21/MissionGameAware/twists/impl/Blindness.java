package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.MeanTwist;
import me.dordsor21.MissionGameAware.twists.Twist;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Blindness extends MeanTwist {

    private static final List<Player> escaped = new ArrayList<>();
    private Thread t = null;

    @Override
    public void escapePlayer(Player p) {
        escaped.add(p);
        p.removePotionEffect(PotionEffectType.BLINDNESS);
    }

    @Override
    public void start() {
        escaped.clear();
        t = new Thread(() -> {
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

    private void blindAll() throws InterruptedException {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (escaped.contains(p)) {
                    continue;
                }
                p.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(21, 10));
                p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 2f, 2.0f);
            }
        });
        Thread.sleep(100L);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (escaped.contains(p)) {
                continue;
            }
            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 2f, 2.0f);
        }
    }
}
