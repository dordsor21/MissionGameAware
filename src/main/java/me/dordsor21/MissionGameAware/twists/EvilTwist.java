package me.dordsor21.MissionGameAware.twists;

public abstract class EvilTwist extends Twist {

    @Override
    public final Type getType() {
        return Type.EVIL;
    }

    public static final class NullTwist extends EvilTwist {
        @Override
        public void start() {
        }

        @Override
        public boolean isComplete() {
            return true;
        }
    }

}
