package me.dordsor21.MissionGameAware.util;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Challenge;
import me.dordsor21.MissionGameAware.twists.Twist;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class RandomTwists {

    private final Random random = new Random();
    private List<Supplier<Twist>> twists = new ArrayList<>();
    private Challenge challenge;
    private Challenge.Type type;
    private String challengeName;
    private BukkitTask task;

    public RandomTwists() {
        this.task = null;
    }

    public void currentChallenge(Challenge challenge) {
        this.challenge = challenge;
        this.type = challenge.getType();
        this.challengeName = challenge.getChallengeName();
        this.twists = challenge.getTwists();
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    public void start(long delay, long interval, int randomAdd) {
        if (randomAdd == 0) {
            task = Bukkit.getScheduler().runTaskTimerAsynchronously(MissionGameAware.plugin, this::randomTwist, delay, interval);
        } else {
            task = Bukkit.getScheduler().runTaskLaterAsynchronously(MissionGameAware.plugin, () -> {
                this.randomTwist();
                Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, () -> scheduleNext(interval, randomAdd), 1L);
            }, delay);
        }
    }

    private void scheduleNext(long interval, int randomAdd) {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
        task = Bukkit.getScheduler().runTaskLaterAsynchronously(MissionGameAware.plugin, () -> {
            this.randomTwist();
            Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, () -> scheduleNext(interval, randomAdd), 1L);
        }, interval + random.nextInt(randomAdd));
    }

    public boolean randomTwist() {
        if (twists.size() == 0) {
            return false;
        }
        final Twist twist;
        if (twists.size() == 1) {
            twist = twists.get(0).get();
        } else {
            twist = twists.get(random.nextInt(twists.size())).get();
        }
        MissionGameAware.plugin.getTwistLocks().queueTwist(twist);
        return true;
    }

}
