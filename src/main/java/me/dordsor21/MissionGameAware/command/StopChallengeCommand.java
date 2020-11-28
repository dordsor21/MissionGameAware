package me.dordsor21.MissionGameAware.command;

import me.dordsor21.MissionGameAware.MissionGameAware;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StopChallengeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!sender.hasPermission("mga.cancel") || args.length > 1) {
            return true;
        }
        if (MissionGameAware.plugin.getChallengeHandler().isRunning()) {
            MissionGameAware.plugin.getChallengeHandler().getRunning().stop();
        }
        return true;

    }
}
