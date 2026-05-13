package com.echoes.model.combat;

import com.echoes.model.character.Character;
import com.echoes.util.DiceRoller;
import com.echoes.util.GameConstants;

/**
 * Attack strategy for magic combat, using arcane spells that penetrate defence.
 *
 * <p>Magic attacks ignore a portion of the target's defence, making them effective
 * against heavily armoured opponents. They have higher base damage potential
 * but follow the same critical hit mechanics as other strategies.</p>
 */
public final class MagicAttackStrategy implements AttackStrategy {

    @Override
    public AttackResult execute(final Character attacker, final Character target) {
        final int accuracyRoll = DiceRoller.rollD20();
        final boolean isCritical = accuracyRoll == GameConstants.CRITICAL_HIT_ROLL;

        final int rawDamage = DiceRoller.roll(3, 6) + attacker.getAttackPower();
        final int defenceReduction = (int) (target.getDefence()
                * GameConstants.MAGIC_DEFENCE_PENETRATION);
        int netDamage = Math.max(GameConstants.MINIMUM_DAMAGE,
                rawDamage - defenceReduction);

        if (isCritical) {
            netDamage = (int) (netDamage * GameConstants.CRITICAL_MULTIPLIER);
        }

        final String description = isCritical
            ? attacker.getName() + " channels a devastating arcane surge for "
                + netDamage + " magic damage!"
            : attacker.getName() + " weaves a spell, inflicting "
                + netDamage + " magic damage.";

        return new AttackResult(rawDamage, netDamage, isCritical, false, description);
    }
}