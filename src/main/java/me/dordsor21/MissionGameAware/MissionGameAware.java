package me.dordsor21.MissionGameAware;

import me.dordsor21.MissionGameAware.command.ManualQueueCommand;
import me.dordsor21.MissionGameAware.util.RandomTwists;
import me.dordsor21.MissionGameAware.util.TwistLocks;
import org.bukkit.plugin.java.JavaPlugin;

public class MissionGameAware extends JavaPlugin {

    private static final TwistLocks twistLocks = new TwistLocks();
    private static final RandomTwists randomTwists = new RandomTwists();
    public static MissionGameAware plugin;

    @Override
    public void onEnable() {
        getLogger().info("Enabling MissionGameAware");

        this.getCommand("queuetwist").setExecutor(new ManualQueueCommand());

        plugin = this;
    }

    public TwistLocks getTwistLocks() {
        return twistLocks;
    }

    public RandomTwists getRandomTwists() {
        return randomTwists;
    }
}
