package me.dordsor21.MissionGameAware.challenges.Survival.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Survival.TimedChallenge;
import me.dordsor21.MissionGameAware.challenges.impl.SurvivalChallenge;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MilkCow extends TimedChallenge {
    private static final String[] messages =
        new String[] {"Milk a &ccow&f. You have 2 minutes.", "You have 2 minutes to milk a &ccow&f.",
            "Milk a &ccow&f in the next 2 minutes."};
    private final GrowTreeListener listener;

    public MilkCow() {
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> Bukkit.broadcastMessage(
            ChatColor.translateAlternateColorCodes('&', SurvivalChallenge.cPr + messages[new Random().nextInt(3)])));
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
        public void milkCOW(PlayerInteractEntityEvent e) {
            if (completed.contains(e.getPlayer())) {
                return;
            }
            if (!(e.getRightClicked() instanceof Cow) || !((Cow) e.getRightClicked()).isAdult()) {
                return;
            }
            if (e.getHand() == EquipmentSlot.OFF_HAND
                && e.getPlayer().getInventory().getItemInOffHand().getType() != Material.BUCKET) {
                return;
            } else if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.BUCKET) {
                return;
            }
            completed.add(e.getPlayer());
            SurvivalChallenge.playerScores.merge(e.getPlayer(), 1, Integer::sum);
            e.getPlayer().sendTitle("", ChatColor.translateAlternateColorCodes('&', "Point obtained!"), 0, 70, 20);
        }
    }
}
