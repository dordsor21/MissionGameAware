package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.MeanTwist;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class PumpkinHead extends MeanTwist {

    private JackoListener listener;
    private BukkitTask r = null;

    @Override
    public void start() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.getInventory().setHelmet(new ItemStack(Material.CARVED_PUMPKIN));
        }
        Bukkit.getPluginManager().registerEvents((listener = new JackoListener()), MissionGameAware.plugin);
        r = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 60 * 20L);
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

    private static final class JackoListener implements Listener {
        @EventHandler
        public void onInventoryClose(InventoryCloseEvent e) {
            if (e.getPlayer().getInventory().getHelmet() == null || e.getPlayer().getInventory().getHelmet().getType() != Material.CARVED_PUMPKIN) {
                e.getPlayer().getInventory().setHelmet(new ItemStack(Material.CARVED_PUMPKIN));
            }
        }

        @EventHandler
        public void onInteract(PlayerInteractEvent e) {
            if (!(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
                return;
            }
            String item = e.getPlayer().getInventory().getItemInMainHand().getType().name().toLowerCase();
            if (item.contains("helmet")) {
                e.setCancelled(true);
                e.getPlayer().getInventory().setHelmet(new ItemStack(Material.CARVED_PUMPKIN));
            }
        }
    }
}
