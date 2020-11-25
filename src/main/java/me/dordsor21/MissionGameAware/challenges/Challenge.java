package me.dordsor21.MissionGameAware.challenges;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.Twist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public abstract class Challenge implements Runnable {

    private String name;

    public abstract Type getType();

    public abstract void stop();

    public abstract boolean isRunning();

    public void newTwist() {
        MissionGameAware.plugin.getTwistLocks().queueTwist(getTwists().get(new Random().nextInt(getTwists().size())).get());
    }

    public List<Supplier<Twist>> getTwists() {
        return Collections.unmodifiableList(new ArrayList<>());
    }

    public String getChallengeName() {
        if (name == null) {
            name = getClass().getSimpleName();
        }
        return name;
    }

    public enum Type {
        PARKOUR, BUILD, SURVIVAL
    }
}
