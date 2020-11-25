package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.MeanTwist;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Zoom extends MeanTwist {

    private static final List<Player> escaped = new ArrayList<>();
    private BukkitTask t;
    private BukkitTask t1;
    private BukkitTask t2;
    private BukkitTask t3;
    private BukkitTask t4;
    private BukkitTask t5;
    private BukkitTask t6;
    private BukkitTask t7;

    @Override
    public void escapePlayer(Player p) {
        escaped.add(p);
    }

    @Override
    public void start() {
        escaped.clear();
        Runnable r = new ZoomRunnable();
        r.run();
        t = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, r, 50L);
        t1 = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, r, 140L);
        t2 = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, r, 230L);
        t3 = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, r, 320L);
        t4 = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, r, 410L);
        t5 = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, r, 500L);
        t6 = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, r, 590L);
        t7 = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 600L);
    }

    @Override
    public void cancel() {
        escaped.clear();
        if (t != null) {
            t.cancel();
        }
        if (t1 != null) {
            t1.cancel();
        }
        if (t2 != null) {
            t2.cancel();
        }
        if (t3 != null) {
            t3.cancel();
        }
        if (t4 != null) {
            t4.cancel();
        }
        if (t5 != null) {
            t5.cancel();
        }
        if (t6 != null) {
            t6.cancel();
        }
        if (t7 != null) {
            t7.cancel();
        }
        super.cancel();
    }

    private static final class ZoomRunnable implements Runnable {

        private static final Vector v05 = new Vector(0.5, 0.5, 0.5);

        @Override
        public void run() {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (escaped.contains(p)) {
                    return;
                }
                p.setVelocity(Vector.getRandom().subtract(v05).multiply(8 + 8 * Math.random()));
                p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&3Zoooom!"), "", 0, 70, 20);
            }
        }
    }
}
