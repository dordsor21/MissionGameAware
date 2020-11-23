package me.dordsor21.MissionGameAware.util;

import me.dordsor21.MissionGameAware.challenges.Challenge;

public class ChallengeHandler {
    private Challenge.Type type;

    public Challenge.Type getType() {
        return type;
    }

    public void setType(Challenge.Type type) {
        this.type = type;
    }

    public void start(Challenge challenge) {
        this.setType(challenge.getType());
        challenge.run();
    }
}
