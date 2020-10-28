package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.twists.EvilTwist;
import me.dordsor21.MissionGameAware.twists.SoleTwist;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SimonSaysFunBoxTime extends EvilTwist implements SoleTwist {
    @Override
    public void start() {
        for (Player p : Bukkit.getOnlinePlayers()){};
    }
}
