package me.dordsor21.MissionGameAware.twists;

import me.dordsor21.MissionGameAware.util.TwistLocks;

public abstract class MeanTwist extends Twist {

    @Override
    public final Type getType() {
        return Type.MEAN;
    }

    public static final class NullTwist extends MeanTwist {
        @Override
        public void start(TwistLocks twistLocks) {
        }

        @Override
        public boolean isComplete() {
            return true;
        }
    }

}
