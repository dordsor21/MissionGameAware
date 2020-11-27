package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.MeanTwist;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class LookDown extends MeanTwist {
    private static final List<Player> escaped = new ArrayList<>();

    private BukkitTask r = null;
    private BukkitTask t = null;

    @Override
    public void escapePlayer(Player p) {
        escaped.add(p);
        p.removePotionEffect(PotionEffectType.BLINDNESS);
    }

    @Override
    public void start() {
        System.out.println(this.getClass().getSimpleName() + " twist started.");
        escaped.clear();
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&4Look &lDOWN"), "", 0, 70, 20);
        }
        r = Bukkit.getScheduler().runTaskTimer(MissionGameAware.plugin, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (escaped.contains(p)) {
                    continue;
                }
                if (p.getLocation().getPitch() < 45) {
                    Location l = p.getLocation();
                    l.setPitch(45);
                    p.teleport(l);
                }
            }
        }, 0, 1);
        t = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 20 * 20L);
    }

    @Override
    public void cancel() {
        escaped.clear();
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
        escaped.clear();
        r.cancel();
        super.complete();
    }
}
