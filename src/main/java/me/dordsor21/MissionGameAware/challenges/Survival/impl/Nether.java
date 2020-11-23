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
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Nether extends TimedChallenge {
    private static final String[] messages =
        new String[] {"Get to the &cNether&f. You have 30 seconds.", "You have 30 seconds to get to the &cNether&f&f.",
            "Get to the &cNether&f&f in the next 30 seconds."};
    private final NetherListener listener;

    public Nether() {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> Bukkit.broadcastMessage(
            ChatColor.translateAlternateColorCodes('&', SurvivalChallenge.cPr + messages[new Random().nextInt(3)])));
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::finish, 600L);
        Bukkit.getPluginManager().registerEvents((listener = new NetherListener()), MissionGameAware.plugin);
    }

    @Override
    public void finish() {
        HandlerList.unregisterAll(listener);
    }

    private static final class NetherListener implements Listener {

        private final Set<Player> completed = new HashSet<>();

        @EventHandler
        public void onPortal(PlayerPortalEvent e) {
            if (completed.contains(e.getPlayer())) {
                return;
            }
            if (e.getCause() != PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
                return;
            }
            completed.add(e.getPlayer());
            SurvivalChallenge.playerScores.merge(e.getPlayer(), 1, Integer::sum);
            e.getPlayer().sendTitle("", ChatColor.translateAlternateColorCodes('&', "Point obtained!"), 0, 70, 20);
        }
    }
}
