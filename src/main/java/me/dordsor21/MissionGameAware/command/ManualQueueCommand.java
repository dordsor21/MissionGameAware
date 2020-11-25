package me.dordsor21.MissionGameAware.command;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.Twist;
import me.dordsor21.MissionGameAware.twists.impl.AnEffect;
import me.dordsor21.MissionGameAware.twists.impl.Bees;
import me.dordsor21.MissionGameAware.twists.impl.Blindness;
import me.dordsor21.MissionGameAware.twists.impl.BlindnessTeleport;
import me.dordsor21.MissionGameAware.twists.impl.Cookies;
import me.dordsor21.MissionGameAware.twists.impl.FakeNuke;
import me.dordsor21.MissionGameAware.twists.impl.HangOn;
import me.dordsor21.MissionGameAware.twists.impl.ItemsGoBye;
import me.dordsor21.MissionGameAware.twists.impl.KittyCannonEract;
import me.dordsor21.MissionGameAware.twists.impl.LaserFocus;
import me.dordsor21.MissionGameAware.twists.impl.Lightning;
import me.dordsor21.MissionGameAware.twists.impl.LookDown;
import me.dordsor21.MissionGameAware.twists.impl.LookUp;
import me.dordsor21.MissionGameAware.twists.impl.ManyMobs;
import me.dordsor21.MissionGameAware.twists.impl.Nausea;
import me.dordsor21.MissionGameAware.twists.impl.NightDay;
import me.dordsor21.MissionGameAware.twists.impl.PumpkinHead;
import me.dordsor21.MissionGameAware.twists.impl.RandomTeleport;
import me.dordsor21.MissionGameAware.twists.impl.Sheep;
import me.dordsor21.MissionGameAware.twists.impl.SimonSaysFunBoxTime;
import me.dordsor21.MissionGameAware.twists.impl.Speed02;
import me.dordsor21.MissionGameAware.twists.impl.Speed10;
import me.dordsor21.MissionGameAware.twists.impl.TeleportAbout;
import me.dordsor21.MissionGameAware.twists.impl.Zoom;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class ManualQueueCommand implements CommandExecutor {

    private static final List<Supplier<Twist>> all = Collections.unmodifiableList(Arrays
        .asList(AnEffect::new, Bees::new, Blindness::new, BlindnessTeleport::new, Cookies::new, FakeNuke::new, HangOn::new,
            ItemsGoBye::new, KittyCannonEract::new, LaserFocus::new, Lightning::new, LookDown::new, LookUp::new,
            ManyMobs::new, Nausea::new, NightDay::new, PumpkinHead::new, RandomTeleport::new, Sheep::new,
            SimonSaysFunBoxTime::new, Speed02::new, Speed10::new, TeleportAbout::new, Zoom::new));

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!sender.hasPermission("mga.queue") || args.length != 1) {
            return true;
        }
        String tw = args[0];
        if (tw.equalsIgnoreCase("all")) {
            for (Supplier<Twist> t : all) {
                MissionGameAware.plugin.getTwistLocks().queueTwist(t.get());
            }
            return true;
        }
        final Class<?> t;
        try {
            t = Class.forName("me.dordsor21.MissionGameAware.twists.impl." + tw);
        } catch (ClassNotFoundException ignored) {
            sender.sendMessage("Could not find twist " + tw);
            return true;
        }
        final Twist twist;
        try {
            twist = (Twist) t.newInstance();
        } catch (InstantiationException | IllegalAccessException ignored) {
            sender.sendMessage("Could not instantiate twist " + tw);
            return true;
        }
        MissionGameAware.plugin.getTwistLocks().queueTwist(twist);
        sender.sendMessage("Twist " + tw + " queued");

        return true;
    }
}
