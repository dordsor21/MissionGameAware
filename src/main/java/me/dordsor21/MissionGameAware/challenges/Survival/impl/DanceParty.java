package me.dordsor21.MissionGameAware.challenges.Survival.impl;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Survival.TimedChallenge;
import me.dordsor21.MissionGameAware.challenges.impl.SurvivalChallenge;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class DanceParty extends TimedChallenge {

    private static final String[] messages = new String[] {"&cDance&f party at spawn! You have 2 minutes.",
        "You have 2 minutes to join the &cdance&f party at spawn!", "&cDance&f party at spawn in the next 2 minutes!."};
    private final JumpListener listener;

    public DanceParty() {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> Bukkit.broadcastMessage(
            ChatColor.translateAlternateColorCodes('&', SurvivalChallenge.cPr + messages[new Random().nextInt(3)])));
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::finish, 6000L);
        Bukkit.getPluginManager().registerEvents((listener = new JumpListener()), MissionGameAware.plugin);
    }

    @Override
    public void finish() {
        HandlerList.unregisterAll(listener);
        SurvivalChallenge.running.remove(this);
    }


    private static final class JumpListener implements Listener {

        private final Set<Player> completed = new HashSet<>();
        private final Map<Player, Integer> count = new HashMap<>();

        private JumpListener() {
            for (Player p : Bukkit.getOnlinePlayers()) {
                count.put(p, 0);
            }
        }

        @EventHandler
        public void onJump(PlayerJumpEvent e) {
            if (completed.contains(e.getPlayer())) {
                return;
            }
            if (e.getPlayer().getLocation().distance(new Location(e.getPlayer().getWorld(), -56.5, 63, -95.5)) > 10) {
                return;
            }
            int c = count.get(e.getPlayer());
            c++;
            if (c >= 5) {
                completed.add(e.getPlayer());
                SurvivalChallenge.playerScores.merge(e.getPlayer(), 1, Integer::sum);
                e.getPlayer().sendTitle("", ChatColor.translateAlternateColorCodes('&', "Point obtained!"), 0, 70, 20);
                count.remove(e.getPlayer());
                return;
            }
            count.replace(e.getPlayer(), c);
        }
    }
}
