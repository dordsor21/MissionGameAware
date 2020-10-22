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
                twist.start(this);
                runningTwists++;
            } else {
                soleTwistQueue.add((SoleTwist) twist);
                queuedTwists++;
                queuedSoleTwists++;
                return false;
            }
        } else {
            twist.start(this);
            runningTwists++;
        }
        return true;
    }

    public synchronized void notify(Twist.Type type) {
        runningTwists--;
        if (soleTwistQueue.size() > 0) {
            if ((queuedTwists - queuedSoleTwists) + runningTwists == 0) {
                Twist twist = (Twist) soleTwistQueue.remove(0);
                niceTwistQueue.remove(twist);
                meanTwistQueue.remove(twist);
                evilTwistQueue.remove(twist);
                weirdTwistQueue.remove(twist);
                queuedTwists--;
                queuedSoleTwists--;
                twist.start(this);
                runningTwists++;
            } else {
                return;
            }
        }
        switch (type) {
            case NICE:
                niceTwist = niceTwistQueue.remove(0);
                queuedTwists--;
                niceTwist.start(this);
                runningTwists++;
                break;
            case MEAN:
                meanTwist = meanTwistQueue.remove(0);
                queuedTwists--;
                meanTwist.start(this);
                runningTwists++;
                break;
            case EVIL:
                evilTwist = evilTwistQueue.remove(0);
                queuedTwists--;
                evilTwist.start(this);
                runningTwists++;
                break;
            case WEIRD:
                weirdTwist = weirdTwistQueue.remove(0);
                queuedTwists--;
                weirdTwist.start(this);
                runningTwists++;
                break;
            default:
                throw new IllegalArgumentException("Invalid twist type!?");
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
