package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.EvilTwist;
import me.dordsor21.MissionGameAware.twists.SoleTwist;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.ChangeDoWhatSimonSays;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.FlyNESW;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.FlyUp;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.HoldItem;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.LookDown;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.LookNESW;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.LookUp;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.PlaceBlock;
import me.dordsor21.MissionGameAware.twists.WhatSimonSays.WhatSimonSays;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class SimonSaysFunBoxTime extends EvilTwist implements SoleTwist {

    private static final List<Supplier<WhatSimonSays>> whatSimonsSayses = Arrays
        .asList(FlyUp::new, FlyNESW::new, FlyUp::new, LookDown::new, LookNESW::new, LookUp::new, PlaceBlock::new, HoldItem::new,
            ChangeDoWhatSimonSays::new);
    private boolean doWhatSimonSays = true;

    @Override
    public void start() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "Simon says it's... &5&lFUNBOX TIME"),
                ChatColor.translateAlternateColorCodes('&', "Do what Simon says or meet the FUNBOX!"), 0, 40, 10);
        }
        Bukkit.getScheduler().runTaskAsynchronously(MissionGameAware.plugin, new SimonSaysTimer(doWhatSimonSays, SimonSaysFunBoxTime.this));
    }

    private void setShouldDoSimonSaying(boolean doWhatSimonSays) {
        this.doWhatSimonSays = doWhatSimonSays;
    }

    private boolean shouldDoWhatSimonSays() {
        return doWhatSimonSays;
    }

    private static final class SimonSaysTimer implements Runnable {

        private static final String prefix = ChatColor.translateAlternateColorCodes('&', "&4Simon says: &f");
        private final SimonSaysFunBoxTime twist;
        private final boolean doWhatSimonSays;

        public SimonSaysTimer(boolean doWhatSimonSays, SimonSaysFunBoxTime twist) {
            this.twist = twist;
            this.doWhatSimonSays = doWhatSimonSays;
        }

        @Override
        public void run() {
            WhatSimonSays whatSimonSays = whatSimonsSayses.get((int) Math.floor(Math.random() * whatSimonsSayses.size())).get();
            if (whatSimonSays instanceof ChangeDoWhatSimonSays) {
                twist.setShouldDoSimonSaying(!doWhatSimonSays);
                return;
            }
            final boolean doWhatMessageSays = Math.random() > 0.5;
            Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendTitle(doWhatMessageSays == doWhatSimonSays ? prefix : "" + whatSimonSays.getMessage(), "", 0, 40, 10);
                }
            });
            whatSimonSays.doIt(doWhatMessageSays);
        }
    }
}
