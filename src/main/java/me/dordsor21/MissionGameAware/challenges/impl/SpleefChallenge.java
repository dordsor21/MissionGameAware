package me.dordsor21.MissionGameAware.challenges.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Challenge;
import me.dordsor21.MissionGameAware.twists.Twist;
import me.dordsor21.MissionGameAware.twists.impl.Blindness;
import me.dordsor21.MissionGameAware.twists.impl.Cookies;
import me.dordsor21.MissionGameAware.twists.impl.FakeNuke;
import me.dordsor21.MissionGameAware.twists.impl.ItemsGoBye;
import me.dordsor21.MissionGameAware.twists.impl.LaserFocus;
import me.dordsor21.MissionGameAware.twists.impl.Nausea;
import me.dordsor21.MissionGameAware.twists.impl.NightDay;
import me.dordsor21.MissionGameAware.twists.impl.PumpkinHead;
import me.dordsor21.MissionGameAware.twists.impl.Speed02;
import me.dordsor21.MissionGameAware.twists.impl.Speed10;
import me.dordsor21.MissionGameAware.twists.impl.Zoom;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class SpleefChallenge extends Challenge {

    private static final List<Supplier<Twist>> twists = Collections.unmodifiableList(Arrays
        .asList(Blindness::new, Cookies::new, FakeNuke::new, ItemsGoBye::new, LaserFocus::new, Nausea::new, PumpkinHead::new,
            Speed02::new, Speed10::new, Zoom::new, NightDay::new));
    private static final AtomicInteger descrStage = new AtomicInteger(0);
    private static ScheduledFuture<?> descr;
    private boolean running = false;

    @Override
    public Type getType() {
        return Type.MINIGAME;
    }

    @Override
    public void stop() {
        this.running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    public List<Supplier<Twist>> getTwists() {
        return twists;
    }

    @Override
    public void run() {
        this.running = true;
        Random r = new Random();
        while (MissionGameAware.queueTwists.getAndDecrement() > 0) {
            MissionGameAware.plugin.getTwistLocks().queueTwist(twists.get(r.nextInt(twists.size())).get());
        }
        descr = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Describe(), 1L, 5L, TimeUnit.SECONDS);
    }

    private static final class Describe implements Runnable {

        @Override
        public void run() {
            try {
                Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                    switch (descrStage.get()) {
                        case 0:
                            Bukkit.broadcastMessage(
                                "\u00A77A classic and in a familiar setting, our \u00A7fspleef\u00A77 arena is an edited version of our \u00A762016 Buildcon hub\u00A77!");
                            break;
                        case 1:
                            Bukkit.broadcastMessage("\u00A77Prepare for some amped up spleef with our \u00A74twists...");
                            break;
                        case 2:
                            Random r = new Random();
                            while (MissionGameAware.queueTwists.getAndDecrement() > 0) {
                                MissionGameAware.plugin.getTwistLocks()
                                    .queueTwist(twists.get(r.nextInt(twists.size())).get());
                            }
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                Bukkit.dispatchCommand(p, "spleef join spleef");
                            }
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spleef forcestart spleef");
                            descr.cancel(false);
                            break;
                    }
                    descrStage.incrementAndGet();
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
