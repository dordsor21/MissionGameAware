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

    @Override
    public void escapePlayer(Player p) {
        escaped.add(p);
    }

    @Override
    public void start() {
        escaped.clear();
        new ZoomRunnable().run();
        t = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, new ZoomRunnable(), 100L);
        t1 = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, new ZoomRunnable(), 200L);
        t2 = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, new ZoomRunnable(), 300L);
        t3 = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 400L);
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
        super.cancel();
    }

    private static final class ZoomRunnable implements Runnable {

        @Override
        public void run() {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (escaped.contains(p)) {
                    return;
                }
                p.setVelocity(Vector.getRandom().multiply(2 * Math.random()));
                p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&3Zoooom!"), "", 0, 70, 20);
            }
        }
    }
}
