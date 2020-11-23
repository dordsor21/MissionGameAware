package me.dordsor21.MissionGameAware.challenges.Survival.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Survival.SingleTimedChallenge;
import me.dordsor21.MissionGameAware.challenges.impl.SurvivalChallenge;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BedrockHurt extends SingleTimedChallenge {
    private static final String[] messages = new String[] {"Hurt your hand on some &cbedrock&f. You have 2 minutes.",
        "You have 2 minutes to hurt your hand on some &cbedrock&f.", "Hurt your hand on &cbedrock&f in the next 2 minutes."};
    private final HurtListener listener;

    public BedrockHurt() {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> Bukkit.broadcastMessage(
            ChatColor.translateAlternateColorCodes('&', SurvivalChallenge.cPr + messages[new Random().nextInt(3)])));
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::finish, 2400L);
        Bukkit.getPluginManager().registerEvents((listener = new HurtListener()), MissionGameAware.plugin);
    }

    @Override
    public void finish() {
        HandlerList.unregisterAll(listener);
    }

    private static final class HurtListener implements Listener {

        private final Set<Player> completed = new HashSet<>();

        @EventHandler
        public void onPunch(PlayerInteractEvent e) {
            if (completed.contains(e.getPlayer())) {
                return;
            }
            if (e.getAction() != Action.LEFT_CLICK_BLOCK) {
                return;
            }
            if (e.getClickedBlock() != null && e.getClickedBlock().getType() != Material.BEDROCK) {
                return;
            }
            e.getPlayer().damage(5d);
            completed.add(e.getPlayer());
            SurvivalChallenge.playerScores.merge(e.getPlayer(), 1, Integer::sum);
            e.getPlayer().sendTitle("", ChatColor.translateAlternateColorCodes('&', "Point obtained!"), 0, 70, 20);
        }
    }
}
