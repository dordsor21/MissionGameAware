package me.dordsor21.MissionGameAware.twists;

import me.dordsor21.MissionGameAware.MissionGameAware;

public abstract class Twist implements AutoCloseable {

    private boolean complete = false;
    private String name = null;

    public boolean isComplete() {
        return complete;
    }

    public void complete() {
        complete = true;
        MissionGameAware.plugin.getLogger().info(getTwistName() + " complete.");
        MissionGameAware.plugin.getTwistLocks().notifyTwistEnd(this instanceof SoleTwist);
    }

    public abstract void start();

    public abstract Type getType();

    public String getTwistName() {
        if (name == null) {
            name = getClass().getSimpleName();
        }
        return name;
    }

    @Override
    public void close() {
        complete();
    }

    public enum Type {
        NICE, MEAN, EVIL, WEIRD;
    }
}
