package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.MeanTwist;
import org.bukkit.Bukkit;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class LaserFocus extends MeanTwist {

    private final AtomicBoolean cancelled = new AtomicBoolean(false);

    @Override
    public void start() {
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 400L);
        final HashMap<UUID, BlockStareEntry> stareMap = new HashMap<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle("§4Laser Focus", "§fDon't stare at blocks too long...", 0, 70, 20);
            RayTraceResult r = p.rayTraceBlocks(100d, FluidCollisionMode.SOURCE_ONLY);
            final int x, y, z;
            if (r == null || r.getHitBlock() == null) {
                x = p.getLocation().getBlockX();
                y = p.getLocation().getBlockY();
                z = p.getLocation().getBlockZ();
            } else {
                x = r.getHitBlock().getX();
                y = r.getHitBlock().getY();
                z = r.getHitBlock().getZ();
            }
            stareMap.put(p.getUniqueId(), new BlockStareEntry(x, y, z));
        }
        new Thread(() -> {
            while (!cancelled.get()) {
                long start = System.currentTimeMillis();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    RayTraceResult r = p.rayTraceBlocks(100d, FluidCollisionMode.SOURCE_ONLY);
                    if (r == null || r.getHitBlock() == null || r.getHitBlock().isEmpty()) {
                        continue;
                    }
                    BlockStareEntry e = stareMap.get(p.getUniqueId());
                    e.set(r.getHitBlock().getX(), r.getHitBlock().getY(), r.getHitBlock().getZ());
                    if (e.testTimeStare()) {
                        r.getHitBlock().setType(Material.AIR);
                    }
                }
                try {
                    Thread.sleep(100 - (System.currentTimeMillis() - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void complete() {
        cancelled.set(true);
        super.complete();
    }

    private static final class BlockStareEntry {
        private long firstStamp;
        private int x, y, z;

        private BlockStareEntry(int bx, int by, int bz) {
            x = bx;
            y = by;
            z = bz;
        }

        private void set(int bx, int by, int bz) {
            if (x != bx || y != by || z != bz) {
                x = bx;
                y = by;
                z = bz;
                firstStamp = System.currentTimeMillis();
            }
        }

        private boolean testTimeStare() {
            long cur = System.currentTimeMillis();
            boolean longer = cur - firstStamp > 1000;
            if (longer) {
                firstStamp = cur;
            }
            return longer;
        }

    }

}
