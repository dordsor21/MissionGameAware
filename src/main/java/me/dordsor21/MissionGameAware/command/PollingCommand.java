package me.dordsor21.MissionGameAware.command;

import me.dordsor21.MissionGameAware.MissionGameAware;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class PollingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof ConsoleCommandSender) || !sender.hasPermission("mga.polling") || args.length != 1) {
            return true;
        }
        if (args[0].equalsIgnoreCase("on")) {
            MissionGameAware.plugin.enablePolling();
        } else if (args[0].equalsIgnoreCase("off")) {
            MissionGameAware.plugin.disablePolling();
        }
        return true;
    }
}
