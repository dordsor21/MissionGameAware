package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.EvilTwist;
import me.dordsor21.MissionGameAware.twists.Twist;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Nausea extends EvilTwist {
    private static final List<Player> escaped = new ArrayList<>();
    private NauseaListener listener;
    private Thread t = null;

    @Override
    public void escapePlayer(Player p) {
        escaped.add(p);
        p.removePotionEffect(PotionEffectType.CONFUSION);
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
                p.addPotionEffect(PotionEffectType.CONFUSION.createEffect(300, 2));
            }
        });
        Bukkit.getPluginManager().registerEvents((listener = new Nausea.NauseaListener()), MissionGameAware.plugin);
        t = new Thread(() -> {
            try (final Twist twist = Nausea.this) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (escaped.contains(p)) {
                        continue;
                    }
                    p.playSound(p.getLocation(), Sound.BLOCK_WET_GRASS_BREAK, 2.0f, 0.2f);
                }
                Thread.sleep(50L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (escaped.contains(p)) {
                        continue;
                    }
                    p.playSound(p.getLocation(), Sound.BLOCK_WET_GRASS_FALL, 2.0f, 0.2f);
                }
                Thread.sleep(50L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (escaped.contains(p)) {
                        continue;
                    }
                    p.playSound(p.getLocation(), Sound.BLOCK_WET_GRASS_HIT, 2.0f, 0.2f);
                }
                Thread.sleep(50L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (escaped.contains(p)) {
                        continue;
                    }
                    p.playSound(p.getLocation(), Sound.BLOCK_WET_GRASS_PLACE, 2.0f, 0.2f);
                }
                Thread.sleep(50L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (escaped.contains(p)) {
                        continue;
                    }
                    p.playSound(p.getLocation(), Sound.BLOCK_WET_GRASS_STEP, 2.0f, 0.2f);
                }
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    @Override
    public void complete() {
        escaped.clear();
        HandlerList.unregisterAll(listener);
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

    private static final class NauseaListener implements Listener {
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent e) {
            if (escaped.contains(e.getPlayer())) {
                return;
            }
            Player p = e.getPlayer();
            Bukkit.getScheduler()
                .runTask(MissionGameAware.plugin, () -> p.addPotionEffect(PotionEffectType.CONFUSION.createEffect(300, 2)));
        }
    }
}
