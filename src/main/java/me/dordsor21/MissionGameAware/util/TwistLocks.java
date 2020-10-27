package me.dordsor21.MissionGameAware.util;

import me.dordsor21.MissionGameAware.twists.EvilTwist;
import me.dordsor21.MissionGameAware.twists.MeanTwist;
import me.dordsor21.MissionGameAware.twists.NiceTwist;
import me.dordsor21.MissionGameAware.twists.SoleTwist;
import me.dordsor21.MissionGameAware.twists.Twist;
import me.dordsor21.MissionGameAware.twists.WeirdTwist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwistLocks {

    private final ArrayList<NiceTwist> niceTwistQueue = new ArrayList<>();
    private final ArrayList<MeanTwist> meanTwistQueue = new ArrayList<>();
    private final ArrayList<EvilTwist> evilTwistQueue = new ArrayList<>();
    private final ArrayList<WeirdTwist> weirdTwistQueue = new ArrayList<>();
    private final ArrayList<SoleTwist> soleTwistQueue = new ArrayList<>();
    private int queuedTwists = 0;
    private int queuedSoleTwists = 0;
    private int runningTwists = 0;
    private boolean soleTwistRunning = false;
    private NiceTwist niceTwist;
    private MeanTwist meanTwist;
    private EvilTwist evilTwist;
    private WeirdTwist weirdTwist;

    public TwistLocks() {
        this.niceTwist = new NiceTwist.NullTwist();
        this.meanTwist = new MeanTwist.NullTwist();
        this.evilTwist = new EvilTwist.NullTwist();
        this.weirdTwist = new WeirdTwist.NullTwist();
    }

    public List<Twist> getTwists() {
        return Arrays.asList(niceTwist, meanTwist, evilTwist, weirdTwist);
    }

    public synchronized boolean queueTwist(final Twist twist) {
        switch (twist.getType()) {
            case NICE:
                if (niceTwist == null || niceTwist.isComplete()) {
                    niceTwist = (NiceTwist) twist;
                    return checkSoleTwist(twist);
                } else {
                    niceTwistQueue.add((NiceTwist) twist);
                }
                break;
            case MEAN:
                if (meanTwist == null || meanTwist.isComplete()) {
                    meanTwist = (MeanTwist) twist;
                    return checkSoleTwist(twist);
                } else {
                    meanTwistQueue.add((MeanTwist) twist);
                }
                break;
            case EVIL:
                if (evilTwist == null || evilTwist.isComplete()) {
                    evilTwist = (EvilTwist) twist;
                    return checkSoleTwist(twist);
                } else {
                    evilTwistQueue.add((EvilTwist) twist);
                }
                break;
            case WEIRD:
                if (weirdTwist == null || weirdTwist.isComplete()) {
                    weirdTwist = (WeirdTwist) twist;
                    return checkSoleTwist(twist);
                } else {
                    weirdTwistQueue.add((WeirdTwist) twist);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid twist type!?");
        }
        return false;
    }

    private boolean checkSoleTwist(Twist twist) {
        if (twist instanceof SoleTwist) {
            if ((queuedTwists - queuedSoleTwists) + runningTwists == 0) {
                twist.start();
                soleTwistRunning = true;
                runningTwists++;
            } else {
                soleTwistQueue.add((SoleTwist) twist);
                queuedTwists++;
                queuedSoleTwists++;
                return false;
            }
        } else {
            twist.start();
            runningTwists++;
        }
        return true;
    }

    public synchronized void notifyTwistEnd(boolean isSole, boolean decrRunning) {
        if (decrRunning) {
            runningTwists--;
        }
        if (isSole) {
            soleTwistRunning = false;
        }
        if (soleTwistQueue.size() > 0) {
            if ((queuedTwists - queuedSoleTwists) + runningTwists == 0) {
                Twist twist = (Twist) soleTwistQueue.remove(0);
                niceTwistQueue.remove(twist);
                meanTwistQueue.remove(twist);
                evilTwistQueue.remove(twist);
                weirdTwistQueue.remove(twist);
                soleTwistRunning = true;
                queuedTwists--;
                queuedSoleTwists--;
                twist.start();
                runningTwists++;
            } else {
                return;
            }
        }
        if (niceTwistQueue.size() > 0 && !soleTwistRunning) {
            niceTwist = niceTwistQueue.remove(0);
            queuedTwists--;
            niceTwist.start();
            runningTwists++;
        }
        if (meanTwistQueue.size() > 0 && !soleTwistRunning) {
            meanTwist = meanTwistQueue.remove(0);
            queuedTwists--;
            meanTwist.start();
            runningTwists++;
        }
        if (evilTwistQueue.size() > 0 && !soleTwistRunning) {
            evilTwist = evilTwistQueue.remove(0);
            queuedTwists--;
            evilTwist.start();
            runningTwists++;
        }
        if (weirdTwistQueue.size() > 0 && !soleTwistRunning) {
            weirdTwist = weirdTwistQueue.remove(0);
            queuedTwists--;
            weirdTwist.start();
            runningTwists++;
        }
    }

    public synchronized void cancelAll() {
        if (niceTwist != null && !niceTwist.isComplete()) {
            niceTwist.cancel();
        }
        if (meanTwist != null && !meanTwist.isComplete()) {
            meanTwist.cancel();
        }
        if (evilTwist != null && !evilTwist.isComplete()) {
            evilTwist.cancel();
        }
        if (weirdTwist != null && !weirdTwist.isComplete()) {
            weirdTwist.cancel();
        }
        runningTwists = 0;
        soleTwistRunning = false;
        notifyTwistEnd(false, false);
    }

    public synchronized boolean cancel(Twist.Type type) {
        switch (type) {
            case NICE:
                return checkCancel(niceTwist);
            case MEAN:
                return checkCancel(meanTwist);
            case EVIL:
                return checkCancel(evilTwist);
            case WEIRD:
                return checkCancel(weirdTwist);
        }
        return false;
    }

    private boolean checkCancel(final Twist twist) {
        if (twist == null || twist.isComplete()) {
            return false;
        } else {
            twist.cancel();
            notifyTwistEnd(twist instanceof SoleTwist, true);
            return true;
        }
    }

    public synchronized NiceTwist getNiceTwist() {
        return niceTwist;
    }

    public synchronized MeanTwist getMeanTwist() {
        return meanTwist;
    }

    public synchronized EvilTwist getEvilTwist() {
        return evilTwist;
    }

    public synchronized WeirdTwist getWeirdTwist() {
        return weirdTwist;
    }
}
