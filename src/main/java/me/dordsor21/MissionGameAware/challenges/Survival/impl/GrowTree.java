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
import org.bukkit.event.world.StructureGrowEvent;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GrowTree extends TimedChallenge {
    private static final String[] messages =
        new String[] {"Grow a tree &ctree&f. You have 3 minutes.", "You have 3 minutes to grow a tree &ctree&f.",
            "Grow a &ctree&f in the next 3 minutes."};
    private final GrowTreeListener listener;

    public GrowTree() {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> Bukkit.broadcastMessage(
            ChatColor.translateAlternateColorCodes('&', SurvivalChallenge.cPr + messages[new Random().nextInt(3)])));
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::finish, 3600L);
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
        public void onBreed(StructureGrowEvent e) {
            if (e.getPlayer() != null) {
                switch (e.getSpecies()) {
                    case ACACIA:
                    case BIG_TREE:
                    case BIRCH:
                    case COCOA_TREE:
                    case DARK_OAK:
                    case JUNGLE:
                    case MEGA_REDWOOD:
                    case RED_MUSHROOM:
                    case SMALL_JUNGLE:
                    case SWAMP:
                    case TALL_BIRCH:
                    case TALL_REDWOOD:
                    case TREE:
                        if (!completed.contains(e.getPlayer())) {
                            completed.add(e.getPlayer());
                            SurvivalChallenge.playerScores.merge(e.getPlayer(), 1, Integer::sum);
                            e.getPlayer()
                                .sendTitle("", ChatColor.translateAlternateColorCodes('&', "Point obtained!"), 0, 70, 20);
                        }
                }
            }
        }
    }
}
