package com.echoes.model.combat;

/**
 * Enumeration of possible outcomes for a combat encounter.
 *
 * <p>These outcomes determine what happens after a fight, such as experience
 * gain, progression, or game over conditions.</p>
 */
public enum CombatOutcome {

    /** The enemy was defeated by the player. */
    ENEMY_DEFEATED,
    /** The player was defeated by the enemy. */
    PLAYER_DEFEATED,
    /** The player successfully fled from combat. */
    PLAYER_FLED,
    /** Combat is still ongoing. */
    ONGOING
}