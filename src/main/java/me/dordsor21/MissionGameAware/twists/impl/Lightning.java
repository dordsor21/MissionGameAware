package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.WeirdTwist;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lightning extends WeirdTwist {

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
        escaped.clear();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (escaped.contains(p)) {
                continue;
            }
            p.getWorld().strikeLightningEffect(p.getLocation());
        }
        r = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 30 * 20L);
        final Random r = new Random();
        t = Bukkit.getScheduler().runTaskTimer(MissionGameAware.plugin, () -> {
            Player p = (Player) Bukkit.getOnlinePlayers().toArray()[r.nextInt(Bukkit.getOnlinePlayers().size())];
            if (escaped.contains(p)) {
                return;
            }
            p.getWorld().strikeLightningEffect(p.getLocation());
        }, 10, 2);
    }

    @Override
    public void cancel() {
        escaped.clear();
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
        escaped.clear();
        t.cancel();
        super.complete();
    }
}
