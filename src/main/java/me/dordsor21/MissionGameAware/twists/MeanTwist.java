package me.dordsor21.MissionGameAware.twists;

public abstract class MeanTwist extends Twist {

    @Override
    public final Type getType() {
        return Type.MEAN;
    }

    public static final class NullTwist extends MeanTwist {
        @Override
        public void start() {
        }

        @Override
        public boolean isComplete() {
            return true;
        }
    }

}
