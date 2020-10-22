package me.dordsor21.MissionGameAware.twists;

public abstract class NiceTwist extends Twist {

    @Override
    public final Type getType() {
        return Type.NICE;
    }

    public static final class NullTwist extends NiceTwist {
        @Override
        public void start() {
        }

        @Override
        public boolean isComplete() {
            return true;
        }
    }

}
