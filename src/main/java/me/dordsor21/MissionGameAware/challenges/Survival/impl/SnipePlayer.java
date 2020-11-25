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
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SnipePlayer extends TimedChallenge {

    private static final String[] messages =
        new String[] {"&cSnipe&f another player from over 20 blocks. You have 5 minutes.",
            "You have 5 minutes to &csnipe&f another player from over 20 blocks.",
            "&cSnipe&f another &cplayer&f in the next 5 minutes."};
    private final KillMobListener listener;

    public SnipePlayer() {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> Bukkit.broadcastMessage(
            ChatColor.translateAlternateColorCodes('&', SurvivalChallenge.cPr + messages[new Random().nextInt(3)])));
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::finish, 6000L);
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
        public void onEntityDeath(ProjectileHitEvent e) {
            if (e.getHitEntity() == null || e.getHitEntity().getType() != EntityType.PLAYER || !(e.getEntity()
                .getShooter() instanceof Player)) {
                return;
            }
            Player p = (Player) e.getEntity().getShooter();
            if (completed.contains(p)) {
                return;
            }
            if (e.getHitEntity().getLocation().distance(((Player) e.getEntity().getShooter()).getLocation()) >= 20) {
                completed.add(p);
                SurvivalChallenge.playerScores.merge(p, 1, Integer::sum);
                p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "Point obtained!"), 0, 70, 20);
            }
        }
    }
}
