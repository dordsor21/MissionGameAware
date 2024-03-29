package me.dordsor21.MissionGameAware.util;

public class Lists {

    public enum Farms {
        WHEAT, POTATOES, CARROTS, PUMPKIN_STEM, MELON_STEM
    }


    public enum EnemyOkayToSpawn {
        CREEPER, ENDERMAN, PILLAGER, SKELETON, SPIDER, ZOMBIE
    }


    public enum SomeEffects {
        GLOWING_15_3, HUNGER_15_2, INVISIBILITY_15_2, JUMP_15_6, LEVITATION_5_1, POISON_15_1, SLOW_15_2, SLOWUSCRDIGGING_15_2,
        UNLUCK_15_2, WEAKNESS_15_2
    }


    public enum NiceItems {
        GRANITE, DIORITE, ANDESITE, DIRT, COBBLESTONE, OAK_PLANKS, SPRUCE_PLANKS, BIRCH_PLANKS, JUNGLE_PLANKS, ACACIA_PLANKS,
        DARK_OAK_PLANKS, CRIMSON_PLANKS, WARPED_PLANKS, OAK_SAPLING, SPRUCE_SAPLING, BIRCH_SAPLING, JUNGLE_SAPLING,
        ACACIA_SAPLING, DARK_OAK_SAPLING, SAND, RED_SAND, GRAVEL, GOLD_ORE, IRON_ORE, NETHER_GOLD_ORE, OAK_LOG, SPRUCE_LOG,
        BIRCH_LOG, JUNGLE_LOG, ACACIA_LOG, DARK_OAK_LOG, STRIPPED_OAK_LOG, STRIPPED_SPRUCE_LOG, STRIPPED_BIRCH_LOG,
        STRIPPED_JUNGLE_LOG, STRIPPED_ACACIA_LOG, STRIPPED_DARK_OAK_LOG, STRIPPED_CRIMSON_STEM, STRIPPED_WARPED_STEM,
        STRIPPED_OAK_WOOD, STRIPPED_SPRUCE_WOOD, STRIPPED_BIRCH_WOOD, STRIPPED_JUNGLE_WOOD, STRIPPED_ACACIA_WOOD,
        STRIPPED_DARK_OAK_WOOD, STRIPPED_CRIMSON_HYPHAE, STRIPPED_WARPED_HYPHAE, OAK_WOOD, SPRUCE_WOOD, BIRCH_WOOD,
        JUNGLE_WOOD, ACACIA_WOOD, DARK_OAK_WOOD, OAK_LEAVES, SPRUCE_LEAVES, BIRCH_LEAVES, JUNGLE_LEAVES, ACACIA_LEAVES,
        DARK_OAK_LEAVES, GLASS, LAPIS_BLOCK, DISPENSER, SANDSTONE, CHISELED_SANDSTONE, CUT_SANDSTONE, FERN, DEAD_BUSH,
        PISTON, DANDELION, POPPY, BLUE_ORCHID, ALLIUM, AZURE_BLUET, RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP,
        OXEYE_DAISY, SUGAR_CANE, KELP, BAMBOO, GOLD_BLOCK, IRON_BLOCK, OAK_SLAB, SPRUCE_SLAB, BIRCH_SLAB, JUNGLE_SLAB,
        ACACIA_SLAB, DARK_OAK_SLAB, STONE_SLAB, SMOOTH_STONE_SLAB, SANDSTONE_SLAB, CUT_SANDSTONE_SLAB, COBBLESTONE_SLAB,
        BRICK_SLAB, STONE_BRICK_SLAB, NETHER_BRICK_SLAB, QUARTZ_SLAB, RED_SANDSTONE_SLAB, CUT_RED_SANDSTONE_SLAB,
        PURPUR_SLAB, PRISMARINE_SLAB, PRISMARINE_BRICK_SLAB, DARK_PRISMARINE_SLAB, SMOOTH_QUARTZ, SMOOTH_RED_SANDSTONE,
        SMOOTH_SANDSTONE, SMOOTH_STONE, BRICKS, TNT, BOOKSHELF, OBSIDIAN, TORCH, OAK_STAIRS, CHEST, DIAMOND_BLOCK,
        CRAFTING_TABLE, FURNACE, LADDER, RAIL, COBBLESTONE_STAIRS, LEVER, STONE_PRESSURE_PLATE, OAK_PRESSURE_PLATE,
        SPRUCE_PRESSURE_PLATE, BIRCH_PRESSURE_PLATE, JUNGLE_PRESSURE_PLATE, ACACIA_PRESSURE_PLATE, DARK_OAK_PRESSURE_PLATE,
        REDSTONE_TORCH, CACTUS, CLAY, OAK_FENCE, SPRUCE_FENCE, BIRCH_FENCE, JUNGLE_FENCE, ACACIA_FENCE, DARK_OAK_FENCE,
        PUMPKIN, NETHERRACK, SOUL_SAND, SOUL_SOIL, BASALT, GLOWSTONE, JACK_O_LANTERN, OAK_TRAPDOOR, SPRUCE_TRAPDOOR,
        BIRCH_TRAPDOOR, JUNGLE_TRAPDOOR, ACACIA_TRAPDOOR, DARK_OAK_TRAPDOOR, STONE_BRICKS, CHISELED_STONE_BRICKS, IRON_BARS,
        GLASS_PANE, MELON, VINE, OAK_FENCE_GATE, SPRUCE_FENCE_GATE, BIRCH_FENCE_GATE, JUNGLE_FENCE_GATE, ACACIA_FENCE_GATE,
        DARK_OAK_FENCE_GATE, BRICK_STAIRS, STONE_BRICK_STAIRS, LILY_PAD, NETHER_BRICKS, CHISELED_NETHER_BRICKS,
        NETHER_BRICK_FENCE, NETHER_BRICK_STAIRS, ENCHANTING_TABLE, REDSTONE_LAMP, SANDSTONE_STAIRS, TRIPWIRE_HOOK,
        EMERALD_BLOCK, SPRUCE_STAIRS, BIRCH_STAIRS, JUNGLE_STAIRS, COBBLESTONE_WALL, BRICK_WALL, RED_SANDSTONE_WALL,
        GRANITE_WALL, STONE_BRICK_WALL, NETHER_BRICK_WALL, ANDESITE_WALL, RED_NETHER_BRICK_WALL, SANDSTONE_WALL,
        END_STONE_BRICK_WALL, DIORITE_WALL, BLACKSTONE_WALL, STONE_BUTTON, OAK_BUTTON, SPRUCE_BUTTON, BIRCH_BUTTON,
        JUNGLE_BUTTON, ACACIA_BUTTON, DARK_OAK_BUTTON, ANVIL, REDSTONE_BLOCK, HOPPER, CHISELED_QUARTZ_BLOCK, QUARTZ_BLOCK,
        QUARTZ_BRICKS, QUARTZ_PILLAR, QUARTZ_STAIRS, IRON_TRAPDOOR, HAY_BLOCK, COAL_BLOCK, ACACIA_STAIRS, DARK_OAK_STAIRS,
        GRASS_PATH, SUNFLOWER, LILAC, LARGE_FERN, SEA_LANTERN, RED_SANDSTONE, CHISELED_RED_SANDSTONE, CUT_RED_SANDSTONE,
        RED_SANDSTONE_STAIRS, MAGMA_BLOCK, RED_NETHER_BRICKS, BONE_BLOCK, CONDUIT, POLISHED_GRANITE_STAIRS,
        SMOOTH_RED_SANDSTONE_STAIRS, POLISHED_DIORITE_STAIRS, STONE_STAIRS, SMOOTH_SANDSTONE_STAIRS, SMOOTH_QUARTZ_STAIRS,
        GRANITE_STAIRS, ANDESITE_STAIRS, RED_NETHER_BRICK_STAIRS, POLISHED_ANDESITE_STAIRS, DIORITE_STAIRS,
        POLISHED_GRANITE_SLAB, SMOOTH_RED_SANDSTONE_SLAB, POLISHED_DIORITE_SLAB, SMOOTH_SANDSTONE_SLAB, SMOOTH_QUARTZ_SLAB,
        GRANITE_SLAB, ANDESITE_SLAB, RED_NETHER_BRICK_SLAB, POLISHED_ANDESITE_SLAB, DIORITE_SLAB, SCAFFOLDING, IRON_DOOR,
        OAK_DOOR, SPRUCE_DOOR, BIRCH_DOOR, JUNGLE_DOOR, ACACIA_DOOR, DARK_OAK_DOOR, REPEATER, COMPARATOR, FLINT_AND_STEEL,
        APPLE, BOW, ARROW, COAL, CHARCOAL, DIAMOND, IRON_INGOT, GOLD_INGOT, WOODEN_SWORD, WOODEN_SHOVEL, WOODEN_PICKAXE,
        WOODEN_AXE, WOODEN_HOE, STONE_SWORD, STONE_SHOVEL, STONE_PICKAXE, STONE_AXE, STONE_HOE, GOLDEN_SWORD, GOLDEN_SHOVEL,
        GOLDEN_PICKAXE, GOLDEN_AXE, GOLDEN_HOE, IRON_SWORD, IRON_SHOVEL, IRON_PICKAXE, IRON_AXE, IRON_HOE, DIAMOND_SWORD,
        DIAMOND_SHOVEL, DIAMOND_PICKAXE, DIAMOND_AXE, DIAMOND_HOE, STICK, BOWL, STRING, FEATHER, GUNPOWDER, WHEAT, BREAD,
        LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_BOOTS, CHAINMAIL_HELMET, CHAINMAIL_CHESTPLATE, CHAINMAIL_BOOTS,
        IRON_HELMET, IRON_CHESTPLATE, IRON_BOOTS, DIAMOND_HELMET, DIAMOND_CHESTPLATE, DIAMOND_BOOTS, GOLDEN_HELMET,
        GOLDEN_CHESTPLATE, GOLDEN_BOOTS, FLINT, PORKCHOP, COOKED_PORKCHOP, BUCKET, WATER_BUCKET, LAVA_BUCKET, MINECART,
        REDSTONE, OAK_BOAT, LEATHER, BRICK, CLAY_BALL, PAPER, BOOK, CHEST_MINECART, FURNACE_MINECART, FISHING_ROD,
        GLOWSTONE_DUST, INK_SAC, COCOA_BEANS, LAPIS_LAZULI, BONE_MEAL, BONE, SUGAR, FILLED_MAP, SHEARS, BEEF, COOKED_BEEF,
        CHICKEN, COOKED_CHICKEN, ROTTEN_FLESH, BLAZE_ROD, GOLD_NUGGET, NETHER_WART, GLASS_BOTTLE, SPIDER_EYE, CAULDRON,
        WRITABLE_BOOK, WRITTEN_BOOK, EMERALD, ITEM_FRAME, FLOWER_POT, CARROT, POTATO, BAKED_POTATO, POISONOUS_POTATO, MAP,
        CARROT_ON_A_STICK, ENCHANTED_BOOK, NETHER_BRICK, QUARTZ, TNT_MINECART, HOPPER_MINECART, RABBIT, COOKED_RABBIT,
        MUTTON, COOKED_MUTTON, BEETROOT, BEETROOT_SEEDS, BEETROOT_SOUP, SHIELD, SPRUCE_BOAT, BIRCH_BOAT, JUNGLE_BOAT,
        ACACIA_BOAT, DARK_OAK_BOAT, IRON_NUGGET, CROSSBOW, LOOM, COMPOSTER, BARREL, SMOKER, BLAST_FURNACE, CARTOGRAPHY_TABLE,
        FLETCHING_TABLE, GRINDSTONE, LECTERN, SMITHING_TABLE, STONECUTTER, BELL, LANTERN
    }
}
