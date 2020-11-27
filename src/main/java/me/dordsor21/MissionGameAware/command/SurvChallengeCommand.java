package me.dordsor21.MissionGameAware.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class SurvChallengeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!sender.hasPermission("mga.queue")) {
            return true;
        }
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        final Scoreboard scoreboard = manager.getNewScoreboard();
        final Objective objective = scoreboard.registerNewObjective("alive", "hasNotDied", "\u00A74Alive");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        int i = 1;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (i > 15) {
                break;
            }
            i++;
            Score score = objective.getScore(p.getName());
            score.setScore(1);
        }
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setScoreboard(scoreboard);
        }
        return true;
    }
}
