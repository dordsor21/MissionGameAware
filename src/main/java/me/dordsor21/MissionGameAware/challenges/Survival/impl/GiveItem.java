package me.dordsor21.MissionGameAware.challenges.Survival.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Survival.TimedChallenge;
import me.dordsor21.MissionGameAware.challenges.impl.SurvivalChallenge;
import me.dordsor21.MissionGameAware.util.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class GiveItem extends TimedChallenge {

    private final GiveItemListener listener;

    public GiveItem() {
        Lists.NiceItems item = Lists.NiceItems.values()[new Random().nextInt(EntityType.values().length)];
        String name = item.name().toLowerCase().replace('_', ' ');
        Material type = Material.valueOf(item.name());
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            Bukkit.broadcastMessage("§fGive another player one §4" + name + "§f. You have 10 minutes.");
        });
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::finish, 12000L);
        Bukkit.getPluginManager().registerEvents((listener = new GiveItemListener(type)), MissionGameAware.plugin);
    }

    @Override
    public void finish() {

    }

    private static final class GiveItemListener implements Listener {

        private final Material type;
        private final HashMap<UUID, Player> whoDropped = new HashMap<>();
        private final Set<Player> completed = new HashSet<>();

        public GiveItemListener(Material type) {
            this.type = type;
        }

        @EventHandler
        public void onItemDrop(PlayerDropItemEvent e) {
            if (e.getItemDrop().getItemStack().getType() != type) {
                return;
            }
            whoDropped.put(e.getItemDrop().getUniqueId(), e.getPlayer());
        }

        @EventHandler
        public void onItemPickup(EntityPickupItemEvent e) {
            if (e.getEntity() instanceof Player) {
                if (e.getItem().getItemStack().getType() != type) {
                    return;
                }
                Player p = whoDropped.get(e.getItem().getUniqueId());
                if (completed.contains(p)) {
                    return;
                }
                if (p != null) {
                    whoDropped.remove(e.getItem().getUniqueId());
                    SurvivalChallenge.playerScores.merge(p, 1, Integer::sum);
                    completed.add(p);
                    p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "Point obtained!"), 0, 70, 20);
                }
            }
        }
    }
}
