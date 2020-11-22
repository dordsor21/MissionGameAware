package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.twists.MeanTwist;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Cookies extends MeanTwist {
    @Override
    public void escapePlayer(Player p) {
        //do nothing
    }

    @Override
    public void start() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            ItemStack[] contents = p.getInventory().getContents();
            for (int i = 0; i < contents.length; i++) {
                contents[i] = new ItemStack(Material.COOKIE, 64);
            }
            p.getInventory().setContents(contents);
            ItemStack[] extraContents = p.getInventory().getExtraContents();
            for (int i = 0; i < extraContents.length; i++) {
                extraContents[i] = new ItemStack(Material.COOKIE, 64);
            }
            p.getInventory().setExtraContents(extraContents);
            ItemStack[] armorContents = p.getInventory().getArmorContents();
            for (int i = 0; i < armorContents.length; i++) {
                armorContents[i] = new ItemStack(Material.COOKIE, 64);
            }
            p.getInventory().setArmorContents(armorContents);
            ItemStack[] storageContents = p.getInventory().getStorageContents();
            for (int i = 0; i < storageContents.length; i++) {
                storageContents[i] = new ItemStack(Material.COOKIE, 64);
            }
            p.getInventory().setStorageContents(storageContents);
        }
        this.complete();
    }
}
