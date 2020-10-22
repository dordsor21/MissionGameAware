package me.dordsor21.MissionGameAware.twists;

import me.dordsor21.MissionGameAware.util.TwistLocks;

public abstract class NiceTwist extends Twist {

    @Override
    public final Type getType() {
        return Type.NICE;
    }

    public static final class NullTwist extends NiceTwist {
        @Override
        public void start(TwistLocks twistLocks) {
        }

        @Override
        public boolean isComplete() {
            return true;
        }
    }

}
