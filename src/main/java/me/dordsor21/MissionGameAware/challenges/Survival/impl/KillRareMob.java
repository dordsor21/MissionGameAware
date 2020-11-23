package me.dordsor21.MissionGameAware.challenges.Survival.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Survival.SurvChallenge;
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

public class KillRareMob extends SurvChallenge {

    private final KillMobListener listener;
    private final String name;

    public KillRareMob() {
        Entities entity = Entities.values()[new Random().nextInt(Entities.values().length)];
        name = entity.getNormalName();
        EntityType type = EntityType.valueOf(entity.name());
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            Bukkit.broadcastMessage("§fKill a §4" + name + "§f. You have until the end of the Survival challenge.");
        });
        Bukkit.getPluginManager().registerEvents((listener = new KillMobListener(type)), MissionGameAware.plugin);
    }

    @Override
    public void finish() {
        HandlerList.unregisterAll(listener);
    }

    private enum Entities {
        SILVERFISH("silverfish"), EVOKER("evoker"), GHAST("ghast"), GUARDIAN("guardian"), HUSK("husk"),
        ILLUSIONER("illusioner"), MAGMA_CUBE("magma cube"), MULE("mule"), MUSHROOM_COW("mooshroom"), PANDA("panda"),
        PARROT("parrot"), PHANTOM("phantom"), POLAR_BEAR("polar bear"), RAVAGER("ravager"), STRAY("stray"),
        STRIDER("strider"), WITHER_SKELETON("wither skeleton"), ZOGLIN("zoglin"),
        ZOMBIE_HORSE("zombie horse");

        private final String normalName;

        Entities(String normalName) {
            this.normalName = normalName;
        }

        public String getNormalName() {
            return normalName;
        }
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
