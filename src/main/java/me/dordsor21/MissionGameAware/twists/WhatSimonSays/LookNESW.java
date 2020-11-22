package me.dordsor21.MissionGameAware.twists.WhatSimonSays;

import me.dordsor21.MissionGameAware.twists.impl.SimonSaysFunBoxTime;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LookNESW implements WhatSimonSays {

    final private Direction dir;

    public LookNESW() {
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
                Thread.sleep(6000L);
                List<Player> fail = new ArrayList<>();
                List<Player> pass = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (SimonSaysFunBoxTime.escaped.contains(p)) {
                        continue;
                    }
                    float yaw = p.getLocation().getYaw();
                    switch (dir) {
                        case NORTH:
                            System.out.println(p.getLocation().getYaw());
                            if (yaw <= 225 && yaw > 135 != value) {
                                fail.add(p);
                            } else {
                                pass.add(p);
                            }
                            return;
                        case EAST:
                            if (yaw <= 315 && yaw > 225 != value) {
                                fail.add(p);
                            } else {
                                pass.add(p);
                            }
                            return;
                        case SOUTH:
                            if (yaw <= 45 && yaw > 315 != value) {
                                fail.add(p);
                            } else {
                                pass.add(p);
                            }
                            return;
                        case WEST:
                            if (yaw <= 135 && yaw > 45 != value) {
                                fail.add(p);
                            } else {
                                pass.add(p);
                            }
                            return;
                    }
                }
                funBox(fail);
                good(pass);
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
