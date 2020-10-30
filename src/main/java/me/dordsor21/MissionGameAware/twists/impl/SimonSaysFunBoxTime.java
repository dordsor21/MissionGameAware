package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.EvilTwist;
import me.dordsor21.MissionGameAware.twists.SoleTwist;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.ChangeDoWhatSimonSays;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.FlyNESW;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.FlyUp;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.HoldItem;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.LookDown;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.LookNESW;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.LookUp;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.PlaceBlock;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.WhatSimonSays;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class SimonSaysFunBoxTime extends EvilTwist implements SoleTwist {

    private static final List<Supplier<WhatSimonSays>> whatSimonsSayses = Arrays
        .asList(FlyUp::new, FlyNESW::new, FlyUp::new, LookDown::new, LookNESW::new, LookUp::new, PlaceBlock::new, HoldItem::new,
            ChangeDoWhatSimonSays::new);
    private boolean doWhatSimonSays = true;
    private BukkitTask t = null;

    @Override
    public void start() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "Simon says it's... &5&lFUNBOX TIME"),
                ChatColor.translateAlternateColorCodes('&', "Do what Simon says or meet the &5&lFUNBOX!"), 0, 40, 10);
        }
        t = Bukkit.getScheduler().runTaskAsynchronously(MissionGameAware.plugin, new SimonSaysTimer(doWhatSimonSays, SimonSaysFunBoxTime.this));
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 60 * 20L);
    }

    @Override
    public void cancel() {
        if (t != null) {
            t.cancel();
        }
        super.cancel();
    }

    @Override
    public void complete() {
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

    private void setTask(BukkitTask t) {
        this.t = t;
    }

    private static final class SimonSaysTimer implements Runnable {

        private static final String prefix = ChatColor.translateAlternateColorCodes('&', "&4Simon says: &f");
        private final SimonSaysFunBoxTime twist;
        private final boolean doWhatSimonSays;

        public SimonSaysTimer(boolean doWhatSimonSays, SimonSaysFunBoxTime twist) {
            this.twist = twist;
            this.doWhatSimonSays = doWhatSimonSays;
        }

        @Override
        public void run() {
            WhatSimonSays whatSimonSays = whatSimonsSayses.get((int) Math.floor(Math.random() * whatSimonsSayses.size())).get();
            if (whatSimonSays instanceof ChangeDoWhatSimonSays) {
                twist.setShouldDoSimonSaying(!doWhatSimonSays);
                return;
            }
            final boolean doWhatMessageSays = Math.random() > 0.5;
            Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendTitle(doWhatMessageSays == doWhatSimonSays ? prefix : "" + whatSimonSays.getMessage(), "", 0, 40, 10);
                    p.playSound(p.getEyeLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
                }
            });
            whatSimonSays.doIt(doWhatMessageSays);
            twist.setTask(Bukkit.getScheduler()
                .runTaskLaterAsynchronously(MissionGameAware.plugin, new SimonSaysTimer(twist.shouldDoWhatSimonSays(), twist),
                    5 * 20L + (long) Math.floor(Math.random() * 10)));
        }
    }
}
