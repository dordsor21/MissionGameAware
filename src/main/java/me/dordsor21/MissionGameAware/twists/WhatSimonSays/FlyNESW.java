package me.dordsor21.MissionGameAware.twists.WhatSimonSays;

import me.dordsor21.MissionGameAware.MissionGameAware;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.LinkedList;

public class FlyNESW implements WhatSimonSays {

    private static final LinkedList<Player> playersWhoDid = new LinkedList<>();

    private final Direction dir;

    public FlyNESW() {
        this.dir = Direction.of((int) Math.floor(Math.random() * 4));
    }

    @Override
    public String getMessage() {
        return "Fly " + dir.name().toLowerCase() + " in 3 seconds' time!";
    }

    @Override
    public Thread doIt(boolean value) {
        return new Thread(() -> {
            try {
                Listener listener = new FlyNESWListener(dir);
                Bukkit.getPluginManager().registerEvents(listener, MissionGameAware.plugin);
                Thread.sleep(4500L);
                HandlerList.unregisterAll(listener);
                checkListAgainstShouldContain(playersWhoDid, value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    enum Direction {
        NORTH, EAST, SOUTH, WEST;

        static Direction of(int i) {
            switch (i) {
                case 1:
                    return Direction.EAST;
                case 2:
                    return Direction.SOUTH;
                case 3:
                    return Direction.WEST;
                case 0:
                default:
                    return Direction.NORTH;
            }
        }
    }


    private static final class FlyNESWListener implements Listener {

        private final Direction dir;

        private FlyNESWListener(Direction dir) {
            this.dir = dir;
        }

        @EventHandler
        public void moveEvent(PlayerMoveEvent e) {
            Location l1 = e.getFrom();
            Location l2 = e.getTo();
            switch (dir) {
                case NORTH:
                    if (l2 != null && l2.getZ() < l1.getZ()) {
                        playersWhoDid.add(e.getPlayer());
                    } else {
                        playersWhoDid.remove(e.getPlayer());
                    }
                    return;
                case EAST:
                    if (l2 != null && l2.getX() > l1.getX()) {
                        playersWhoDid.add(e.getPlayer());
                    } else {
                        playersWhoDid.remove(e.getPlayer());
                    }
                    return;
                case SOUTH:
                    if (l2 != null && l2.getZ() > l1.getZ()) {
                        playersWhoDid.add(e.getPlayer());
                    } else {
                        playersWhoDid.remove(e.getPlayer());
                    }
                    return;
                case WEST:
                    if (l2 != null && l2.getX() < l1.getX()) {
                        playersWhoDid.add(e.getPlayer());
                    } else {
                        playersWhoDid.remove(e.getPlayer());
                    }
            }
        }
    }
}
