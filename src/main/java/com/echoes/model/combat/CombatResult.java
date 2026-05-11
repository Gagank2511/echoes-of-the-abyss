package com.echoes.model.combat;

public final class CombatResult {

    private final CombatOutcome outcome;
    private final int experienceGained;
    private final int goldGained;
    private final String summary;

    public CombatResult(final CombatOutcome outcome,
                        final int experienceGained,
                        final int goldGained,
                        final String summary) {
        this.outcome = outcome;
        this.experienceGained = experienceGained;
        this.goldGained = goldGained;
        this.summary = summary;
    }

    public CombatOutcome getOutcome() { return outcome; }
    public int getExperienceGained() { return experienceGained; }
    public int getGoldGained() { return goldGained; }
    public String getSummary() { return summary; }
}