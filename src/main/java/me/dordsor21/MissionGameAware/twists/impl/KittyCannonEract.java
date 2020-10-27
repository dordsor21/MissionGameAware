package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.WeirdTwist;
import org.bukkit.Bukkit;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;

public class KittyCannonEract extends WeirdTwist {

    private KittyCannonListener listener;
    private BukkitTask r = null;

    @Override
    public void start() {
        Bukkit.getPluginManager().registerEvents((listener = new KittyCannonListener()), MissionGameAware.plugin);
        r = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 6000L);
    }

    @Override
    public void complete() {
        HandlerList.unregisterAll(listener);
        super.complete();
    }

    @Override
    public void cancel() {
        HandlerList.unregisterAll(listener);
        if (r != null) {
            r.cancel();
        }
        super.cancel();
    }

    private static final class KittyCannonListener implements Listener {

        @EventHandler
        public void onInteract(PlayerInteractEvent e) {
            if ((e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getPlayer().getInventory().getItemInMainHand().getType().isBlock()) || (
                e.getAction() == Action.LEFT_CLICK_BLOCK && !e.getPlayer().getInventory().getItemInMainHand().getType().name().toLowerCase()
                    .contains("swordf"))) {
                return;
            }
            final Cat cat = (Cat) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getEyeLocation(), EntityType.CAT);
            cat.setTamed(true);
            cat.setBaby();
            cat.setInvulnerable(true);
            cat.setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(2));
            Bukkit.getScheduler().scheduleSyncDelayedTask(MissionGameAware.plugin, () -> {
                cat.getWorld().createExplosion(cat.getLocation(), 5F, false, false);
                cat.remove();
            }, 30);
        }

    }

}
