package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.MeanTwist;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class LaserFocus extends MeanTwist {

    private static final List<Player> escaped = new ArrayList<>();

    private final AtomicBoolean cancelled = new AtomicBoolean(false);
    private Thread t = null;

    @Override
    public void start() {
        System.out.println(this.getClass().getSimpleName() + " twist started.");
        escaped.clear();
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 400L);
        final HashMap<UUID, BlockStareEntry> stareMap = new HashMap<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (escaped.contains(p)) {
                continue;
            }
            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&4Laser Focus"),
                ChatColor.translateAlternateColorCodes('&', "&fDon't stare at blocks too long..."), 0, 70, 20);
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
        t = new Thread(() -> {
            while (!cancelled.get()) {
                long start = System.currentTimeMillis();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (escaped.contains(p)) {
                        continue;
                    }
                    RayTraceResult r = p.rayTraceBlocks(100d, FluidCollisionMode.SOURCE_ONLY);
                    if (r == null || r.getHitBlock() == null || r.getHitBlock().isEmpty()) {
                        continue;
                    }
                    BlockStareEntry e = stareMap.get(p.getUniqueId());
                    e.set(r.getHitBlock().getX(), r.getHitBlock().getY(), r.getHitBlock().getZ());
                    if (e.testTimeStare()) {
                        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> r.getHitBlock().setType(Material.AIR));
                    }
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    @Override
    public void escapePlayer(Player p) {
        escaped.add(p);
    }

    @Override
    public void complete() {
        escaped.clear();
        cancelled.set(true);
        super.complete();
    }

    @Override
    public void cancel() {
        escaped.clear();
        if (t != null) {
            t.stop();
        }
        super.cancel();
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
            boolean longer = cur - firstStamp > 200;
            if (longer) {
                firstStamp = cur;
            }
            return longer;
        }

    }

}
