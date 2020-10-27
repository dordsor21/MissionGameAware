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
        MissionGameAware.plugin.getTwistLocks().notifyTwistEnd(this instanceof SoleTwist, true);
    }

    public abstract void start();

    public void cancel() {
        complete = true;
    }

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

        public static Type getValue(String s) {
            switch (s.toLowerCase()) {
                case "nice":
                    return Type.NICE;
                case "mean":
                    return Type.MEAN;
                case "evil":
                    return Type.EVIL;
                case "weird":
                    return Type.WEIRD;
                default:
                    return null;
            }
        }
    }
}
