package me.dordsor21.MissionGameAware.challenges.Survival.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Survival.TimedChallenge;
import me.dordsor21.MissionGameAware.challenges.impl.SurvivalChallenge;
import me.dordsor21.MissionGameAware.util.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFertilizeEvent;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GrowFarmFood extends TimedChallenge {
    private static final String[] messages = new String[] {"Grow some &c%s&f using bonemeal. You have 3 minutes.",
        "You have 3 minutes to grow some &c%s&f using bonemeal.", "Grow some &c%s&f using bonemeal in the next 3 minutes."};
    private final GrowFarmFoodListener listener;

    public GrowFarmFood() {
        Lists.Farms item = Lists.Farms.values()[new Random().nextInt(Lists.Farms.values().length)];
        String name = item.name().toLowerCase().replace('_', ' ').replace("stem", "");
        Material type = Material.valueOf(item.name());
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> Bukkit.broadcastMessage(ChatColor
            .translateAlternateColorCodes('&',
                String.format(SurvivalChallenge.cPr + messages[new Random().nextInt(3)], name))));
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::finish, 3600L);
        Bukkit.getPluginManager().registerEvents((listener = new GrowFarmFoodListener(type)), MissionGameAware.plugin);
    }

    @Override
    public void finish() {
        HandlerList.unregisterAll(listener);
    }

    private static final class GrowFarmFoodListener implements Listener {

        private final Set<Player> completed = new HashSet<>();
        private final Material type;

        public GrowFarmFoodListener(Material type) {
            this.type = type;
        }

        @EventHandler
        public void onFertilize(BlockFertilizeEvent e) {
            if (e.getPlayer() != null) {
                if (completed.contains(e.getPlayer())) {
                    return;
                }
                for (BlockState b : e.getBlocks()) {
                    if (b.getType() == type && b.getBlockData() instanceof Ageable
                        && ((Ageable) b.getBlockData()).getAge() >= 7) {
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
