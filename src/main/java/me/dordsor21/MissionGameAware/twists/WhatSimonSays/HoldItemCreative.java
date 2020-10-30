package me.dordsor21.MissionGameAware.twists.WhatSimonSays;

import me.dordsor21.MissionGameAware.MissionGameAware;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HoldItemCreative implements WhatSimonSays {

    private final Material item;

    public HoldItemCreative() {
        Material[] mats = Material.values();
        Random r = new Random();
        int i = r.nextInt(mats.length);
        Material mat = mats[i];
        while (mat.isAir() || !mat.isItem()) {
            i = r.nextInt(mats.length);
            mat = mats[i];
        }
        this.item = mat;
    }

    @Override
    public String getMessage() {
        return "Quick... hold some " + item.name().toLowerCase().replace('_', ' ') + "!";
    }

    @Override
    public Thread doIt(final boolean value) {
        return new Thread(() -> {
            try {
                Thread.sleep(5000L);
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
                List<Player> fail = new ArrayList<>();
                List<Player> pass = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if ((p.getInventory().getItemInMainHand().getType() == item || p.getInventory().getItemInOffHand().getType() == item) != value) {
                        fail.add(p);
                    } else {
                        pass.add(p);
                    }
                }
                funBox(fail);
                good(pass);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
