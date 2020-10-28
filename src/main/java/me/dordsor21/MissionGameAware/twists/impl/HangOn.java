package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.twists.EvilTwist;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class HangOn extends EvilTwist {

    @Override
    public void start() {
        Map<Player, Location> locs = new HashMap<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "Hang on for a second..."), "", 0, 70, 20);
            locs.put(p, p.getLocation().clone());
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        locs.forEach(Entity::teleport);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        locs.forEach(Entity::teleport);
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        locs.forEach((p, l) -> {
            p.teleport(l);
            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "...cheers"), "", 0, 70, 20);
        });
        this.complete();
    }
}
