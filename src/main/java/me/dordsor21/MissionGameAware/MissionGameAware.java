package me.dordsor21.MissionGameAware;

import me.dordsor21.MissionGameAware.command.ManualCancelCommand;
import me.dordsor21.MissionGameAware.command.ManualQueueChallengeCommand;
import me.dordsor21.MissionGameAware.command.ManualQueueCommand;
import me.dordsor21.MissionGameAware.command.SurvChallengeCommand;
import me.dordsor21.MissionGameAware.util.ChallengeHandler;
import me.dordsor21.MissionGameAware.util.RandomTwists;
import me.dordsor21.MissionGameAware.util.TwistLocks;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MissionGameAware extends JavaPlugin implements Listener {

    public static final String prefix = "&6[&eMGA&6] &f";
    private static final TwistLocks twistLocks = new TwistLocks();
    private static final RandomTwists randomTwists = new RandomTwists();
    private static final ChallengeHandler challengeHandler = new ChallengeHandler();
    public static MissionGameAware plugin;

    @Override
    public void onEnable() {
        getLogger().info("Enabling MissionGameAware");

        this.getCommand("queuetwist").setExecutor(new ManualQueueCommand());
        this.getCommand("canceltwist").setExecutor(new ManualCancelCommand());
        this.getCommand("queuechallenge").setExecutor(new ManualQueueChallengeCommand());
        this.getCommand("survchallenge").setExecutor(new SurvChallengeCommand());

        Bukkit.getPluginManager().registerEvents(this, this);
        plugin = this;
    }

    public TwistLocks getTwistLocks() {
        return twistLocks;
    }

    public RandomTwists getRandomTwists() {
        return randomTwists;
    }

    public ChallengeHandler getChallengeHandler() {
        return challengeHandler;
    }

    @EventHandler
    public void placeBlock(BlockPlaceEvent e) {
        Block placed = e.getBlockPlaced();
        if (placed.getType() != Material.CARVED_PUMPKIN) {
            return;
        }
        if (placed.getRelative(0, -1, 0).getType() == Material.IRON_BLOCK
            && placed.getRelative(0, -2, 0).getType() == Material.IRON_BLOCK && (
            (placed.getRelative(1, -1, 0).getType() == Material.IRON_BLOCK
                && placed.getRelative(-1, -1, 0).getType() == Material.IRON_BLOCK) || (
                placed.getRelative(0, -1, 1).getType() == Material.IRON_BLOCK
                    && placed.getRelative(0, -1, -1).getType() == Material.IRON_BLOCK))) {
            System.out.println("a");
        }
    }
}
