package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.EvilTwist;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Speed10 extends EvilTwist {

    private Speed10Listener listener;

    private static float getRealMoveSpeed(final boolean isFly) {
        final float defaultSpeed = isFly ? 0.1f : 0.2f;
        float maxSpeed = 1f;

        float ratio = (((float) 10 - 1) / 9) * (maxSpeed - defaultSpeed);
        return ratio + defaultSpeed;
    }

    @Override
    public void start() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "perms group group set byteutil.speeder false");
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setFlySpeed(getRealMoveSpeed(true));
            p.setWalkSpeed(getRealMoveSpeed(false));
            p.playSound(p.getLocation(), Sound.ITEM_ELYTRA_FLYING, SoundCategory.MUSIC, 2.0f, 2.0f);
        }
        Bukkit.getPluginManager().registerEvents((listener = new Speed10Listener()), MissionGameAware.plugin);
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, () -> {
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
        HandlerList.unregisterAll(listener);
        super.complete();
    }

    private static final class Speed10Listener implements Listener {
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent e) {
            Player p = e.getPlayer();
            p.setFlySpeed(getRealMoveSpeed(true));
            p.setWalkSpeed(getRealMoveSpeed(false));
        }
    }

}
