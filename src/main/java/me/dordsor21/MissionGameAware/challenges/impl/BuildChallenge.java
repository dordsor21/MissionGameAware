package me.dordsor21.MissionGameAware.challenges.impl;

import me.dordsor21.MissionGameAware.twists.Twist;
import me.dordsor21.MissionGameAware.twists.impl.Blindness;
import me.dordsor21.MissionGameAware.twists.impl.BlindnessTeleport;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class BuildChallenge {

    private static final List<Supplier<Twist>> twists = Collections.unmodifiableList(Arrays.asList(BlindnessTeleport::new, Blindness::new));

    public BuildChallenge() {

    }

    public static List<Supplier<Twist>> getTwists() {
        return twists;
    }
}
