package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.MeanTwist;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class LookUp extends MeanTwist {

    private BukkitTask r = null;
    private BukkitTask t = null;

    @Override
    public void start() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&3Look &lUP"), "", 0, 70, 20);
        }
        r = Bukkit.getScheduler().runTaskTimer(MissionGameAware.plugin, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getLocation().getPitch() > -45) {
                    Location l = p.getLocation();
                    l.setPitch(-45);
                    p.teleport(l);
                }
            }
        }, 0, 1);
        t = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 20 * 20L);
    }

    @Override
    public void cancel() {
        if (r != null) {
            r.cancel();
        }
        if (t != null) {
            t.cancel();
        }
        super.cancel();
    }

    @Override
    public void complete() {
        r.cancel();
        super.complete();
    }
}
