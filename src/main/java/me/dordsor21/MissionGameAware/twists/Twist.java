package me.dordsor21.MissionGameAware.twists;

import me.dordsor21.MissionGameAware.util.TwistLocks;

public abstract class Twist {

    private boolean complete = false;

    public boolean isComplete() {
        return complete;
    }

    public void complete() {
        complete = true;
    }

    public abstract void start(TwistLocks twistLocks);

    public abstract Type getType();

    public enum Type {
        NICE, MEAN, EVIL, WEIRD;
    }
}
