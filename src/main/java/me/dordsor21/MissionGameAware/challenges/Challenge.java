package me.dordsor21.MissionGameAware.challenges;

import me.dordsor21.MissionGameAware.twists.Twist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public abstract class Challenge {

    private String name;

    public abstract Type getType();

    public List<Supplier<Twist>> getTwists() {
        return Collections.unmodifiableList(new ArrayList<>());
    }

    public String getChallengeName() {
        if (name == null) {
            name = getClass().getSimpleName();
        }
        return name;
    }

    public enum Type {
        PARKOUR;
    }
}
