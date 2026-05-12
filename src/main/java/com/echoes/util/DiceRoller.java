package com.echoes.util;

import java.util.Random;

/**
 * Stateless utility class providing standard dice-rolling operations.
 *
 * <p>All methods are static. The internal {@link Random} instance can be
 * replaced with a seeded version via {@link #seed(long)} to produce
 * deterministic results in unit tests — ensuring tests are repeatable
 * and not dependent on chance.</p>
 */
public final class DiceRoller {

    private static Random random = new Random();

    private DiceRoller() {
        // Utility class — instantiation not permitted
    }

    /**
     * Replaces the internal random source with a seeded instance.
     * Call this at the start of a test to make dice rolls deterministic.
     *
     * @param seed the seed value to use
     */
    public static void seed(final long seed) {
        random = new Random(seed);
    }

    /**
     * Rolls {@code numDice} dice each with {@code sides} faces and returns
     * the total of all results.
     *
     * @param numDice number of dice to roll — must be at least 1
     * @param sides   number of faces per die — must be at least 1
     * @return the sum of all dice rolled
     * @throws IllegalArgumentException if either argument is less than 1
     */
    public static int roll(final int numDice, final int sides) {
        if (numDice < 1 || sides < 1) {
            throw new IllegalArgumentException(
                    "numDice and sides must both be >= 1. Got: "
                            + numDice + "d" + sides);
        }
        int total = 0;
        for (int i = 0; i < numDice; i++) {
            total += random.nextInt(sides) + 1;
        }
        return total;
    }

    /**
     * Rolls a single twenty-sided die (d20).
     *
     * @return a value between 1 and 20 inclusive
     */
    public static int rollD20() {
        return roll(1, 20);
    }

    /**
     * Returns {@code true} with the given probability.
     * Used for percentage-chance checks such as flee attempts.
     *
     * @param probability a value between 0.0 (never) and 1.0 (always)
     * @return true if the check succeeds
     */
    public static boolean chance(final double probability) {
        return random.nextDouble() < probability;
    }
}
