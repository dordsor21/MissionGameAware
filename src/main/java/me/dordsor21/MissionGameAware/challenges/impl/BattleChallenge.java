package me.dordsor21.MissionGameAware.challenges.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Challenge;
import me.dordsor21.MissionGameAware.twists.Twist;
import me.dordsor21.MissionGameAware.twists.impl.Bees;
import me.dordsor21.MissionGameAware.twists.impl.Blindness;
import me.dordsor21.MissionGameAware.twists.impl.Cookies;
import me.dordsor21.MissionGameAware.twists.impl.FakeNuke;
import me.dordsor21.MissionGameAware.twists.impl.HangOn;
import me.dordsor21.MissionGameAware.twists.impl.ItemsGoBye;
import me.dordsor21.MissionGameAware.twists.impl.KittyCannonEract;
import me.dordsor21.MissionGameAware.twists.impl.LaserFocus;
import me.dordsor21.MissionGameAware.twists.impl.Lightning;
import me.dordsor21.MissionGameAware.twists.impl.LookDown;
import me.dordsor21.MissionGameAware.twists.impl.LookUp;
import me.dordsor21.MissionGameAware.twists.impl.Nausea;
import me.dordsor21.MissionGameAware.twists.impl.NightDay;
import me.dordsor21.MissionGameAware.twists.impl.PumpkinHead;
import me.dordsor21.MissionGameAware.twists.impl.Sheep;
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

public class BattleChallenge extends Challenge {

    private static final List<Supplier<Twist>> twists = Collections.unmodifiableList(Arrays
        .asList(Blindness::new, Bees::new, Cookies::new, FakeNuke::new, HangOn::new, ItemsGoBye::new, KittyCannonEract::new,
            LaserFocus::new, Lightning::new, LookDown::new, LookUp::new, Nausea::new, PumpkinHead::new, Sheep::new,
            Speed02::new, Speed10::new, Zoom::new, NightDay::new));
    private static final AtomicInteger descrStage = new AtomicInteger(0);
    private static ScheduledFuture<?> descr;
    private boolean running = false;

    @Override
    public Type getType() {
        return Type.BUILD;
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
        descr = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Describe(), 1L, 5L, TimeUnit.SECONDS);
        Executors.newSingleThreadScheduledExecutor().schedule(this::stop, 8L, TimeUnit.MINUTES);
    }

    private static final class Describe implements Runnable {

        @Override
        public void run() {
            try {
                Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                    switch (descrStage.get()) {
                        case 0:
                            Bukkit.broadcastMessage(
                                "\u00A76Build battles\u00A77, but speedy! You have only \u00A768 minutes \u00A77to create! "
                                    + "Challenge your hand building skills and remember to build to the theme.");
                            break;
                        case 1:
                            Random r = new Random();
                            while (MissionGameAware.queueTwists.getAndDecrement() > 0) {
                                MissionGameAware.plugin.getTwistLocks()
                                    .queueTwist(twists.get(r.nextInt(twists.size())).get());
                            }
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                Bukkit.dispatchCommand(p, "bg join battle");
                            }
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bg forcestart battle");
                            Bukkit.broadcastMessage(
                                "\u00A77You'll be able to vote on each-others builds at the end to see who's the winner!");
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
