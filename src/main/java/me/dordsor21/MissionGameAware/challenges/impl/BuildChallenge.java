package me.dordsor21.MissionGameAware.challenges.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Challenge;
import me.dordsor21.MissionGameAware.twists.Twist;
import me.dordsor21.MissionGameAware.twists.impl.Bees;
import me.dordsor21.MissionGameAware.twists.impl.Blindness;
import me.dordsor21.MissionGameAware.twists.impl.BlindnessTeleport;
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
import me.dordsor21.MissionGameAware.twists.impl.RandomTeleport;
import me.dordsor21.MissionGameAware.twists.impl.Sheep;
import me.dordsor21.MissionGameAware.twists.impl.SimonSaysFunBoxTime;
import me.dordsor21.MissionGameAware.twists.impl.Speed02;
import me.dordsor21.MissionGameAware.twists.impl.Speed10;
import me.dordsor21.MissionGameAware.twists.impl.TeleportAbout;
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

public class BuildChallenge extends Challenge {

    private static final List<Supplier<Twist>> twists = Collections.unmodifiableList(Arrays
        .asList(BlindnessTeleport::new, Blindness::new, Bees::new, Cookies::new, FakeNuke::new, HangOn::new, ItemsGoBye::new,
            KittyCannonEract::new, LaserFocus::new, Lightning::new, LookDown::new, LookUp::new, Nausea::new,
            PumpkinHead::new, RandomTeleport::new, Sheep::new, SimonSaysFunBoxTime::new, Speed02::new, Speed10::new,
            TeleportAbout::new, Zoom::new, NightDay::new));
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
    }

    private static final class Describe implements Runnable {

        @Override
        public void run() {
            try {
                Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                    switch (descrStage.get()) {
                        case 0:
                            Bukkit.broadcastMessage(
                                "\u00A76Welcome all\u00A77 to your first \u00A7cchallenge\u00A77; a creative build to ease you into the day.");
                            break;
                        case 1:
                            Bukkit.broadcastMessage(
                                "\u00A77In your solos or duos, renovate the castle given to you in any style or way you'd like.");
                            break;
                        case 2:
                            Bukkit.broadcastMessage(
                                "\u00A77Along the way we'll throw suggestions of what you can add, you have access to \u00A7cWorld Edit\u00A77 to help you too!");
                            break;
                        case 3:
                            Bukkit.broadcastMessage(
                                "\u00A77Oh and beware of the \u00A74twists\u00A77 to make your building session that much more challenging!");
                            break;
                        case 4:
                            Random r = new Random();
                            while (MissionGameAware.queueTwists.getAndDecrement() > 0) {
                                MissionGameAware.plugin.getTwistLocks()
                                    .queueTwist(twists.get(r.nextInt(twists.size())).get());
                            }
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                Bukkit.dispatchCommand(p, "plot home");
                                Bukkit.dispatchCommand(p, "plot list shared world");
                                p.sendMessage("Didn't get teleported to a plot? Hit the [1] in the message above!");
                                p.sendMessage("Didn't get a plot in your list, shout!");
                            }
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
