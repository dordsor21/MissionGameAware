package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.WeirdTwist;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public class Lightning extends WeirdTwist {

    private BukkitTask r = null;
    private BukkitTask t = null;

    @Override
    public void start() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.getWorld().strikeLightningEffect(p.getLocation());
        }
        r = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 30 * 20L);
        final Random r = new Random();
        t = Bukkit.getScheduler().runTaskTimer(MissionGameAware.plugin, () -> {
            Player p = (Player) Bukkit.getOnlinePlayers().toArray()[r.nextInt(Bukkit.getOnlinePlayers().size())];
            p.getWorld().strikeLightningEffect(p.getLocation());
        }, 10, 2);
    }

    @Override
    public void cancel() {
        if (t != null) {
            t.cancel();
        }
        if (r != null) {
            r.cancel();
        }
        super.cancel();
    }

    @Override
    public void complete() {
        t.cancel();
        super.complete();
    }
}
