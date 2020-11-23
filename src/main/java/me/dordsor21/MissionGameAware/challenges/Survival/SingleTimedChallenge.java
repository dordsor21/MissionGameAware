package me.dordsor21.MissionGameAware.challenges.Survival;

public abstract class SingleTimedChallenge extends SingleChallenge {
    @Override
    public boolean isTimed() {
        return true;
    }

    @Override
    public boolean isSingle() {
        return true;
    }
}
