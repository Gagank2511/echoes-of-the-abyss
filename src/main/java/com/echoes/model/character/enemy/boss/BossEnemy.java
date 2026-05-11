package com.echoes.model.character.enemy.boss;

import com.echoes.model.character.Character;
import com.echoes.model.character.enemy.Enemy;
import com.echoes.model.character.enemy.EnemyType;
import com.echoes.model.combat.AttackResult;
import com.echoes.model.combat.AttackStrategy;
import com.echoes.util.GameConstants;

/**
 * Abstract base class for all floor-boss enemies.
 *
 * <p>Extends the Template Method hooks defined in {@link Enemy}:</p>
 * <ul>
 *   <li>{@link #shouldUseSpecialAbility()} is overridden to return true
 *       when health drops below {@link GameConstants#BOSS_SPECIAL_THRESHOLD}
 *       — the boss "enrages" at low health.</li>
 *   <li>{@link #executeSpecialAbility(Character)} is declared abstract here
 *       so each concrete boss provides its own unique attack behaviour.</li>
 * </ul>
 *
 * <p>This demonstrates the Template Method pattern at two levels: the common
 * enrage logic lives here in {@code BossEnemy}, while the specific ability
 * content is deferred further to each concrete subclass.</p>
 *
 * @author Your Name
 * @version 1.0
 */
public abstract class BossEnemy extends Enemy {

    /**
     * Constructs a boss enemy with the given stats.
     *
     * @param name           display name
     * @param maxHealth      maximum hit points
     * @param attackPower    base attack power
     * @param defence        defence rating
     * @param attackStrategy standard attack strategy
     * @param enemyType      boss type classification
     * @param xpReward       XP awarded on defeat
     * @param goldReward     gold awarded on defeat
     */
    protected BossEnemy(final String name,
                        final int maxHealth,
                        final int attackPower,
                        final int defence,
                        final AttackStrategy attackStrategy,
                        final EnemyType enemyType,
                        final int xpReward,
                        final int goldReward) {
        super(name, maxHealth, attackPower, defence, attackStrategy,
              enemyType, xpReward, goldReward);
    }

    /**
     * Activates the special ability once health drops below the enrage
     * threshold defined in {@link GameConstants#BOSS_SPECIAL_THRESHOLD}.
     *
     * @return true if the boss is enraged and should use its special ability
     */
    @Override
    protected boolean shouldUseSpecialAbility() {
        return (double) getCurrentHealth() / getMaxHealth()
                < GameConstants.BOSS_SPECIAL_THRESHOLD;
    }

    /**
     * Returns the display name of this boss's special ability.
     *
     * @return ability name (e.g. "Seismic Slam")
     */
    public abstract String getSpecialAbilityName();

    /**
     * Executes this boss's unique special ability against the target.
     * Each concrete subclass must provide a distinct implementation.
     *
     * @param target the target character
     * @return the result of the special ability
     */
    @Override
    protected abstract AttackResult executeSpecialAbility(Character target);
}
