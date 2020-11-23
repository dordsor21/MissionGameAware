package me.dordsor21.MissionGameAware.challenges.Survival.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Survival.TimedChallenge;
import me.dordsor21.MissionGameAware.challenges.impl.SurvivalChallenge;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Burn extends TimedChallenge {
    private static final String[] messages = new String[] {"&cBurn&f. You have 2 minutes to earn double points.",
        "You have 2 minutes to &cBurn&f. This is worth double points.", "&cBurn&f in the next 2 minutes for double points."};
    private final GrowTreeListener listener;

    public Burn() {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> Bukkit.broadcastMessage(
            ChatColor.translateAlternateColorCodes('&', SurvivalChallenge.cPr + messages[new Random().nextInt(3)])));
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::finish, 2400L);
        Bukkit.getPluginManager().registerEvents((listener = new GrowTreeListener()), MissionGameAware.plugin);
    }

    @Override
    public void finish() {
        HandlerList.unregisterAll(listener);
        SurvivalChallenge.running.remove(this);
    }

    private static final class GrowTreeListener implements Listener {

        private final Set<Player> completed = new HashSet<>();

        @EventHandler
        public void onDeath(EntityDamageEvent e) {
            if (e.getEntityType() != EntityType.PLAYER) {
                return;
            }
            Player p = (Player) e.getEntity();
            if (completed.contains(p)) {
                return;
            }
            switch (e.getCause()) {
                case FIRE:
                case FIRE_TICK:
                case LAVA:
                    completed.add(p);
                    SurvivalChallenge.playerScores.merge(p, 2, Integer::sum);
                    p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "2 points obtained!"), 0, 70, 20);
            }
        }
    }
}
