package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.MeanTwist;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class NightDay extends MeanTwist {

    private final Set<Player> escaped = new LinkedHashSet<>();
    private ScheduledFuture<?> runner = null;
    private boolean day = false;
    private BukkitTask t = null;

    @Override
    public void escapePlayer(Player p) {
        escaped.clear();
        p.setPlayerTime(p.getWorld().getTime(), false);
        escaped.add(p);
    }

    @Override
    public void start() {
        escaped.clear();
        runner = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (escaped.contains(p)) {
                    continue;
                }
                p.setPlayerTime(day ? 6000L : 12000L, false);
            }
            day = !day;
        }, 0L, 5L, TimeUnit.SECONDS);
        t = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 60 * 20L);
    }

    @Override
    public void cancel() {
        escaped.clear();
        if (t != null) {
            t.cancel();
        }
        if (runner != null) {
            runner.cancel(true);
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setPlayerTime(p.getWorld().getTime(), false);
        }
        super.complete();
    }

    @Override
    public void complete() {
        escaped.clear();
        if (runner != null) {
            runner.cancel(true);
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setPlayerTime(p.getWorld().getTime(), false);
        }
        super.complete();
    }
}
