package me.dordsor21.MissionGameAware.challenges.Survival.impl;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Survival.TimedChallenge;
import me.dordsor21.MissionGameAware.challenges.impl.SurvivalChallenge;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Dance extends TimedChallenge {

    private static final String[] messages = new String[] {"&cDance&f (jump about) with another player! You have 5 minutes.",
        "You have 5 minutes to &cdance&f (jump about) with another player!",
        "&cDance&f (jump about) with another player in the next 5 minutes."};
    private final JumpListener listener;

    public Dance() {
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
        private final Map<Player, Location> cache = new HashMap<>();
        private final Map<Player, Integer> count = new HashMap<>();

        private JumpListener() {
            for (Player p : Bukkit.getOnlinePlayers()) {
                cache.put(p, p.getLocation());
                count.put(p, 0);
            }
        }

        @EventHandler
        public void onJump(PlayerJumpEvent e) {
            if (completed.contains(e.getPlayer())) {
                return;
            }
            List<Entity> nearby = e.getPlayer().getNearbyEntities(10, 10, 10);
            if (nearby.size() == 0) {
                return;
            }
            boolean nearPlayer = false;
            for (Entity ent : nearby) {
                if (ent.getType() == EntityType.PLAYER) {
                    nearPlayer = true;
                    break;
                }
            }
            if (!nearPlayer) {
                return;
            }
            Location cached = cache.get(e.getPlayer());
            if (cached.getBlock() != e.getTo().getBlock() || cached.getBlock() != e.getTo().getBlock()
                || cached.getBlock() != e.getTo().getBlock()) {
                int c = count.get(e.getPlayer());
                c++;
                if (c >= 5) {
                    completed.add(e.getPlayer());
                    SurvivalChallenge.playerScores.merge(e.getPlayer(), 1, Integer::sum);
                    e.getPlayer().sendTitle("", ChatColor.translateAlternateColorCodes('&', "Point obtained!"), 0, 70, 20);
                    cache.remove(e.getPlayer());
                    count.remove(e.getPlayer());
                    return;
                }
                cache.replace(e.getPlayer(), e.getTo());
                count.replace(e.getPlayer(), c);
            }
        }
    }
}
