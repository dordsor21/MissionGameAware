package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.EvilTwist;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class Speed10 extends EvilTwist {

    private static final List<Player> escaped = new ArrayList<>();

    private Speed10Listener listener;
    private BukkitTask r = null;

    @Override
    public void escapePlayer(Player p) {
        p.setFlySpeed(0.1f);
        p.setWalkSpeed(0.2f);
        escaped.add(p);
    }

    private static float getRealMoveSpeed(final boolean isFly) {
        final float defaultSpeed = isFly ? 0.1f : 0.2f;
        float maxSpeed = 1f;

        float ratio = (((float) 10 - 1) / 9) * (maxSpeed - defaultSpeed);
        return ratio + defaultSpeed;
    }

    @Override
    public void start() {
        escaped.clear();
        Bukkit.getScheduler().runTask(MissionGameAware.plugin,
            () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "perms group group set byteutil.speeder false"));
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setFlySpeed(getRealMoveSpeed(true));
            p.setWalkSpeed(getRealMoveSpeed(false));
            p.playSound(p.getLocation(), Sound.ITEM_ELYTRA_FLYING, 2.0f, 2.0f);
        }
        Bukkit.getPluginManager().registerEvents((listener = new Speed10Listener()), MissionGameAware.plugin);
        r = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, () -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "perms group group set byteutil.speeder true");
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.setFlySpeed(0.1f);
                p.setWalkSpeed(0.2f);
            }
            this.complete();
        }, 6000L);
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
        HandlerList.unregisterAll(listener);
        if (r != null) {
            r.cancel();
        }
        super.cancel();
    }

    private static final class Speed10Listener implements Listener {
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent e) {
            if (escaped.contains(e.getPlayer())) {
                return;
            }
            Player p = e.getPlayer();
            p.setFlySpeed(getRealMoveSpeed(true));
            p.setWalkSpeed(getRealMoveSpeed(false));
        }
    }

}
