package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.SoleTwist;
import me.dordsor21.MissionGameAware.twists.WeirdTwist;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Bee;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Bees extends WeirdTwist implements SoleTwist {

    private static final Vector up = new Vector(0, 1, 0);
    private static final Vector halves = new Vector(0.5, 0.5, 0.5);
    private Thread t = null;

    @Override
    public void start() {
        t = new Thread(() -> {
            try {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.ENTITY_BEE_LOOP, 1.0f, 0.2f);
                    p.playSound(p.getLocation(), Sound.ENTITY_BEE_LOOP_AGGRESSIVE, 1.0f, 0.2f);
                }
                Thread.sleep(50);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.ENTITY_BEE_LOOP, 1.0f, 1.0f);
                    p.playSound(p.getLocation(), Sound.ENTITY_BEE_LOOP_AGGRESSIVE, 1.0f, 1.0f);
                }
                Thread.sleep(50);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.ENTITY_BEE_LOOP, 1.0f, 2.0f);
                    p.playSound(p.getLocation(), Sound.ENTITY_BEE_LOOP_AGGRESSIVE, 1.0f, 2.0f);
                }
                Thread.sleep(850);
                final List<List<Bee>> beess = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    List<Bee> bees = new ArrayList<>();
                    Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                        for (int i = 0; i < 7; i++) {
                            Bee bee = (Bee) p.getWorld().spawnEntity(p.getEyeLocation(), EntityType.BEE);
                            bee.setVelocity(Vector.getRandom().subtract(halves).multiply(Math.random() * 2));
                            bee.setInvulnerable(true);
                            bees.add(bee);
                        }
                    });
                    beess.add(bees);
                }
                Thread.sleep(10000);
                final List<List<Bee>> babies = dupe(beess);

                Thread.sleep(3000);
                final List<List<Bee>> babies2 = dupe(babies);

                Thread.sleep(3000);
                final List<List<Bee>> babies3 = dupe(babies2);

                Thread.sleep(3000);
                final List<List<Bee>> babies4 = dupe(babies3);

                Thread.sleep(3000);
                final List<List<Bee>> babies5 = dupe(babies4);

                Thread.sleep(2000);
                final List<List<Bee>> babies6 = dupe(babies5);

                Thread.sleep(1000);
                final List<List<Bee>> babies7 = dupe(babies6);

                Thread.sleep(30000);
                for (List<Bee> bees : babies7) {
                    Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, () -> {
                        for (Bee bee : bees) {
                            bee.remove();
                        }
                    }, 1);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    private List<List<Bee>> dupe(List<List<Bee>> beess) {
        List<List<Bee>> babiess = new ArrayList<>();
        for (List<Bee> bees : beess) {
            final List<Bee> babies = new ArrayList<>(bees);
            Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, () -> {
                for (Bee bee : bees) {
                    if (!bee.isValid() || bee.isDead()) {
                        babies.remove(bee);
                        bee.remove();
                        continue;
                    }
                    bee.setBaby();
                    Bee baby = (Bee) bee.getWorld().spawnEntity(bee.getLocation().add(0, 0.5, 0), EntityType.BEE);
                    baby.setVelocity(up);
                    baby.setInvulnerable(true);
                    baby.setBaby();
                    babies.add(baby);
                }
            }, 1L);
            babiess.add(babies);
        }
        return babiess;
    }

    @Override
    public void cancel() {
        if (t != null) {
            t.stop();
        }
        super.cancel();
    }
}
