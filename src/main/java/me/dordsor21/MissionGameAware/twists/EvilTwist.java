package me.dordsor21.MissionGameAware.twists;

import org.bukkit.entity.Player;

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
