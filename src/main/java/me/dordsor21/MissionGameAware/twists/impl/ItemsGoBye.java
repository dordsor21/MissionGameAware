package me.dordsor21.MissionGameAware.twists.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.twists.SoleTwist;
import me.dordsor21.MissionGameAware.twists.WeirdTwist;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemsGoBye extends WeirdTwist implements SoleTwist {
    private ItemsGoByeListener listener = null;

    @Override
    public void start() {
        Bukkit.getPluginManager().registerEvents((listener = new ItemsGoByeListener()), MissionGameAware.plugin);
        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, this::complete, 20 * 20L);
    }

    @Override
    public void cancel() {
        if (listener != null) {
            HandlerList.unregisterAll(listener);
        }
        super.cancel();
    }

    @Override
    public void complete() {
        if (listener != null) {
            HandlerList.unregisterAll(listener);
        }
        super.complete();
    }

    private static final class ItemsGoByeListener implements Listener {

        @EventHandler
        public void onInventoryThing(PlayerItemHeldEvent e) {
            final int currentSlot = e.getNewSlot();
            final Player p = e.getPlayer();
            ItemStack[] contents = p.getInventory().getContents();
            ItemStack[] newContents = contents.clone();
            ArrayList<ItemStack> items = new ArrayList<>();
            int hotbarNo = 0;
            if (currentSlot == 0) {
                if (contents[1] == null || contents[1].getType().isAir()) {
                    return;
                }
            } else if (currentSlot < 8) {
                if ((contents[currentSlot - 1] == null || contents[currentSlot - 1].getType().isAir()) && (contents[currentSlot + 1] == null
                    || contents[currentSlot + 1].getType().isAir())) {
                    return;
                }
            } else {
                if (contents[7] == null || contents[7].getType().isAir()) {
                    return;
                }
            }
            for (int i = 0; i < 9; i++) {
                if (contents[i] != null && !contents[i].getType().isAir()) {
                    hotbarNo++;
                    items.add(contents[i]);
                    newContents[i] = new ItemStack(Material.AIR);
                }
            }
            switch (hotbarNo) {
                case 0:
                    return;
                case 1:
                    newContents[currentSlot > 4 ? 0 : 8] = items.get(0);
                    break;
                case 7:
                case 8:
                case 9:
                    boolean success = true;
                    while (items.size() > 6 && success) {
                        ItemStack item = items.remove(6);
                        for (int n = 9; n < newContents.length; n++) {
                            ItemStack slot = newContents[n];
                            if (slot != null && slot.getType().isAir()) {
                                if (n == newContents.length - 1) {
                                    success = false;
                                }
                                continue;
                            }
                            newContents[n] = item;
                        }
                    }
                    if (!success) {
                        while (items.size() > 6) {
                            items.remove(6);
                        }
                    }
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    int min = 0;
                    int max = 8;
                    int s = items.size();
                    for (int n = 0; n < s; n++) {
                        int tmax = max - currentSlot;
                        int tmin = currentSlot - min;
                        final int i;
                        final ItemStack item;
                        if (tmax > tmin) {
                            item = items.remove(0);
                            i = max;
                            max--;
                        } else {
                            item = items.remove(items.size() - 1);
                            i = min;
                            min++;
                        }
                        newContents[i] = item;
                    }
            }
            p.getInventory().setContents(newContents);
        }

    }
}
