package me.dordsor21.MissionGameAware.twists.impl;

import com.destroystokyo.paper.event.entity.EntityPathfindEvent;
import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.EvilTwist;
import me.dordsor21.MissionGameAware.util.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ManyMobs extends EvilTwist {

    private final Map<Player, List<Entity>> spawned = new HashMap<>();
    private final Set<Entity> entities = new LinkedHashSet<>();
    private MobMoveListener listener;
    private EntityType type;

    @Override
    public void escapePlayer(Player p) {
        for (Entity e : spawned.get(p)) {
            if (e != null && !e.isDead()) {
                e.remove();
            }
        }
        spawned.remove(p);
    }

    @Override
    public void start() {
        System.out.println(this.getClass().getSimpleName() + " twist started.");
        type = EntityType
            .valueOf(Lists.EnemyOkayToSpawn.values()[new Random().nextInt(Lists.EnemyOkayToSpawn.values().length)].name());
        for (Player p : Bukkit.getOnlinePlayers()) {
            ArrayList<Entity> pentities = new ArrayList<>();
            for (int x = -6; x <= 6; x += 6) {
                for (int z = -6; z <= 6; z += 6) {
                    int y = 8;
                    while (y > 0 && p.getWorld().getBlockAt(x, p.getLocation().getBlockY() + y - 1, z).getType().isAir()) {
                        y--;
                    }
                    Location l = p.getLocation().clone().add(x, y, z);
                    Entity e = p.getWorld().spawnEntity(l, type);
                    entities.add(e);
                    pentities.add(e);
                }
            }
            spawned.put(p, pentities);
        }
        Bukkit.getPluginManager().registerEvents(listener = new MobMoveListener(), MissionGameAware.plugin);
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, () -> HandlerList.unregisterAll(listener), 5 * 20L);
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 10 * 20L);
    }

    @Override
    public void cancel() {
        if (listener != null) {
            HandlerList.unregisterAll(listener);
        }
        super.cancel();
    }

    private class MobMoveListener implements Listener {

        @EventHandler
        public void onMove(EntityPathfindEvent e) {
            if (e.getEntityType() != type) {
                return;
            }
            if (entities.contains(e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }
}
