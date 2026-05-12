package com.echoes.util;

/**
 * Central store for all game-wide numeric and textual constants.
 *
 * <p>Keeping magic numbers here rather than scattering them across the
 * codebase makes balancing straightforward — a single change in one
 * place propagates everywhere it is needed. All fields are
 * {@code public static final} so no instance is ever required.</p>
 */
public final class GameConstants {

    // ── Dungeon layout ─────────────────────────────────────────────────────
    /** Total number of dungeon floors generated at startup. */
    public static final int FLOOR_COUNT = 5;

    /** Number of rooms per floor, including the entrance and boss room. */
    public static final int ROOMS_PER_FLOOR = 5;

    // ── Player progression ─────────────────────────────────────────────────
    /** Base experience required to reach level 2. */
    public static final int BASE_XP_THRESHOLD = 100;

    /** Multiplier applied to the XP threshold after each level-up. */
    public static final double XP_SCALING_FACTOR = 1.5;

    /** Hit points gained on each level-up. */
    public static final int HP_PER_LEVEL = 10;

    /** Attack power gained on each level-up. */
    public static final int ATTACK_PER_LEVEL = 2;

    /** Defence gained on each level-up. */
    public static final int DEFENCE_PER_LEVEL = 1;

    // ── Combat calculations ────────────────────────────────────────────────
    /** Minimum damage dealt by any attack, regardless of the target's defence. */
    public static final int MINIMUM_DAMAGE = 1;

    /** The d20 roll value that produces a critical hit. */
    public static final int CRITICAL_HIT_ROLL = 20;

    /** Multiplier applied to net damage when a critical hit occurs. */
    public static final double CRITICAL_MULTIPLIER = 2.0;

    /**
     * Rolls of 1 through this value on a d20 are misses for ranged attacks.
     * A value of 3 means rolls 1, 2, and 3 all miss.
     */
    public static final int RANGED_MISS_THRESHOLD = 3;

    /**
     * Proportion of the target's defence that a magic attack ignores.
     * A value of 0.5 means magic penetrates half of any armour.
     */
    public static final double MAGIC_DEFENCE_PENETRATION = 0.5;

    /** Probability (0.0 to 1.0) that a standard flee attempt succeeds. */
    public static final double FLEE_SUCCESS_CHANCE = 0.5;

    /**
     * HP ratio below which a boss activates its special ability.
     * For example, 0.4 triggers the ability below 40 percent health.
     */
    public static final double BOSS_SPECIAL_THRESHOLD = 0.4;

    // ── Item effects ────────────────────────────────────────────────────────
    /** Hit points restored by consuming a Health Potion. */
    public static final int HEALTH_POTION_HEAL = 40;

    /** Temporary attack bonus from an Energy Elixir — lasts one combat. */
    public static final int ENERGY_ELIXIR_BOOST = 4;

    /** Permanent attack increase granted by a Power Crystal. */
    public static final int POWER_CRYSTAL_BONUS = 3;

    /** Permanent defence increase granted by a Stone Amulet. */
    public static final int STONE_AMULET_BONUS = 2;

    // ── Inventory ──────────────────────────────────────────────────────────
    /** Maximum number of items the player may carry at any one time. */
    public static final int MAX_INVENTORY_SIZE = 8;

    /** Number of Health Potions every new player starts with. */
    public static final int STARTING_POTIONS = 2;

    private GameConstants() {
        // Utility class — instantiation not permitted
    }
}
