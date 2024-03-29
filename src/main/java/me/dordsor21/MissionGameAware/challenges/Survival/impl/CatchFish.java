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
import org.bukkit.event.player.PlayerFishEvent;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CatchFish extends TimedChallenge {

    private static final String[] messages =
        new String[] {"Catch a &cfish&f. You have 5 minutes.", "You have 5 minutes to catch a &cfish&f.",
            "Catch a &cfish&f in the next 5 minutes."};
    private final FishingListener listener;

    public CatchFish() {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> Bukkit.broadcastMessage(
            ChatColor.translateAlternateColorCodes('&', SurvivalChallenge.cPr + messages[new Random().nextInt(3)])));
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::finish, 6000L);
        Bukkit.getPluginManager().registerEvents((listener = new FishingListener()), MissionGameAware.plugin);
    }

    @Override
    public void finish() {
        HandlerList.unregisterAll(listener);
        SurvivalChallenge.running.remove(this);
    }

    private static final class FishingListener implements Listener {
        private final Set<Player> completed = new HashSet<>();

        @EventHandler
        public void onFishing(PlayerFishEvent e) {
            if (e.getCaught() != null) {
                Player p = e.getPlayer();
                if (!completed.contains(p)) {
                    completed.add(p);
                    SurvivalChallenge.playerScores.merge(p, 1, Integer::sum);
                    p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "Point obtained!"), 0, 70, 20);
                }
            }
        }
    }
}
