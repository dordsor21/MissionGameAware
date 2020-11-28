package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.SoleTwist;
import me.dordsor21.MissionGameAware.twists.WeirdTwist;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftSheep;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Sheep extends WeirdTwist implements SoleTwist {

    private static final Vector up = new Vector(0, 1, 0);
    private static final Vector halves = new Vector(0.5, 0.5, 0.5);
    private static final DyeColor[] colors =
        new DyeColor[] {DyeColor.BLACK, DyeColor.BLUE, DyeColor.BROWN, DyeColor.CYAN, DyeColor.GRAY, DyeColor.GREEN,
            DyeColor.LIGHT_BLUE, DyeColor.LIGHT_GRAY, DyeColor.LIME, DyeColor.MAGENTA, DyeColor.ORANGE, DyeColor.PINK,
            DyeColor.PURPLE, DyeColor.RED, DyeColor.WHITE, DyeColor.YELLOW};
    private static final IChatBaseComponent jeb = IChatBaseComponent.ChatSerializer.b("jeb_");
    private static final Map<Player, List<org.bukkit.entity.Sheep>> playersheep = new ConcurrentHashMap<>();
    private Thread t = null;

    @Override
    public void escapePlayer(Player p) {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            for (org.bukkit.entity.Sheep sheep : playersheep.get(p)) {
                sheep.remove();
            }
        });
        playersheep.remove(p);
    }

    @Override
    public void start() {
        System.out.println(this.getClass().getSimpleName() + " twist started.");
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "&4Twist&f: " + this.getClass().getSimpleName()),
                    0, 70, 20);
            }
        });
        t = new Thread(() -> {
            try {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.ENTITY_SHEEP_AMBIENT, 1.0f, 0.2f);
                }
                Thread.sleep(50);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.ENTITY_SHEEP_AMBIENT, 1.0f, 1.0f);
                }
                Thread.sleep(50);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.ENTITY_SHEEP_AMBIENT, 1.0f, 2.0f);
                }
                Thread.sleep(850);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    List<org.bukkit.entity.Sheep> sheeps = new ArrayList<>();
                    Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                        for (int i = 0; i < 7; i++) {
                            org.bukkit.entity.Sheep sheep =
                                (org.bukkit.entity.Sheep) p.getWorld().spawnEntity(p.getEyeLocation(), EntityType.SHEEP);
                            sheep.setVelocity(Vector.getRandom().subtract(halves).multiply(Math.random() * 2));
                            sheep.setInvulnerable(true);
                            if (Math.random() < 0.5) {
                                Entity e = ((CraftSheep) sheep).getHandle();
                                e.setCustomName(jeb);
                            } else {
                                int c = (int) Math.floor(Math.random() * 16);
                                sheep.setColor(colors[c]);
                            }
                            sheeps.add(sheep);
                        }
                    });
                    playersheep.put(p, sheeps);
                }
                Thread.sleep(10000);
                dupe();

                Thread.sleep(10000);
                dupe();

                Thread.sleep(8000);
                dupe();

                Thread.sleep(6000);
                dupe();

                Thread.sleep(4000);
                dupe();

                Thread.sleep(2000);
                dupe();

                Thread.sleep(30000);
                for (List<org.bukkit.entity.Sheep> sheeps : playersheep.values()) {
                    Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, () -> {
                        for (org.bukkit.entity.Sheep sheep : sheeps) {
                            sheep.remove();
                        }
                    }, 1);
                }
                this.complete();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    private void dupe() {
        for (Map.Entry<Player, List<org.bukkit.entity.Sheep>> entry : playersheep.entrySet()) {
            final List<org.bukkit.entity.Sheep> babies = new ArrayList<>(entry.getValue());
            Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, () -> {
                for (org.bukkit.entity.Sheep sheep : entry.getValue()) {
                    if (!sheep.isValid() || sheep.isDead()) {
                        babies.remove(sheep);
                        sheep.remove();
                        continue;
                    }
                    sheep.setBaby();
                    org.bukkit.entity.Sheep baby = (org.bukkit.entity.Sheep) sheep.getWorld()
                        .spawnEntity(sheep.getLocation().add(0, 1, 0), EntityType.SHEEP);
                    baby.setVelocity(up);
                    baby.setInvulnerable(true);
                    baby.setBaby();
                    if (Math.random() < 0.5) {
                        Entity e = ((CraftSheep) baby).getHandle();
                        e.setCustomName(jeb);
                    } else {
                        int c = (int) Math.floor(Math.random() * 16);
                        baby.setColor(colors[c]);
                    }
                    babies.add(baby);
                }
            }, 1L);
            playersheep.put(entry.getKey(), babies);
        }
    }

    @Override
    public void cancel() {
        if (t != null) {
            t.stop();
        }
        super.cancel();
    }
}
