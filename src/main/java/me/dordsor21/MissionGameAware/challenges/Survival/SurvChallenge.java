package me.dordsor21.MissionGameAware.challenges.Survival;

public abstract class SurvChallenge {

    public boolean isTimed() {
        return false;
    }

    public boolean isSingle() {
        return false;
    }

    public abstract void finish();
}
