package com.echoes.model.combat;

/**
 * Immutable value object representing the outcome of a combat encounter.
 *
 * <p>This class encapsulates the result of a fight, including whether the player
 * won or lost, experience and gold gained, and a summary description for display.</p>
 */
public final class CombatResult {

    private final CombatOutcome outcome;
    private final int experienceGained;
    private final int goldGained;
    private final String summary;

    /**
     * Constructs a combat result with the specified outcomes.
     *
     * @param outcome          the outcome of the combat (victory or defeat)
     * @param experienceGained experience points gained from the combat
     * @param goldGained       gold gained from the combat
     * @param summary          a textual summary of the combat result
     */
    public CombatResult(final CombatOutcome outcome,
                        final int experienceGained,
                        final int goldGained,
                        final String summary) {
        this.outcome = outcome;
        this.experienceGained = experienceGained;
        this.goldGained = goldGained;
        this.summary = summary;
    }

    /**
     * Gets the outcome of the combat.
     *
     * @return the combat outcome
     */
    public CombatOutcome getOutcome() { return outcome; }

    /**
     * Gets the experience gained from the combat.
     *
     * @return the experience gained
     */
    public int getExperienceGained() { return experienceGained; }

    /**
     * Gets the gold gained from the combat.
     *
     * @return the gold gained
     */
    public int getGoldGained() { return goldGained; }

    /**
     * Gets a summary description of the combat result.
     *
     * @return the summary
     */
    public String getSummary() { return summary; }
}