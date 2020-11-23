package me.dordsor21.MissionGameAware.challenges.Survival.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Survival.SingleTimedChallenge;
import me.dordsor21.MissionGameAware.challenges.Survival.SurvChallenge;
import me.dordsor21.MissionGameAware.challenges.impl.SurvivalChallenge;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashSet;
import java.util.Set;

public class KillWither extends SingleTimedChallenge {

    private final KillMobListener listener;

    public KillWither() {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            Bukkit.broadcastMessage("§fKill a §4Wither§f. You have until the end of the Survival challenge.");
        });
        Bukkit.getPluginManager().registerEvents((listener = new KillMobListener()), MissionGameAware.plugin);
    }

    @Override
    public void finish() {
        HandlerList.unregisterAll(listener);
    }

    private static final class KillMobListener implements Listener {

        private final Set<Player> completed = new HashSet<>();

        @EventHandler
        public void onEntityDeath(EntityDeathEvent e) {
            if (e.getEntityType() != EntityType.WITHER) {
                return;
            }
            if (e.getEntity().getKiller() != null) {
                Player p = e.getEntity().getKiller();
                if (!completed.contains(p)) {
                    completed.add(p);
                    SurvivalChallenge.playerScores.merge(p, 1, Integer::sum);
                    p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "Point obtained!"), 0, 70, 20);
                }
            }
        }
    }
}
