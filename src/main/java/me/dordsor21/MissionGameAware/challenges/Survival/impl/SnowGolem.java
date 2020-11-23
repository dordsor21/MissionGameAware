package me.dordsor21.MissionGameAware.challenges.Survival.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Survival.SingleTimedChallenge;
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
import java.util.Random;
import java.util.Set;

public class SnowGolem extends SingleTimedChallenge {
    private static final String[] messages =
        new String[] {"Build a &cSnow Golem&f. You have 3 minutes.", "You have 3 minutes to build a &cSnow Golem&f.",
            "Build a &cSnow Golem&f in the next 3 minutes."};
    private final GrowTreeListener listener;

    public SnowGolem() {
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
        public void placeBlock(BlockPlaceEvent e) {
            if (completed.contains(e.getPlayer())) {
                return;
            }
            Block placed = e.getBlockPlaced();
            if (placed.getType() != Material.CARVED_PUMPKIN) {
                return;
            }
            if (placed.getRelative(0, -1, 0).getType() == Material.SNOW_BLOCK
                && placed.getRelative(0, -2, 0).getType() == Material.SNOW_BLOCK) {
                completed.add(e.getPlayer());
                SurvivalChallenge.playerScores.merge(e.getPlayer(), 1, Integer::sum);
                e.getPlayer().sendTitle("", ChatColor.translateAlternateColorCodes('&', "Point obtained!"), 0, 70, 20);
            }
        }
    }
}
