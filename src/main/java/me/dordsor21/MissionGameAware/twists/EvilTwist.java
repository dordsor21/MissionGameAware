package me.dordsor21.MissionGameAware.twists;

import me.dordsor21.MissionGameAware.util.TwistLocks;

public abstract class EvilTwist extends Twist {

    @Override
    public final Type getType() {
        return Type.EVIL;
    }

    public static final class NullTwist extends EvilTwist {
        @Override
        public void start(TwistLocks twistLocks) {
        }

        @Override
        public boolean isComplete() {
            return true;
        }
    }

}
