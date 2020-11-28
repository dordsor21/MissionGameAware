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
import me.dordsor21.MissionGameAware.challenges.Survival.impl.Dance;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.DanceParty;
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
import me.dordsor21.MissionGameAware.challenges.Survival.impl.SnipePlayer;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.SnowGolem;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.TameCat;
import me.dordsor21.MissionGameAware.challenges.Survival.impl.TameWolf;
import me.dordsor21.MissionGameAware.twists.Twist;
import me.dordsor21.MissionGameAware.twists.impl.AnEffect;
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
import me.dordsor21.MissionGameAware.twists.impl.ManyMobs;
import me.dordsor21.MissionGameAware.twists.impl.Nausea;
import me.dordsor21.MissionGameAware.twists.impl.NightDay;
import me.dordsor21.MissionGameAware.twists.impl.PumpkinHead;
import me.dordsor21.MissionGameAware.twists.impl.Sheep;
import me.dordsor21.MissionGameAware.twists.impl.Speed02;
import me.dordsor21.MissionGameAware.twists.impl.Speed10;
import me.dordsor21.MissionGameAware.twists.impl.TeleportAbout;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
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
    public static final List<SurvChallenge> running = new LinkedList<>();
    private static final List<Supplier<Twist>> twists = Collections.unmodifiableList(Arrays
        .asList(Blindness::new, Bees::new, FakeNuke::new, HangOn::new, ItemsGoBye::new, KittyCannonEract::new,
            LaserFocus::new, Lightning::new, LookDown::new, LookUp::new, Nausea::new, PumpkinHead::new, Sheep::new,
            Speed02::new, Speed10::new, TeleportAbout::new, ManyMobs::new, AnEffect::new, NightDay::new));
    private static final AtomicInteger descrStage = new AtomicInteger(0);
    private static final List<Player> players = new ArrayList<>();
    private static final ArrayList<String> lore = new ArrayList<>();
    private static final List<Class<? extends SurvChallenge>> challengeList = new ArrayList<>(Arrays
        .asList(BedrockHurt.class, Breed.class, Burn.class, CatchFish.class, FallDeath.class, GiveItem.class,
            GrowFarmFood.class, GrowTree.class, KillCommonMob.class, KillPlayer.class, KillRareMob.class, Nether.class,
            ChickenEgg.class, CraftCake.class, IronGolem.class, MilkCow.class, KillWither.class, SnowGolem.class,
            TameCat.class, TameWolf.class, Dance.class, DanceParty.class, SnipePlayer.class));
    private static final Set<Class<?>> run = new LinkedHashSet<>();
    private static final List<Location> chests = new ArrayList<>();
    private static final Set<Player> inScoreboard = new HashSet<>();
    private static Location spawn = null;
    private static ScheduledFuture<?> descr;
    private static ScheduledFuture<?> challenges;
    private static ScheduledFuture<?> scoreboardRunner;
    private static ScheduledFuture<?> doChests;
    private static List<Player> top5 = new ArrayList<>();
    private static List<Player> deadWhenDone = new ArrayList<>();
    private static Player pvpwinner = null;
    private static PVPListener pvpListener;
    private static DeathListener deathListener;
    private static Scoreboard scoreboard = null;
    private static Objective objective = null;

    static {
        lore.add("Throw me to escape a twist!");
        lore.add("Single use only!");
    }

    private boolean isRunning = false;

    @Override
    public Type getType() {
        return Type.SURVIVAL;
    }

    public List<Supplier<Twist>> getTwists() {
        return twists;
    }

    @Override
    public void stop() {
        this.isRunning = false;
        descr.cancel(true);
        challenges.cancel(true);
        scoreboardRunner.cancel(true);
        doChests.cancel(true);
        for (SurvChallenge challenge : running) {
            challenge.finish();
        }
        HandlerList.unregisterAll(deathListener);
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void run() {
        this.isRunning = true;
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        scoreboard = manager.getNewScoreboard();
        objective = scoreboard.registerNewObjective("alive", "hasNotDied", "\u00A74Alive");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        descrStage.set(0);
        spawn = new Location(Bukkit.getWorld("world"), 4.5, 68, -94.5, 0, 0);
        final World w = Bukkit.getWorld("world");
        chests.addAll(Arrays
            .asList(new Location(w, -234, 62, -315), new Location(w, -182, 62, 148), new Location(w, 307, 62, -231),
                new Location(w, 663, 61, -103), new Location(w, 950, 56, 261), new Location(w, 269, 86, -1150),
                new Location(w, 852, 60, -812), new Location(w, 944, 54, -605), new Location(w, -474, 74, -264),
                new Location(w, -35, 54, 444), new Location(w, 157, 67, 461), new Location(w, -318, 60, 229),
                new Location(w, 0, 64, 920), new Location(w, 460, 77, 1065), new Location(w, -744, 62, -772),
                new Location(w, -699, 78, -334)));
        descr = Executors.newSingleThreadScheduledExecutor()
            .scheduleAtFixedRate(new SurvivalDescribe(), 1L, 4L, TimeUnit.SECONDS);
    }

    private static final class SurvivalDescribe implements Runnable {

        @Override
        public void run() {
            try {
                Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                    switch (descrStage.get()) {
                        case 0:
                            Bukkit.broadcastMessage("\u00A76Welcome to the Survival Challenge");
                            break;
                        case 1:
                            Bukkit.broadcastMessage(
                                "\u00A77The purpose of this challenge is to create your dream survival base, "
                                    + "while surviving our \u00A74twists\u00A77 and completing \u00A73mini challenges\u00A77 that'll earn you points!");
                            break;
                        case 2:
                            Bukkit.broadcastMessage(
                                "\u00A77Basic survival gear and creative blocks have been provided to you along with \u00A75portals\u00A77 at the \u00A72spawn\u00A77. "
                                    + "You may find \u00A7cchests\u00A77 on your travels and coordinates for \u00A76treasure chests\u00A77 will be released over time."
                                    + " Well worth collecting!");
                            break;
                        case 3:
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&4Challenge 1: &fPVP."), "", 0, 70,
                                    20);
                            }
                            break;
                        case 4:
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&4Top 5 prize: &fEscape a Twist"),
                                    "", 0, 70, 20);
                            }
                            break;
                        case 5:
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&4Top prize: &fTotem of Undying"),
                                    "", 0, 70, 20);
                            }
                            break;
                        case 6:
                            int i = 1;
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (i > 15) {
                                    break;
                                }
                                i++;
                                Score score = objective.getScore(p.getName());
                                objective.setRenderType(RenderType.HEARTS);
                                inScoreboard.add(p);
                                score.setScore(1);
                            }
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.teleport(new Location(Bukkit.getWorld("pvp"), 156, 4, 335));
                                p.setScoreboard(scoreboard);
                            }
                            break;
                        case 7:
                            descr.cancel(false);
                            Thread t = new Thread(() -> {
                                try {
                                    Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&55"), "", 0, 70, 20);
                                            p.getInventory().clear();
                                            p.getInventory().addItem(new ItemStack(Material.IRON_HELMET, 1));
                                            p.getInventory().addItem(new ItemStack(Material.IRON_CHESTPLATE, 1));
                                            p.getInventory().addItem(new ItemStack(Material.IRON_LEGGINGS, 1));
                                            p.getInventory().addItem(new ItemStack(Material.IRON_BOOTS, 1));
                                            p.getInventory().addItem(new ItemStack(Material.IRON_SWORD, 1));
                                            p.getInventory().addItem(new ItemStack(Material.IRON_AXE, 1));
                                            p.getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
                                            p.getInventory().addItem(new ItemStack(Material.BOW, 1));
                                            p.getInventory().addItem(new ItemStack(Material.CROSSBOW, 1));
                                            p.getInventory().addItem(new ItemStack(Material.ARROW, 128));
                                        }
                                    });
                                    Thread.sleep(1000L);
                                    Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&24"), "", 0, 70, 20);
                                        }
                                    });
                                    Thread.sleep(1000L);
                                    Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&13"), "", 0, 70, 20);
                                        }
                                    });
                                    Thread.sleep(1000L);
                                    Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&32"), "", 0, 70, 20);
                                        }
                                    });
                                    Thread.sleep(1000L);
                                    Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&f1"), "", 0, 70, 20);
                                        }
                                    });
                                    Thread.sleep(1000L);
                                    Random r = new Random();
                                    while (MissionGameAware.queueTwists.getAndDecrement() > 0) {
                                        MissionGameAware.plugin.getTwistLocks()
                                            .queueTwist(twists.get(r.nextInt(twists.size())).get());
                                    }
                                    Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&4Battle!"), "", 0, 70,
                                                20);
                                        }
                                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                                            "rg flag pvp_area -w pvp  pvp allow");
                                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                                            "rg flag spawnspawn -w world exit allow");
                                        Bukkit.getServer()
                                            .dispatchCommand(Bukkit.getConsoleSender(), "gamerule keepInventory true");
                                        Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, () -> {
                                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "rg reload");
                                        }, 5L);
                                    });
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
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    private static final class PVPListener implements Listener {
        @EventHandler
        public void onPlayerDeath(PlayerDeathEvent event) {
            handle(event.getEntity());
        }

        void handle(Player pl) {
            players.remove(pl);
            if (inScoreboard.contains(pl)) {
                scoreboard.resetScores(pl.getName());
                if (players.size() > 15) {
                    for (Player p : players) {
                        if (!inScoreboard.contains(p)) {
                            Score score = objective.getScore(p.getName());
                            inScoreboard.add(p);
                            score.setScore(1);
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.setScoreboard(scoreboard);
                            }
                            break;
                        }
                    }
                }
            }
            if (players.size() == 5) {
                objective.setDisplayName("\u00A74Final 5!");
                top5 = new ArrayList<>(players);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&4Final 5!"), "", 0, 70, 20);
                }
            }
            if (players.size() == 1) {
                pvpwinner = players.get(0);
                HandlerList.unregisterAll(pvpListener);
                new Thread(() -> {
                    Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "&6We have a winner!"),
                                ChatColor.translateAlternateColorCodes('&', "&fCongratulations &5" + pvpwinner.getName()), 0,
                                70, 20);
                        }
                    });
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.isDead()) {
                                deadWhenDone.add(p);
                                continue;
                            }
                            p.getInventory().clear();
                            p.setGameMode(GameMode.SURVIVAL);
                            p.teleport(spawn);
                            p.sendTitle("", ChatColor.translateAlternateColorCodes('&',
                                "&fTake 5 minutes to explore, and then the challenges begin!"), 0, 70, 20);
                            scoreboard.resetScores(p.getName());
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
                    challenges =
                        Executors.newSingleThreadScheduledExecutor().schedule(new ChallengeSelect(), 5L, TimeUnit.MINUTES);
                    doChests = Executors.newSingleThreadScheduledExecutor()
                        .scheduleAtFixedRate(new DoChests(), 6L, 6L, TimeUnit.MINUTES);
                    scoreboardRunner = Executors.newSingleThreadScheduledExecutor()
                        .scheduleAtFixedRate(new ScoreboardUpdate(), 10L, 10L, TimeUnit.SECONDS);
                    Bukkit.getPluginManager().registerEvents(deathListener = new DeathListener(), MissionGameAware.plugin);
                    Bukkit.getPluginManager().registerEvents(new TwistListener(), MissionGameAware.plugin);
                    objective.setDisplayName("\u00A74Top 15");
                    objective.setRenderType(RenderType.INTEGER);
                }).start();
            }
        }

        @EventHandler
        public void onRespawn(PlayerRespawnEvent event) {
            if (players.size() > 1) {
                event.getPlayer().teleport(new Location(Bukkit.getWorld("pvp"), 156, 4, 335));
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
            } else {
                Player p = event.getPlayer();
                p.getInventory().clear();
                p.setGameMode(GameMode.SURVIVAL);
                p.teleport(spawn);
                p.sendTitle("", ChatColor
                        .translateAlternateColorCodes('&', "&fTake 5 minutes to explore, and then the challenges begin!"), 0, 70,
                    20);
                if (top5.contains(p)) {
                    ItemStack namedSnowball = new ItemStack(Material.SNOWBALL);
                    ItemMeta meta = namedSnowball.getItemMeta();
                    meta.setDisplayName("Escape a Twist");
                    meta.setLore(lore);
                    namedSnowball.setItemMeta(meta);
                    p.getInventory().addItem(namedSnowball);
                    ItemStack diamonds = new ItemStack(Material.DIAMOND, 16);
                    p.getInventory().addItem(diamonds);
                }
                if (pvpwinner.equals(p)) {
                    p.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
                    p.getInventory().addItem(new ItemStack(Material.DIAMOND, 16));
                    pvpwinner.getInventory().addItem(new ItemStack(Material.EXPERIENCE_BOTTLE, 32));
                }
            }
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


    private static final class DoChests implements Runnable {

        @Override
        public void run() {
            if (chests.size() == 0) {
                doChests.cancel(false);
                return;
            }
            Location chest = chests.remove(0);
            Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                Bukkit.broadcastMessage(
                    "\u00A76Treasure Chest \u00A7flocated at: " + chest.getBlockX() + "," + chest.getBlockZ());
            });
        }
    }


    public static final class ChallengeSelect implements Runnable {

        @Override
        public void run() {
            try {
                Class<? extends SurvChallenge> challenge;
                Random r = new Random();
                int size = challengeList.size();
                while (!(challenge = challengeList.get(r.nextInt(size))).getSuperclass().equals(SingleChallenge.class)
                    && !challenge.getSuperclass().getSuperclass().equals(SingleChallenge.class) || !run
                    .contains(challenge)) {
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
                challenges = Executors.newSingleThreadScheduledExecutor()
                    .schedule(new ChallengeSelect(), Math.round(60 + 240 * Math.random()), TimeUnit.SECONDS);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    private static final class DeathListener implements Listener {

        @EventHandler
        public void onRespawn(PlayerRespawnEvent e) {
            final Player p = e.getPlayer();
            if (deadWhenDone.contains(p)) {
                p.getInventory().clear();
                p.setGameMode(GameMode.SURVIVAL);
                p.teleport(spawn);
                p.sendTitle("", ChatColor
                        .translateAlternateColorCodes('&', "&fTake 5 minutes to explore, and then the challenges begin!"), 0, 70,
                    20);
                if (top5.contains(p)) {
                    ItemStack namedSnowball = new ItemStack(Material.SNOWBALL);
                    ItemMeta meta = namedSnowball.getItemMeta();
                    meta.setDisplayName("Escape a Twist");
                    meta.setLore(lore);
                    namedSnowball.setItemMeta(meta);
                    p.getInventory().addItem(namedSnowball);
                    ItemStack diamonds = new ItemStack(Material.DIAMOND, 16);
                    p.getInventory().addItem(diamonds);
                }
                if (pvpwinner.equals(p)) {
                    p.getInventory().addItem(new ItemStack(Material.TOTEM_OF_UNDYING, 1));
                    p.getInventory().addItem(new ItemStack(Material.DIAMOND, 16));
                    pvpwinner.getInventory().addItem(new ItemStack(Material.EXPERIENCE_BOTTLE, 32));
                }
                deadWhenDone.remove(p);
                return;
            }
            p.sendTitle(ChatColor.translateAlternateColorCodes('&', "You died! You will remain in spectator for 30 seconds."),
                "", 0, 70, 20);
            p.setGameMode(GameMode.SPECTATOR);
            Bukkit.getScheduler().runTaskLater(MissionGameAware.plugin, () -> {
                Location l = p.getLocation();
                Block b = l.getBlock();
                if (b.isEmpty()) {
                    //int y = 0;
                    for (int y = l.getBlockY(); y > 0; y--) {
                        if (testLoc(p, l, b, y)) {
                            return;
                        }
                    }
                } else {
                    for (int y = l.getBlockY(); y < 255; y++) {
                        if (testLoc(p, l, b, y)) {
                            return;
                        }
                    }
                }
                p.setGameMode(GameMode.SURVIVAL);
            }, 30 * 20L);
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

        @EventHandler
        public void onSpecTeleport(PlayerTeleportEvent e) {
            if (e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE) {
                e.setCancelled(true);
            }
        }
    }


    private static final class ScoreboardUpdate implements Runnable {

        @Override
        public void run() {
            List<Map.Entry<Player, Integer>> list = new ArrayList<>(playerScores.entrySet());
            list.sort(Map.Entry.comparingByValue());

            Bukkit.getScheduler().runTask(MissionGameAware.plugin, () -> {
                int i = 1;
                for (Map.Entry<Player, Integer> entry : list) {
                    if (i > 15) {
                        break;
                    }
                    i++;
                    Score score = objective.getScore(entry.getKey().getName());
                    score.setScore(entry.getValue());
                }
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.setScoreboard(scoreboard);
                }
            });
        }
    }
}
