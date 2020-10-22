package me.dordsor21.MissionGameAware.twists;

import me.dordsor21.MissionGameAware.util.TwistLocks;

public abstract class WeirdTwist extends Twist {

    @Override
    public final Type getType() {
        return Type.WEIRD;
    }

    public static final class NullTwist extends WeirdTwist {
        @Override
        public void start(TwistLocks twistLocks) {
        }

        @Override
        public boolean isComplete() {
            return true;
        }
    }

}
