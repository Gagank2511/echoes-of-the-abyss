package com.echoes.model.combat;

import com.echoes.model.character.Player;
import com.echoes.model.character.enemy.Enemy;
import com.echoes.model.character.enemy.Troll;
import com.echoes.util.DiceRoller;
import com.echoes.util.GameConstants;

/**
 * Stateless service that orchestrates all combat calculations.
 *
 * <p>Keeping combat arithmetic in one place makes balance tuning
 * straightforward — any change to damage, flee probability, or reward
 * calculation is made here and takes effect everywhere. The combat state
 * machine lives in {@link com.echoes.pattern.state.CombatState}; this
 * class only performs calculations and returns structured results.</p>
 */
public final class CombatEngine {

    /**
     * Executes the player's attack against the given enemy.
     * Damage is applied to the enemy immediately after calculation.
     *
     * @param player the attacker
     * @param enemy  the defender
     * @return the attack result, for display by the renderer
     */
    public AttackResult executePlayerAttack(final Player player, final Enemy enemy) {
        AttackResult result = player.performAttack(enemy);
        enemy.takeDamage(result.getNetDamage());
        return result;
    }

    /**
     * Executes the enemy's turn via the Template Method
     * {@link Enemy#takeTurn(com.echoes.model.character.Character)}.
     * Damage is applied to the player immediately.
     *
     * @param enemy  the attacking enemy
     * @param player the defending player
     * @return the attack result, for display by the renderer
     */
    public AttackResult executeEnemyTurn(final Enemy enemy, final Player player) {
        AttackResult result = enemy.takeTurn(player);
        player.takeDamage(result.getNetDamage());
        return result;
    }

    /**
     * Resolves a flee attempt. Bosses can never be fled from.
     * Standard enemies have a fixed probability of allowing escape.
     *
     * @param isBoss true if the current enemy is a boss
     * @return true if the flee attempt succeeds
     */
    public boolean attemptFlee(final boolean isBoss) {
        if (isBoss) return false;
        return DiceRoller.chance(GameConstants.FLEE_SUCCESS_CHANCE);
    }

    /**
     * Builds the final {@link CombatResult} after the enemy is defeated,
     * awarding XP and gold to the player.
     *
     * @param player the victorious player
     * @param enemy  the defeated enemy
     * @return a populated victory result
     */
    public CombatResult buildVictoryResult(final Player player, final Enemy enemy) {
        int xp = enemy.getXpReward();
        int gold = enemy.getGoldReward();
        player.gainExperience(xp);
        player.gainGold(gold);
        String summary = String.format(
                "%s has been defeated! You gain %d XP and %d gold.",
                enemy.getName(), xp, gold);
        return new CombatResult(CombatOutcome.ENEMY_DEFEATED, xp, gold, summary);
    }

    /**
     * Returns the pre-turn narrative for a Troll's regeneration effect,
     * or an empty string for all other enemy types.
     *
     * @param enemy the enemy whose pre-turn effect to retrieve
     * @return the regen narrative string, or empty string
     */
    public String getPreTurnEffect(final Enemy enemy) {
        if (enemy instanceof Troll troll) {
            return troll.getLastTurnEffect();
        }
        return "";
    }
}
