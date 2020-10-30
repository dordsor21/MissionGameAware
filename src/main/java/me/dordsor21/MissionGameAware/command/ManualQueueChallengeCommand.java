package me.dordsor21.MissionGameAware.command;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Challenge;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ManualQueueChallengeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!sender.hasPermission("mga.queuechallenge") || args.length != 1) {
            return true;
        }
        String ch = args[0];
        final Class<?> t;
        try {
            t = Class.forName("me.dordsor21.MissionGameAware.challenges.impl." + ch);
        } catch (ClassNotFoundException ignored) {
            sender.sendMessage("Could not find challenge " + ch);
            return true;
        }
        final Challenge challenge;
        try {
            challenge = (Challenge) t.newInstance();
        } catch (InstantiationException | IllegalAccessException ignored) {
            sender.sendMessage("Could not instantiate challenge " + ch);
            return true;
        }
        MissionGameAware.plugin.getChallengeHandler().start(challenge);
        sender.sendMessage("Challenge " + ch + " started");

        return true;
    }
}
