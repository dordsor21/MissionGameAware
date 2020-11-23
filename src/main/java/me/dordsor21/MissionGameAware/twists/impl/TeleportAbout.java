package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.EvilTwist;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class TeleportAbout extends EvilTwist {

    private final HashMap<Player, Location> initial = new HashMap<>();
    private BukkitTask t;

    @Override
    public void escapePlayer(Player p) {
        //cannot lol
    }

    @Override
    public void start() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            initial.put(p, p.getLocation().clone());
        }
        List<Map.Entry<Player, Location>> locs = new ArrayList<>(initial.entrySet());
        Random r = new Random();
        for (Player p : Bukkit.getOnlinePlayers()) {
            Map.Entry<Player, Location> entry = locs.remove(r.nextInt(locs.size()));
            p.teleport(entry.getValue());
            p.sendTitle(ChatColor
                    .translateAlternateColorCodes('&', "&3Wondering where &c" + entry.getKey().getName() + "&3 is? Here!"), "",
                0, 70, 20);
        }
        t = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, () -> initial.forEach(Player::teleport), 100L);
        t = Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 101L);
    }

    @Override
    public void cancel() {
        if (t != null) {
            t.cancel();
        }
        super.cancel();
    }
}
