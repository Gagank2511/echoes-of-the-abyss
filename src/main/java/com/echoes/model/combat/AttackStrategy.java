package com.echoes.model.combat;

import com.echoes.model.character.Character;

/**
 * Strategy interface for attack algorithms — the <em>Strategy</em> pattern.
 *
 * <p>Each concrete strategy ({@link MeleeAttackStrategy},
 * {@link RangedAttackStrategy}, {@link MagicAttackStrategy}) encapsulates
 * a distinct damage-calculation algorithm. {@link com.echoes.model.character.Character}
 * holds a reference to a strategy and delegates to it at runtime.</p>
 *
 * <p>This design means combat behaviour can be swapped independently of
 * the character hierarchy. Adding a new attack type — for example, a poison
 * or fire attack — requires only a new class implementing this interface.
 * No existing character or combat code needs to change.</p>
 * @see MeleeAttackStrategy
 * @see RangedAttackStrategy
 * @see MagicAttackStrategy
 */
@FunctionalInterface
public interface AttackStrategy {

    /**
     * Calculates and returns the result of an attack from {@code attacker}
     * against {@code target}.
     *
     * <p>Implementations must <strong>not</strong> modify the health of either
     * character — damage application is the caller's responsibility. This keeps
     * strategies side-effect-free and easy to test.</p>
     *
     * @param attacker the attacking character
     * @param target   the defending character
     * @return a fully populated {@link AttackResult}
     */
    AttackResult execute(Character attacker, Character target);
}
