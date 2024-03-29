package me.dordsor21.MissionGameAware.challenges.Survival.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Survival.SingleTimedChallenge;
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
import java.util.Random;
import java.util.Set;

public class ChickenEgg extends SingleTimedChallenge {

    private static final String[] messages = new String[] {"Hatch a &cchicken&f from an egg. You have 4 minutes.",
        "You have 4 minutes to hatch a &cchicken&f from an egg.", "Hatch a &cchicken&f from an egg in the next 4 minutes."};
    private final BreedListener listener;

    public ChickenEgg() {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> Bukkit.broadcastMessage(
            ChatColor.translateAlternateColorCodes('&', SurvivalChallenge.cPr + messages[new Random().nextInt(3)])));
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::finish, 4800L);
        Bukkit.getPluginManager().registerEvents((listener = new BreedListener()), MissionGameAware.plugin);
    }

    @Override
    public void finish() {
        HandlerList.unregisterAll(listener);
        SurvivalChallenge.running.remove(this);
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
