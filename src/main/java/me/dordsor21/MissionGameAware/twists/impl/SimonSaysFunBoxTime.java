package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.EvilTwist;
import me.dordsor21.MissionGameAware.twists.SoleTwist;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.ChangeDoWhatSimonSays;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.FlyNESW;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.HoldItem;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.Jump;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.LookDown;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.LookNESW;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.LookUp;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.PlaceBlock;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.WhatSimonSays;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class SimonSaysFunBoxTime extends EvilTwist implements SoleTwist {

    public static final List<Player> escaped = new ArrayList<>();
    private static final List<Supplier<WhatSimonSays>> whatSimonsSayses = Arrays
        .asList(Jump::new, FlyNESW::new, Jump::new, LookDown::new, LookNESW::new, LookUp::new, PlaceBlock::new,
            HoldItem::new, ChangeDoWhatSimonSays::new);
    public static Location funBoxLoc = null;
    private boolean doWhatSimonSays = true;
    private SimonSaysTimer t = null;
    private BukkitTask r = null;
    private boolean cancelled = false;

    @Override
    public void escapePlayer(Player p) {
        escaped.add(p);
        p.removePotionEffect(PotionEffectType.BLINDNESS);
    }

    @Override
    public void start() {
        System.out.println(this.getClass().getSimpleName() + " twist started.");
        funBoxLoc = new Location(Bukkit.getWorld("world"), 100, 100, 100);
        escaped.clear();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (escaped.contains(p)) {
                continue;
            }
            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "Simon says it's... &5&lFUNBOX TIME"),
                ChatColor.translateAlternateColorCodes('&', "Do what Simon says or meet the &5&lFUNBOX!"), 0, 40, 10);
        }
        t = new SimonSaysTimer(doWhatSimonSays, SimonSaysFunBoxTime.this);
        r = Bukkit.getScheduler().runTaskLaterAsynchronously(MissionGameAware.plugin, t, 5 * 20L);
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 60 * 20L);
    }

    @Override
    public void cancel() {
        escaped.clear();
        if (t != null) {
            t.cancel();
        }
        if (r != null) {
            r.cancel();
        }
        cancelled = true;
        super.cancel();
    }

    @Override
    public void complete() {
        escaped.clear();
        if (t != null) {
            t.cancel();
        }
        super.complete();
    }

    private void setShouldDoSimonSaying(boolean doWhatSimonSays) {
        this.doWhatSimonSays = doWhatSimonSays;
    }

    private boolean shouldDoWhatSimonSays() {
        return doWhatSimonSays;
    }

    private void setTask(SimonSaysTimer t) {
        if (cancelled) {
            t.cancel();
            return;
        }
        this.t = t;
    }

    private static final class SimonSaysTimer implements Runnable {

        private static final String prefix = ChatColor.translateAlternateColorCodes('&', "&4Simon says: &f");
        private final SimonSaysFunBoxTime twist;
        private final boolean doWhatSimonSays;
        private Thread t;
        private boolean cancelled = false;

        public SimonSaysTimer(boolean doWhatSimonSays, SimonSaysFunBoxTime twist) {
            this.twist = twist;
            this.doWhatSimonSays = doWhatSimonSays;
        }

        private void cancel() {
            if (t != null) {
                t.stop();
            }
            cancelled = true;
        }

        @Override
        public void run() {
            final WhatSimonSays whatSimonSays =
                whatSimonsSayses.get((int) Math.floor(Math.random() * whatSimonsSayses.size())).get();
            if (cancelled) {
                return;
            }
            if (whatSimonSays instanceof ChangeDoWhatSimonSays) {
                Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                    if (doWhatSimonSays) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (escaped.contains(p)) {
                                continue;
                            }
                            p.sendTitle(
                                ChatColor.translateAlternateColorCodes('&', "&4Simon says: &fDon't do what he says!"), "", 0,
                                40, 10);
                        }
                    } else {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (escaped.contains(p)) {
                                continue;
                            }
                            p.sendTitle("Do what Simon says!", "", 0, 40, 10);
                        }
                    }
                });
                twist.setShouldDoSimonSaying(!doWhatSimonSays);
                if (!cancelled) {
                    SimonSaysTimer timer = new SimonSaysTimer(twist.shouldDoWhatSimonSays(), twist);
                    twist.setTask(timer);
                    Bukkit.getScheduler().runTaskLaterAsynchronously(MissionGameAware.plugin, timer,
                        (5 + (long) Math.floor(Math.random() * 5)) * 20L);
                }
                return;
            }
            final boolean doWhatMessageSays = Math.random() > 0.5;
            Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (escaped.contains(p)) {
                        continue;
                    }
                    p.sendTitle((doWhatMessageSays == doWhatSimonSays ? prefix : "") + whatSimonSays.getMessage(), "", 0, 40,
                        10);
                    p.playSound(p.getEyeLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                }
            });
            t = whatSimonSays.doIt(doWhatMessageSays);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (!cancelled) {
                    SimonSaysTimer timer = new SimonSaysTimer(twist.shouldDoWhatSimonSays(), twist);
                    twist.setTask(timer);
                    Bukkit.getScheduler().runTaskLaterAsynchronously(MissionGameAware.plugin, timer,
                        (2 + (long) Math.floor(Math.random() * 5)) * 20L);
                }
            }
        }
    }
}
