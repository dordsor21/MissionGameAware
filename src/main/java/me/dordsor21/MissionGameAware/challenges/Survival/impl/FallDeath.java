package me.dordsor21.MissionGameAware.challenges.Survival.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Survival.TimedChallenge;
import me.dordsor21.MissionGameAware.challenges.impl.SurvivalChallenge;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class FallDeath extends TimedChallenge {
    private static final String[] messages =
        new String[] {"Die from &cfall damage&f. You have 1 minute to earn triple points.",
            "You have 1 minute to &cfall&f to death. This is worth triple points.",
            "&cFall to death&f in the next minute for triple points."};
    private final GrowTreeListener listener;

    public FallDeath() {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> Bukkit.broadcastMessage(
            ChatColor.translateAlternateColorCodes('&', SurvivalChallenge.cPr + messages[new Random().nextInt(3)])));
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::finish, 1200L);
        Bukkit.getPluginManager().registerEvents((listener = new GrowTreeListener()), MissionGameAware.plugin);
    }

    @Override
    public void finish() {
        HandlerList.unregisterAll(listener);
        SurvivalChallenge.running.remove(this);
    }

    private static final class GrowTreeListener implements Listener {

        private final Set<Player> respawned = new HashSet<>();
        private final Set<Player> completed = new HashSet<>();

        @EventHandler
        public void onDeath(PlayerDeathEvent e) {
            if (e.getEntity().getLastDamageCause() != null
                && e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (!completed.contains(e.getEntity())) {
                    completed.add(e.getEntity());
                    SurvivalChallenge.playerScores.merge(e.getEntity(), 3, Integer::sum);
                }
            }
        }

        @EventHandler
        public void onRespawn(PlayerRespawnEvent e) {
            if (completed.contains(e.getPlayer()) && !respawned.contains(e.getPlayer())) {
                respawned.add(e.getPlayer());
                e.getPlayer().sendTitle("", ChatColor.translateAlternateColorCodes('&', "3 points obtained!"), 0, 70, 20);
            }
        }
    }
}
