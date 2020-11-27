package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.SoleTwist;
import me.dordsor21.MissionGameAware.twists.Twist;
import me.dordsor21.MissionGameAware.twists.WeirdTwist;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Bee;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Bees extends WeirdTwist implements SoleTwist {

    private static final Vector up = new Vector(0, 1, 0);
    private static final Vector halves = new Vector(0.5, 0.5, 0.5);
    private static final Map<Player, List<Bee>> playerbees = new ConcurrentHashMap<>();
    private Thread t = null;

    @Override
    public void escapePlayer(Player p) {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            for (Bee bee : playerbees.get(p)) {
                bee.remove();
            }
        });
        playerbees.remove(p);
    }

    @Override
    public void start() {
        System.out.println(this.getClass().getSimpleName() + " twist started.");
        t = new Thread(() -> {
            try (Twist twist = Bees.this) {
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
                playerbees.clear();
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
                    playerbees.put(p, bees);
                }
                Thread.sleep(10000);
                dupe();

                Thread.sleep(3000);
                dupe();

                Thread.sleep(3000);
                dupe();

                Thread.sleep(3000);
                dupe();

                Thread.sleep(3000);
                dupe();

                Thread.sleep(2000);
                dupe();

                Thread.sleep(1000);
                dupe();

                Thread.sleep(30000);
                for (List<Bee> bees : playerbees.values()) {
                    Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, () -> {
                        for (Bee bee : bees) {
                            bee.remove();
                        }
                    }, 1);
                }
                playerbees.clear();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    private void dupe() {
        for (Map.Entry<Player, List<Bee>> entry : playerbees.entrySet()) {
            final List<Bee> babies = new ArrayList<>(entry.getValue());
            Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, () -> {
                for (Bee bee : entry.getValue()) {
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
            playerbees.replace(entry.getKey(), babies);
        }
    }

    @Override
    public void cancel() {
        playerbees.clear();
        if (t != null) {
            t.stop();
        }
        super.cancel();
    }
}
