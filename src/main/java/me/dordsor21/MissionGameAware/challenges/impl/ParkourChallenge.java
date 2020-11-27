package me.dordsor21.MissionGameAware.challenges.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Challenge;
import me.dordsor21.MissionGameAware.twists.Twist;
import me.dordsor21.MissionGameAware.twists.impl.Bees;
import me.dordsor21.MissionGameAware.twists.impl.Blindness;
import me.dordsor21.MissionGameAware.twists.impl.Cookies;
import me.dordsor21.MissionGameAware.twists.impl.FakeNuke;
import me.dordsor21.MissionGameAware.twists.impl.HangOn;
import me.dordsor21.MissionGameAware.twists.impl.KittyCannonEract;
import me.dordsor21.MissionGameAware.twists.impl.Lightning;
import me.dordsor21.MissionGameAware.twists.impl.LookDown;
import me.dordsor21.MissionGameAware.twists.impl.LookUp;
import me.dordsor21.MissionGameAware.twists.impl.Nausea;
import me.dordsor21.MissionGameAware.twists.impl.NightDay;
import me.dordsor21.MissionGameAware.twists.impl.PumpkinHead;
import me.dordsor21.MissionGameAware.twists.impl.Speed02;
import me.dordsor21.MissionGameAware.twists.impl.Speed10;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class ParkourChallenge extends Challenge {
    private static final List<Supplier<Twist>> twists = Collections.unmodifiableList(Arrays
        .asList(Blindness::new, Bees::new, Cookies::new, FakeNuke::new, HangOn::new, KittyCannonEract::new, Lightning::new,
            LookDown::new, LookUp::new, Nausea::new, PumpkinHead::new, Speed02::new, Speed10::new, NightDay::new));
    private boolean running = false;

    @Override
    public Type getType() {
        return Type.PARKOUR;
    }

    @Override
    public void stop() {
        this.running = false;
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void run() {
        this.running = true;
        Random r = new Random();
        while (MissionGameAware.queueTwists.getAndDecrement() > 0) {
            MissionGameAware.plugin.getTwistLocks().queueTwist(twists.get(r.nextInt(twists.size())).get());
        }
    }

    public List<Supplier<Twist>> getTwists() {
        return twists;
    }
}
