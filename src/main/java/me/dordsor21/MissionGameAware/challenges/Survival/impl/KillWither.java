package me.dordsor21.MissionGameAware.challenges.Survival.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Survival.SingleChallenge;
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
import java.util.Random;
import java.util.Set;

public class KillWither extends SingleChallenge {

    private static final String[] messages =
        new String[] {"Kill a &cwither&f. You have until the end of the Survival challenge.",
            "You have until the end of the Survival challenge to kill a &cwither&f.",
            "Kill a &cwither&f by the end of the Survival challenge."};
    private final KillMobListener listener;

    public KillWither() {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> Bukkit.broadcastMessage(
            ChatColor.translateAlternateColorCodes('&', SurvivalChallenge.cPr + messages[new Random().nextInt(3)])));
        Bukkit.getPluginManager().registerEvents((listener = new KillMobListener()), MissionGameAware.plugin);
    }

    @Override
    public void finish() {
        HandlerList.unregisterAll(listener);
        SurvivalChallenge.running.remove(this);
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
