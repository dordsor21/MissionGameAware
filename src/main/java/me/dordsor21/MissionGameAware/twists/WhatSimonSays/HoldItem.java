package me.dordsor21.MissionGameAware.twists.WhatSimonSays;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.impl.SimonSaysFunBoxTime;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HoldItem implements WhatSimonSays {

    private final Material item;
    private final Material[] list = new Material[9];

    public HoldItem() {
        Material[] mats = Material.values();
        Random r = new Random();
        int i = r.nextInt(mats.length);
        for (int n = 0; n < 9; n++) {
            Material mat = mats[i];
            while (mat.isAir() || !mat.isItem()) {
                i = r.nextInt(mats.length);
                mat = mats[i];
            }
            list[n] = mat;
        }
        this.item = list[(int) Math.floor(Math.random() * 9)];
    }

    @Override
    public String getMessage() {
        return "Quick... hold some " + item.name().toLowerCase().replace('_', ' ') + "!";
    }

    @Override
    public Thread doIt(final boolean value) {
        return new Thread(() -> {
            try {
                Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (SimonSaysFunBoxTime.escaped.contains(p)) {
                            continue;
                        }
                        ItemStack[] items = p.getInventory().getContents();
                        for (int i = 0; i < 9; i++) {
                            items[i] = new ItemStack(list[i]);
                        }
                        p.getInventory().setContents(items);
                    }
                });
                Thread.sleep(5000L);
                Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (SimonSaysFunBoxTime.escaped.contains(p)) {
                            continue;
                        }
                        p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&l3"), "", 5, 10, 5);
                    }
                });
                Thread.sleep(1000L);
                Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (SimonSaysFunBoxTime.escaped.contains(p)) {
                            continue;
                        }
                        p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&l2"), "", 5, 10, 5);
                    }
                });
                Thread.sleep(1000L);
                Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (SimonSaysFunBoxTime.escaped.contains(p)) {
                            continue;
                        }
                        p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&l1"), "", 5, 10, 5);
                    }
                });
                Thread.sleep(1000L);
                List<Player> fail = new ArrayList<>();
                List<Player> pass = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (SimonSaysFunBoxTime.escaped.contains(p)) {
                        continue;
                    }
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
