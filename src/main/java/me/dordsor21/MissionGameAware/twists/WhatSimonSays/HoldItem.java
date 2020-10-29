package me.dordsor21.MissionGameAware.twists.WhatSimonSays;

import me.dordsor21.MissionGameAware.MissionGameAware;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.Random;

public class HoldItem implements WhatSimonSays {

    private final Material item;

    public HoldItem() {
        Material[] mats = Material.values();
        Random r = new Random();
        int i = r.nextInt(mats.length);
        Material mat = mats[i];
        while (mat.isAir() || !mat.isBlock()) {
            i = r.nextInt(mats.length);
            mat = mats[i];
        }
        this.item = mat;
    }

    @Override
    public String getMessage() {
        return "Quick... hold some " + item.name().toLowerCase() + "!";
    }

    @Override
    public Thread doIt(final boolean isSimonSaying) {
        return new Thread(() -> {
            try {
                Thread.sleep(3000L);
                Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&l3"), "", 5, 10, 5);
                    }
                });
                Thread.sleep(1000L);
                Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&l2"), "", 5, 10, 5);
                    }
                });
                Thread.sleep(1000L);
                Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&l1"), "", 5, 10, 5);
                    }
                });
                Thread.sleep(1000L);
                Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.getInventory().getItemInMainHand().getType() != item && p.getInventory().getItemInOffHand().getType() != item) {
                            funBox(p);
                        }
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
