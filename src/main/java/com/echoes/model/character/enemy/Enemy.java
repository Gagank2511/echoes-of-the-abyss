package com.echoes.model.character.enemy;

import com.echoes.model.character.Character;
import com.echoes.model.combat.AttackResult;
import com.echoes.model.combat.AttackStrategy;

/**
 * Abstract base class for all dungeon enemies.
 *
 * <p>Extends {@link Character} with enemy-specific attributes: the experience
 * and gold awarded to the player on defeat, and a type classification used
 * by factories and display logic.</p>
 *
 * <p>{@link #takeTurn(Character)} is the <strong>Template Method</strong>.
 * It defines a fixed algorithm for an enemy's turn:</p>
 * <ol>
 *   <li>Call {@link #onBeforeTurn()} — a hook for regeneration or self-buffs.</li>
 *   <li>If {@link #shouldUseSpecialAbility()} returns true, delegate to
 *       {@link #executeSpecialAbility(Character)}.</li>
 *   <li>Otherwise, perform a standard attack.</li>
 * </ol>
 * <p>Subclasses customise behaviour by overriding only the hook methods,
 * not the overall structure. This prevents code duplication and ensures
 * the turn sequence is always consistent.</p>
 *
 * @author Your Name
 * @version 1.0
 */
public abstract class Enemy extends Character {

    private final EnemyType enemyType;
    private final int xpReward;
    private final int goldReward;

    /**
     * Constructs an enemy with full stat and reward specification.
     *
     * @param name           display name
     * @param maxHealth      maximum hit points
     * @param attackPower    base attack power
     * @param defence        defence rating
     * @param attackStrategy the attack strategy to use
     * @param enemyType      type classification for factory and display use
     * @param xpReward       experience points awarded on defeat
     * @param goldReward     gold awarded on defeat
     */
    protected Enemy(final String name,
                    final int maxHealth,
                    final int attackPower,
                    final int defence,
                    final AttackStrategy attackStrategy,
                    final EnemyType enemyType,
                    final int xpReward,
                    final int goldReward) {
        super(name, maxHealth, attackPower, defence, attackStrategy);
        this.enemyType = enemyType;
        this.xpReward = xpReward;
        this.goldReward = goldReward;
    }

    // ── Template Method ──────────────────────────────────────────────────────

    /**
     * Executes this enemy's full turn — the <strong>Template Method</strong>.
     * The overall structure is fixed here; subclasses customise it via hooks.
     *
     * @param target the character this enemy is attacking
     * @return the result of the action taken this turn
     */
    public final AttackResult takeTurn(final Character target) {
        onBeforeTurn();
        if (shouldUseSpecialAbility()) {
            return executeSpecialAbility(target);
        }
        return performAttack(target);
    }

    // ── Template Method hooks ─────────────────────────────────────────────────

    /**
     * Hook called at the start of this enemy's turn before any attack.
     * Override to implement regeneration or pre-turn effects.
     * Default implementation does nothing.
     */
    protected void onBeforeTurn() { }

    /**
     * Determines whether the special ability should be used this turn.
     * Default always returns false — standard enemies never use special abilities.
     *
     * @return true to use the special ability instead of a normal attack
     */
    protected boolean shouldUseSpecialAbility() {
        return false;
    }

    /**
     * Executes the special ability against the target.
     * Default falls back to a standard attack.
     * Boss subclasses override this with unique behaviours.
     *
     * @param target the attack target
     * @return the result of the special ability
     */
    protected AttackResult executeSpecialAbility(final Character target) {
        return performAttack(target);
    }

    /** @return this enemy's type classification */
    public EnemyType getEnemyType() { return enemyType; }

    /** @return experience points awarded when this enemy is defeated */
    public int getXpReward() { return xpReward; }

    /** @return gold awarded when this enemy is defeated */
    public int getGoldReward() { return goldReward; }
}
