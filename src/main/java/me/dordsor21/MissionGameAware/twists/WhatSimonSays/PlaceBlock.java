package me.dordsor21.MissionGameAware.twists.WhatSimonSays;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.impl.SimonSaysFunBoxTime;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.LinkedList;
import java.util.Random;

public class PlaceBlock implements WhatSimonSays {

    private static final LinkedList<Player> playersWhoPlaced = new LinkedList<>();
    private final Material block;

    public PlaceBlock() {
        Material[] mats = Material.values();
        Random r = new Random();
        int i = r.nextInt(mats.length);
        Material mat = mats[i];
        while (mat.isAir() || !mat.isBlock()) {
            i = r.nextInt(mats.length);
            mat = mats[i];
        }
        this.block = mat;
        playersWhoPlaced.clear();
    }

    @Override
    public String getMessage() {
        return "Quick... place some " + block.name().toLowerCase().replace('_', ' ') + "!";
    }

    @Override
    public Thread doIt(final boolean value) {
        return new Thread(() -> {
            try {
                PlaceBlockListener listener = new PlaceBlockListener(block);
                Bukkit.getPluginManager().registerEvents(listener, MissionGameAware.plugin);
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
                HandlerList.unregisterAll(listener);
                checkListAgainstShouldContain(playersWhoPlaced, value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static final class PlaceBlockListener implements Listener {

        private final Material block;

        private PlaceBlockListener(Material block) {
            this.block = block;
        }

        @EventHandler
        public void onBlockPlace(BlockPlaceEvent e) {
            if (e.getBlockPlaced().getType() != block) {
                return;
            }
            playersWhoPlaced.add(e.getPlayer());
        }
    }
}
