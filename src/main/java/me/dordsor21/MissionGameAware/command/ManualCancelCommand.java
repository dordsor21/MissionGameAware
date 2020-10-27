package me.dordsor21.MissionGameAware.command;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.Twist;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ManualCancelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!sender.hasPermission("mga.cancel") || args.length > 1) {
            return true;
        }
        if (args.length == 0) {
            MissionGameAware.plugin.getTwistLocks().cancelAll();
            sender.sendMessage("Cancelled all twists.");
        } else if (Twist.Type.getValue(args[0]) != null) {
            boolean b = MissionGameAware.plugin.getTwistLocks().cancel(Twist.Type.getValue(args[0]));
            if (b) {
                sender.sendMessage("Cancelled current " + args[0] + " twist.");
            } else {
                sender.sendMessage("No " + args[0] + " twist to cancel.");
            }
        }
        return true;

    }
}
