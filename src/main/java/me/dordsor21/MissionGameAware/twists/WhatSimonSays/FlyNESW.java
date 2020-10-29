package me.dordsor21.MissionGameAware.twists.WhatSimonSays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FlyNESW implements WhatSimonSays {

    final private Direction dir;

    public FlyNESW() {
        this.dir = Direction.of((int) Math.floor(Math.random() * 4));
    }

    @Override
    public String getMessage() {
        return "Fly " + dir.name().toLowerCase() + " in 3 seconds' time!";
    }

    @Override
    public Thread doIt(boolean isSimonSaying) {
        return new Thread(() -> {
            try {
                Thread.sleep(3000L);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    switch (dir) {
                        case NORTH:
                            if (p.getVelocity().getZ() < 0 != isSimonSaying) {
                                funBox(p);
                            }
                            return;
                        case EAST:
                            if (p.getVelocity().getX() > 0 != isSimonSaying) {
                                funBox(p);
                            }
                            return;
                        case SOUTH:
                            if (p.getVelocity().getZ() > 0 != isSimonSaying) {
                                funBox(p);
                            }
                            return;
                        case WEST:
                            if (p.getVelocity().getX() < 0 != isSimonSaying) {
                                funBox(p);
                            }
                            return;
                    }
                }
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
}
