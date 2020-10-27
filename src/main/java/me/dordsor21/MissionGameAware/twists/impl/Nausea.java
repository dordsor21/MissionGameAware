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

public class Nausea extends EvilTwist {
    private NauseaListener listener;

    @Override
    public void start() {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.addPotionEffect(PotionEffectType.CONFUSION.createEffect(300, 2));
            }
        });
        Bukkit.getPluginManager().registerEvents((listener = new Nausea.NauseaListener()), MissionGameAware.plugin);
        new Thread(() -> {
            try (final Twist twist = Nausea.this) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.BLOCK_WET_GRASS_BREAK, 2.0f, 0.2f);
                }
                Thread.sleep(50L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.BLOCK_WET_GRASS_FALL, 2.0f, 0.2f);
                }
                Thread.sleep(50L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.BLOCK_WET_GRASS_HIT, 2.0f, 0.2f);
                }
                Thread.sleep(50L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.BLOCK_WET_GRASS_PLACE, 2.0f, 0.2f);
                }
                Thread.sleep(50L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.BLOCK_WET_GRASS_STEP, 2.0f, 0.2f);
                }
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void complete() {
        HandlerList.unregisterAll(listener);
        super.complete();
    }

    private static final class NauseaListener implements Listener {
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent e) {
            Player p = e.getPlayer();
            Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> p.addPotionEffect(PotionEffectType.CONFUSION.createEffect(300, 2)));
        }
    }
}
