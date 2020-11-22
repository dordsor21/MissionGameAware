package me.dordsor21.MissionGameAware.challenges.Survival.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Survival.TimedChallenge;
import me.dordsor21.MissionGameAware.challenges.impl.SurvivalChallenge;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.HashSet;
import java.util.Set;

public class ChickenEgg extends TimedChallenge {

    private final BreedListener listener;

    public ChickenEgg() {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            Bukkit.broadcastMessage("§fBreed an animal. You have 5 minutes.");
        });
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::finish, 6000L);
        Bukkit.getPluginManager().registerEvents((listener = new BreedListener()), MissionGameAware.plugin);
    }

    @Override
    public void finish() {
        HandlerList.unregisterAll(listener);
    }

    private static final class BreedListener implements Listener {

        private final Set<Player> completed = new HashSet<>();

        @EventHandler
        public void onChickenSpawn(CreatureSpawnEvent e) {
            if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.EGG && e.getEntityType() == EntityType.CHICKEN) {
                e.setCancelled(true);
            }
        }

        @EventHandler
        public void onProjectileHit(ProjectileHitEvent e) {
            if (e.getEntity().getType() != EntityType.EGG || e.getHitBlock() == null || e.getHitEntity() != null) {
                return;
            }
            if (!(e.getEntity().getShooter() instanceof Player)) {
                return;
            }
            Player p = (Player) e.getEntity().getShooter();
            boolean spawn = Math.random() < 1d / 8;
            if (!spawn) {
                return;
            }
            Location l = e.getHitBlock().getLocation().add(e.getHitBlockFace().getDirection());
            if (completed.contains(p)) {
                return;
            }
            completed.add(p);
            SurvivalChallenge.playerScores.merge(p, 1, Integer::sum);
            p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "Point obtained!"), 0, 70, 20);

        }
    }
}
