package me.dordsor21.MissionGameAware.twists;

import org.bukkit.entity.Player;

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
        public void cancel() {
        }

        @Override
        public boolean isComplete() {
            return true;
        }

        @Override
        public void escapePlayer(Player p) {

        }
    }

}
