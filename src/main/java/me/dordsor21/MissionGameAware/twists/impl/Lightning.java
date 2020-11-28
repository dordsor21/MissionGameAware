package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.WeirdTwist;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        System.out.println(this.getClass().getSimpleName() + " twist started.");
        escaped.clear();
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (escaped.contains(p)) {
                    continue;
                }
                p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "&4Twist&f: " + this.getClass().getSimpleName()),
                    0, 70, 20);
                p.getWorld().strikeLightningEffect(p.getLocation());
            }
        });
        r = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 30 * 20L);
        final Random rr = new Random();
        t = Bukkit.getScheduler().runTaskTimer(MissionGameAware.plugin, () -> {
            Player p = (Player) Bukkit.getOnlinePlayers().toArray()[rr.nextInt(Bukkit.getOnlinePlayers().size())];
            if (escaped.contains(p)) {
                return;
            }
            p.getWorld().strikeLightningEffect(p.getLocation());
        }, 10, 4);
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
