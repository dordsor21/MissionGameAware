package me.dordsor21.MissionGameAware.command;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.impl.SurvivalChallenge;
import me.dordsor21.MissionGameAware.twists.Twist;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SurvChallengeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!sender.hasPermission("mga.queue")) {
            return true;
        }
        new SurvivalChallenge.ChallengeSelect().run();
        new SurvivalChallenge.ChallengeSelect().run();
        new SurvivalChallenge.ChallengeSelect().run();
        new SurvivalChallenge.ChallengeSelect().run();
        new SurvivalChallenge.ChallengeSelect().run();
        new SurvivalChallenge.ChallengeSelect().run();
        new SurvivalChallenge.ChallengeSelect().run();
        return true;
    }
}
