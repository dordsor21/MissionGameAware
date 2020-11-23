package me.dordsor21.MissionGameAware.challenges.impl;

import me.dordsor21.MissionGameAware.MissionGameAware;
import me.dordsor21.MissionGameAware.challenges.Challenge;
import me.dordsor21.MissionGameAware.challenges.Survival.SingleChallenge;
import me.dordsor21.MissionGameAware.challenges.Survival.SurvChallenge;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.BedrockHurt;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.Breed;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.Burn;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.CatchFish;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.ChickenEgg;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.CraftCake;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.FallDeath;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.GiveItem;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.GrowFarmFood;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.GrowTree;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.IronGolem;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.KillCommonMob;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.KillPlayer;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.KillRareMob;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.KillWither;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.MilkCow;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.Nether;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.SnowGolem;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.TameCat;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.TameWolf;
import me.dordsor21.MissionGameAware.twists.Twist;
import me.dordsor21.MissionGameAware.twists.impl.Bees;
import me.dordsor21.MissionGameAware.twists.impl.Blindness;
import me.dordsor21.MissionGameAware.twists.impl.FakeNuke;
import me.dordsor21.MissionGameAware.twists.impl.HangOn;
import me.dordsor21.MissionGameAware.twists.impl.ItemsGoBye;
import me.dordsor21.MissionGameAware.twists.impl.KittyCannonEract;
import me.dordsor21.MissionGameAware.twists.impl.LaserFocus;
import me.dordsor21.MissionGameAware.twists.impl.Lightning;
import me.dordsor21.MissionGameAware.twists.impl.LookDown;
import me.dordsor21.MissionGameAware.twists.impl.LookUp;
import me.dordsor21.MissionGameAware.twists.impl.Nausea;
import me.dordsor21.MissionGameAware.twists.impl.PumpkinHead;
import me.dordsor21.MissionGameAware.twists.impl.Sheep;
import me.dordsor21.MissionGameAware.twists.impl.Speed02;
import me.dordsor21.MissionGameAware.twists.impl.Speed10;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class SurvivalChallenge extends Challenge {

    public static final Map<Player, Integer> playerScores = new ConcurrentHashMap<>();
    public static final String cPr = "&4&l[&c&lChallenge&4&l] &r";
    private static final List<Supplier<Twist>> twists = Collections.unmodifiableList(Arrays
        .asList(Blindness::new, Bees::new, FakeNuke::new, HangOn::new, ItemsGoBye::new, KittyCannonEract::new,
            LaserFocus::new, Lightning::new, LookDown::new, LookUp::new, Nausea::new, PumpkinHead::new, Sheep::new,
            Speed02::new, Speed10::new));
    private static final AtomicInteger descrStage = new AtomicInteger(0);
    private static final List<Player> players = new ArrayList<>();
    private static final ArrayList<String> lore = new ArrayList<>();
    private static final Random rand = new Random();
    private static final List<Class<? extends SurvChallenge>> challengeList = new ArrayList<>(Arrays
        .asList(BedrockHurt.class, Breed.class, Burn.class, CatchFish.class, FallDeath.class, GiveItem.class,
            GrowFarmFood.class, GrowTree.class, KillCommonMob.class, KillPlayer.class, KillRareMob.class, Nether.class,
            ChickenEgg.class, CraftCake.class, IronGolem.class, MilkCow.class, KillWither.class, SnowGolem.class,
            TameCat.class, TameWolf.class));
    private static final Location spawn = new Location(Bukkit.getWorld("world"), 100, 100, 100);
    private static final List<SurvChallenge> running = new LinkedList<>();
    private static final Set<Class<?>> run = new LinkedHashSet<>();
    private static ScheduledFuture<?> descr;
    private static ScheduledFuture<?> challenges;
    private static List<Player> top5 = new ArrayList<>();
    private static Player pvpwinner = null;
    private static PVPListener pvpListener;

    static {
        lore.add("Throw me to escape a twist!");
        lore.add("Single use only!");
    }

    @Override
    public Type getType() {
        return Type.SURVIVAL;
    }

    public List<Supplier<Twist>> getTwists() {
        return twists;
    }

    @Override
    public void stop() {
        descr.cancel(true);
        challenges.cancel(true);

    }

    @Override
    public void run() {
        for (Supplier<Twist> twist : twists) {
            MissionGameAware.plugin.getTwistLocks().queueTwist(twist.get());
        }
        descrStage.set(0);
        descr = Executors.newSingleThreadScheduledExecutor()
            .scheduleAtFixedRate(SurvivalDescribe::new, 5L, 5L, TimeUnit.SECONDS);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(ChallengeSelect::new, 5L, 5L, TimeUnit.MINUTES);
    }

    private static final class SurvivalDescribe implements Runnable {

        @Override
        public void run() {
            Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                switch (descrStage.get()) {
                    case 0:
                        Bukkit.broadcastMessage("Welcome to the Survival Challenge");
                        break;
                    case 1:
                        Bukkit.broadcastMessage("10 Points win. 3 top 5.");
                        break;
                    case 2:
                        Bukkit.broadcastMessage("Message 2");
                        break;
                    case 3:
                        Bukkit.broadcastMessage("Message 3");
                        break;
                    case 4:
                        Bukkit.broadcastMessage("Message 4");
                        break;
                    case 5:
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "&4Challenge 1: &fPVP."), 0, 70, 20);
                        }
                        break;
                    case 6:
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "&4Top 5 prize: Escape a Twist"), 0,
                                70, 20);
                        }
                        break;
                    case 7:
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "&4Top prize: Totem of Undying"), 0,
                                70, 20);
                        }
                        break;
                    case 8:
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.teleport(new Location(Bukkit.getWorld("pvp"), 100, 100, 100));
                        }
                        break;
                    case 9:
                        descr.cancel(true);
                        Thread t = new Thread(() -> {
                            try {
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "&55"), 0, 70, 20);
                                }
                                Thread.sleep(1000L);
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "&24"), 0, 70, 20);
                                }
                                Thread.sleep(1000L);
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "&13"), 0, 70, 20);
                                }
                                Thread.sleep(1000L);
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "&32"), 0, 70, 20);
                                }
                                Thread.sleep(1000L);
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "&f1"), 0, 70, 20);
                                }
                                Thread.sleep(1000L);
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "&4Battle!"), 0, 70, 20);
                                }
                                players.addAll(Bukkit.getOnlinePlayers());
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    playerScores.put(p, 0);
                                }
                                Bukkit.getPluginManager()
                                    .registerEvents(pvpListener = new PVPListener(), MissionGameAware.plugin);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                        t.start();
                        break;
                }
                descrStage.incrementAndGet();
            });
        }
    }


    private static final class PVPListener implements Listener {
        @EventHandler
        public void onPlayerDeath(PlayerDeathEvent event) {
            handle(event.getEntity());
        }

        void handle(Player pl) {
            players.remove(pl);
            if (players.size() == 5) {
                top5 = new ArrayList<>(players);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendTitle("", ChatColor.translateAlternateColorCodes('&', "&4Final 5!"), 0, 70, 20);
                }
            }
            if (players.size() == 1) {
                pvpwinner = players.get(0);
                HandlerList.unregisterAll(pvpListener);
                new Thread(() -> {
                    Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendTitle("", ChatColor
                                    .translateAlternateColorCodes('&', "&6Winner! &f Congratulations &5" + pvpwinner.getName()),
                                0, 70, 20);
                        }
                    });
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.setGameMode(GameMode.SURVIVAL);
                            p.teleport(spawn);
                            p.sendTitle("", ChatColor.translateAlternateColorCodes('&',
                                "&fTake 5 minutes to explore, and then the challenges begin!"), 0, 70, 20);
                        }
                        pvpwinner.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
                        pvpwinner.getInventory().addItem(new ItemStack(Material.DIAMOND, 16));
                        pvpwinner.getInventory().addItem(new ItemStack(Material.EXPERIENCE_BOTTLE, 32));
                        for (Player p : top5) {
                            ItemStack namedSnowball = new ItemStack(Material.SNOWBALL);
                            ItemMeta meta = namedSnowball.getItemMeta();
                            meta.setDisplayName("Escape a Twist");
                            meta.setLore(lore);
                            namedSnowball.setItemMeta(meta);
                            p.getInventory().addItem(namedSnowball);
                            ItemStack diamonds = new ItemStack(Material.DIAMOND, 16);
                            p.getInventory().addItem(diamonds);
                        }
                    });
                    challenges = Executors.newSingleThreadScheduledExecutor()
                        .scheduleAtFixedRate(ChallengeSelect::new, 5L, 5L, TimeUnit.MINUTES);
                }).start();
            }
        }

        @EventHandler
        public void onRespawn(PlayerRespawnEvent event) {
            event.getPlayer().teleport(new Location(Bukkit.getWorld("pvp"), 100, 100, 100));
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        }

        @EventHandler
        public void onPlayerDeath(PlayerQuitEvent event) {
            handle(event.getPlayer());
        }
    }


    private static final class TwistListener implements Listener {
        @EventHandler
        public void onInteract(PlayerInteractEvent event) {
            if (!top5.contains(event.getPlayer())) {
                return;
            }
            ItemStack offhand = event.getPlayer().getInventory().getItemInOffHand();
            ItemStack mainhand = event.getPlayer().getInventory().getItemInMainHand();
            if ((offhand.getType() == Material.SNOWBALL && offhand.getItemMeta() != null && offhand.getItemMeta()
                .getDisplayName().equals("Escape a Twist") && offhand.getItemMeta().getLore() != null && offhand
                .getItemMeta().getLore().equals(lore))) {
                MissionGameAware.plugin.getTwistLocks().escapePlayer(event.getPlayer());
                event.setCancelled(true);
                event.getPlayer().getInventory().setItemInOffHand(new ItemStack(Material.AIR));
            } else if ((mainhand.getType() == Material.SNOWBALL && mainhand.getItemMeta() != null && mainhand.getItemMeta()
                .getDisplayName().equals("Escape a Twist") && mainhand.getItemMeta().getLore() != null && mainhand
                .getItemMeta().getLore().equals(lore))) {
                MissionGameAware.plugin.getTwistLocks().escapePlayer(event.getPlayer());
                event.setCancelled(true);
                event.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            }
        }
    }


    public static final class ChallengeSelect implements Runnable {

        @Override
        public void run() {
            Class<? extends SurvChallenge> challenge;
            Random r = new Random();
            int size = challengeList.size();
            while (
                !(challenge = challengeList.get(r.nextInt(size))).getSuperclass().equals(SingleChallenge.class) && !challenge
                    .getSuperclass().getSuperclass().equals(SingleChallenge.class) || !run.contains(challenge)) {
                try {
                    SurvChallenge survChallenge = challenge.newInstance();
                    running.add(survChallenge);
                    if (survChallenge.isSingle()) {
                        run.add(challenge);
                    }
                    break;
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
