package me.dordsor21.MissionGameAware.challenges.Survival.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Survival.SingleTimedChallenge;
import me.dordsor21.MissionGameAware.challenges.impl.SurvivalChallenge;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTameEvent;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TameCat extends SingleTimedChallenge {
    private static final String[] messages =
        new String[] {"Tame a &ccat&f. You have 5 minutes.", "You have 5 minutes to tame a &ccat&f.",
            "Tame a &ccat&f in the next 5 minutes."};
    private final GrowTreeListener listener;

    public TameCat() {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> Bukkit.broadcastMessage(
            ChatColor.translateAlternateColorCodes('&', SurvivalChallenge.cPr + messages[new Random().nextInt(3)])));
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::finish, 6000L);
        Bukkit.getPluginManager().registerEvents((listener = new GrowTreeListener()), MissionGameAware.plugin);
    }

    @Override
    public void finish() {
        HandlerList.unregisterAll(listener);
    }

    private static final class GrowTreeListener implements Listener {

        private final Set<Player> completed = new HashSet<>();

        @EventHandler
        public void placeBlock(EntityTameEvent e) {
            if (e.getEntity().getType() != EntityType.CAT || !(e.getOwner() instanceof Player)) {
                return;
            }
            Player p = (Player) e.getOwner();
            if (completed.contains(p)) {
                return;
            }
            completed.add(p);
            SurvivalChallenge.playerScores.merge(p, 1, Integer::sum);
            p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "Point obtained!"), 0, 70, 20);
        }
    }
}
