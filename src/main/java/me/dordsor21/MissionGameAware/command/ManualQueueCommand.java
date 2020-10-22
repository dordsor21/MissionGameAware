package me.dordsor21.MissionGameAware.command;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.Twist;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ManualQueueCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!sender.hasPermission("mga.queue") || args.length != 1) {
            return true;
        }
        String tw = args[0];
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
        return true;
    }
}
