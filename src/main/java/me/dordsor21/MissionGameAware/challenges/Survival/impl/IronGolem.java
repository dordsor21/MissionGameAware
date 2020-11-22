package me.dordsor21.MissionGameAware.challenges.Survival.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Survival.TimedChallenge;
import me.dordsor21.MissionGameAware.challenges.impl.SurvivalChallenge;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashSet;
import java.util.Set;

public class IronGolem extends TimedChallenge {
    private final GrowTreeListener listener;

    public IronGolem() {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            Bukkit.broadcastMessage("§fBuild an Iron Golem. You have 2 minutes.");
        });
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::finish, 2400L);
        Bukkit.getPluginManager().registerEvents((listener = new GrowTreeListener()), MissionGameAware.plugin);
    }

    @Override
    public void finish() {
        HandlerList.unregisterAll(listener);
    }

    private static final class GrowTreeListener implements Listener {

        private final Set<Player> completed = new HashSet<>();

        @EventHandler
        public void placeBlock(BlockPlaceEvent e) {
            if (completed.contains(e.getPlayer())) {
                return;
            }
            Block placed = e.getBlockPlaced();
            if (placed.getType() != Material.CARVED_PUMPKIN) {
                return;
            }
            if (placed.getRelative(0, -1, 0).getType() == Material.IRON_BLOCK
                && placed.getRelative(0, -2, 0).getType() == Material.IRON_BLOCK && (
                (placed.getRelative(1, -1, 0).getType() == Material.IRON_BLOCK
                    && placed.getRelative(-1, -1, 0).getType() == Material.IRON_BLOCK) || (
                    placed.getRelative(0, -1, 1).getType() == Material.IRON_BLOCK
                        && placed.getRelative(0, -1, -1).getType() == Material.IRON_BLOCK))) {
                completed.add(e.getPlayer());
                SurvivalChallenge.playerScores.merge(e.getPlayer(), 1, Integer::sum);
                e.getPlayer().sendTitle("", ChatColor.translateAlternateColorCodes('&', "Point obtained!"), 0, 70, 20);
            }
        }
    }
}
