package me.dordsor21.MissionGameAware.twists;

import org.bukkit.entity.Player;

public abstract class WeirdTwist extends Twist {

    @Override
    public final Type getType() {
        return Type.WEIRD;
    }

    public static final class NullTwist extends WeirdTwist {
        @Override
        public void start() {
        }

        @Override
        public void cancel() {
        }

        @Override
        public void escapePlayer(Player p) {

        }

        @Override
        public boolean isComplete() {
            return true;
        }
    }

}
