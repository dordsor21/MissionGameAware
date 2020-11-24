package me.dordsor21.MissionGameAware;

import io.lettuce.core.Consumer;
import io.lettuce.core.RedisBusyException;
import io.lettuce.core.RedisClient;
import io.lettuce.core.StreamMessage;
import io.lettuce.core.XGroupCreateArgs;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import me.dordsor21.MissionGameAware.command.ManualCancelCommand;
import me.dordsor21.MissionGameAware.command.ManualQueueChallengeCommand;
import me.dordsor21.MissionGameAware.command.ManualQueueCommand;
import me.dordsor21.MissionGameAware.command.PollingCommand;
import me.dordsor21.MissionGameAware.command.SurvChallengeCommand;
import me.dordsor21.MissionGameAware.util.ChallengeHandler;
import me.dordsor21.MissionGameAware.util.RandomTwists;
import me.dordsor21.MissionGameAware.util.TwistLocks;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MissionGameAware extends JavaPlugin implements Listener {

    public static final String prefix = "&6[&eMGA&6] &f";
    private static final RedisClient redis = RedisClient.create("redis://localhost:6379");
    private static final String rand = UUID.randomUUID().toString().substring(0, 6);
    private static final TwistLocks twistLocks = new TwistLocks();
    private static final RandomTwists randomTwists = new RandomTwists();
    private static final ChallengeHandler challengeHandler = new ChallengeHandler();
    private static final AtomicInteger next100 = new AtomicInteger(0);
    public static MissionGameAware plugin;
    private RedisCommands<String, String> syncCommands = null;
    private StatefulRedisConnection<String, String> connection = null;
    private ScheduledFuture<?> poller;

    @Override
    public void onEnable() {
        getLogger().info("Enabling MissionGameAware");

        this.getCommand("queuetwist").setExecutor(new ManualQueueCommand());
        this.getCommand("canceltwist").setExecutor(new ManualCancelCommand());
        this.getCommand("queuechallenge").setExecutor(new ManualQueueChallengeCommand());
        this.getCommand("survchallenge").setExecutor(new SurvChallengeCommand());
        this.getCommand("polling").setExecutor(new PollingCommand());

        Bukkit.getPluginManager().registerEvents(this, this);
        plugin = this;
    }

    @EventHandler
    public void onServerUp(ServerLoadEvent e) {
        connection = redis.connect();
        syncCommands = connection.sync();
        try {
            syncCommands.xgroupCreate(XReadArgs.StreamOffset.from("just_giving", "0-0"), "server_" + rand,
                XGroupCreateArgs.Builder.mkstream());
        } catch (RedisBusyException redisBusyException) {
            System.out.println("server_" + rand + " group already exists");
        }
        poller = Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            List<StreamMessage<String, String>> messages = syncCommands
                .xreadgroup(Consumer.from("server_" + rand, "mga"), XReadArgs.StreamOffset.lastConsumed("just_giving"));

            double newAmount = 0;
            for (StreamMessage<String, String> message : messages) {
                if (message.getStream().equalsIgnoreCase("just_giving")) {
                    Map<String, String> map = message.getBody();
                    newAmount = Math.max(newAmount, Double.parseDouble(map.get("amount")));
                    syncCommands.xdel("just_giving", message.getId());
                }
                syncCommands.xack("just_giving", "server_" + rand, message.getId());
            }
            if (newAmount > next100.get()) {
                next100.getAndAdd(100);
            }
        }, 10L, TimeUnit.SECONDS);
    }

    @Override
    public void onDisable() {
        poller.cancel(true);
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
}
