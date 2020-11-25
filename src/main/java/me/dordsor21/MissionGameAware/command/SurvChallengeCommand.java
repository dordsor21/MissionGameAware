package me.dordsor21.MissionGameAware.command;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SurvChallengeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (!sender.hasPermission("mga.queue")) {
            return true;
        }
        Player p = (Player) sender;
        Location l = p.getLocation();
        Block b = l.getBlock();
        if (b.isEmpty()) {
            //int y = 0;
            for (int y = l.getBlockY(); y > 0; y--) {
                if (testLoc(p, l, b, y)) {
                    return true;
                }
            }
        } else {
            for (int y = l.getBlockY(); y < 255; y++) {
                if (testLoc(p, l, b, y)) {
                    return true;
                }
            }
        }
        p.setGameMode(GameMode.SURVIVAL);
        return true;
    }

    private boolean testLoc(Player p, Location l, Block b, int y) {
        int rel = y - l.getBlockY();
        if (!b.getRelative(0, rel - 1, 0).isEmpty() && b.getRelative(0, rel, 0).isEmpty() && b.getRelative(0, rel + 1, 0)
            .isEmpty()) {
            p.setGameMode(GameMode.SURVIVAL);
            l.setY(y);
            p.teleport(l);
            return true;
        }
        return false;
    }
}
