package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.twists.EvilTwist;
import me.dordsor21.MissionGameAware.twists.SoleTwist;
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

    private static final List<Supplier<WhatSimonSays>> whatSimonsSayses =
        Arrays.asList(FlyUp::new, FlyNESW::new, FlyUp::new, LookDown::new, LookNESW::new, LookUp::new, PlaceBlock::new, HoldItem::new);

    @Override
    public void start() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "Simon says it's... &5&lFUNBOX TIME"),
                ChatColor.translateAlternateColorCodes('&', "Do what Simon says or meet the FUNBOX!"), 5, 10, 5);
        }
    }
}
