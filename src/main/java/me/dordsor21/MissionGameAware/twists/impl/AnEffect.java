package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.MeanTwist;
import me.dordsor21.MissionGameAware.util.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class AnEffect extends MeanTwist {

    private PotionEffectType type;

    @Override
    public void escapePlayer(Player p) {
        p.removePotionEffect(type);
    }

    @Override
    public void start() {
        System.out.println(this.getClass().getSimpleName() + " twist started.");
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "&4Twist&f: " + this.getClass().getSimpleName()),
                    0, 70, 20);
            }
        });
        String[] str = Lists.SomeEffects.values()[new Random().nextInt(Lists.SomeEffects.values().length)].name().split("_");
        type = PotionEffectType.getByName(str[0].replace("USCR", "_"));
        Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.addPotionEffect(new PotionEffect(type, Integer.parseInt(str[1]), Integer.parseInt(str[2])));
            }
        });
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 45 * 20L);
    }

}
