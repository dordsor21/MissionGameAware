package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.MeanTwist;
import me.dordsor21.MissionGameAware.twists.Twist;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlindnessTeleport extends MeanTwist {

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
            try (final Twist twist = BlindnessTeleport.this) {
                for (int i = 0; i < 6; i++) {
                    Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (escaped.contains(p)) {
                                continue;
                            }
                            p.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(30, 10));
                        }
                    });
                    Thread.sleep(100L);
                    Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (escaped.contains(p)) {
                                continue;
                            }
                            p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 2.0f, 2.0f);
                            double d = Math.random();
                            p.teleport(p.getLocation().add(new Vector((d - 0.5) * 10, 0, (d - 0.5) * 10)));
                        }
                    });
                    Thread.sleep(new Random().nextInt(2000) + 1000);
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
