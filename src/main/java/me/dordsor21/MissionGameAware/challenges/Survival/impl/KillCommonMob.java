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
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class KillCommonMob extends TimedChallenge {

    private static final String[] messages =
        new String[] {"Kill a &c%s&f. You have 5 minutes.", "You have 5 minutes to kill a &c%s&f.",
            "Kill a &c%s&f in the next 5 minutes."};
    private final KillMobListener listener;

    public KillCommonMob() {
        Entities entity = Entities.values()[new Random().nextInt(Entities.values().length)];
        String name = entity.name().toLowerCase().replace('_', ' ');
        EntityType type = EntityType.valueOf(entity.name());
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> Bukkit.broadcastMessage(ChatColor
            .translateAlternateColorCodes('&',
                String.format(SurvivalChallenge.cPr + messages[new Random().nextInt(3)], name))));
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::finish, 6000L);
        Bukkit.getPluginManager().registerEvents((listener = new KillMobListener(type)), MissionGameAware.plugin);
    }

    @Override
    public void finish() {
        HandlerList.unregisterAll(listener);
    }

    private enum Entities {
        BAT, BEE, BLAZE, CAVE_SPIDER, CHICKEN, COD, COW, CREEPER, DROWNED, ENDERMAN, FOX, HOGLIN, HORSE, LLAMA, OCELOT, PIG,
        PIGLIN, PILLAGER, PLAYER, PUFFERFISH, RABBIT, SALMON, SHEEP, SKELETON, SLIME, SPIDER, SQUID, TURTLE, WOLF, ZOMBIE
    }


    private static final class KillMobListener implements Listener {

        private final EntityType type;
        private final Set<Player> completed = new HashSet<>();

        private KillMobListener(EntityType type) {
            this.type = type;
        }

        @EventHandler
        public void onEntityDeath(EntityDeathEvent e) {
            if (e.getEntityType() != type) {
                return;
            }
            if (e.getEntity().getKiller() != null) {
                Player p = e.getEntity().getKiller();
                if (!completed.contains(p)) {
                    completed.add(p);
                    SurvivalChallenge.playerScores.merge(p, 1, Integer::sum);
                    p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "Point obtained!"), 0, 70, 20);
                }
            }
        }
    }
}
