package com.echoes.model.combat;

import com.echoes.model.character.Character;
import com.echoes.util.DiceRoller;
import com.echoes.util.GameConstants;

public final class RangedAttackStrategy implements AttackStrategy {

    @Override
    public AttackResult execute(final Character attacker, final Character target) {
        final int accuracyRoll = DiceRoller.rollD20();

        if (accuracyRoll <= GameConstants.RANGED_MISS_THRESHOLD) {
            return new AttackResult(0, 0, false, true,
                    attacker.getName() + "'s shot flies wide and misses!");
        }

        final boolean isCritical = accuracyRoll == GameConstants.CRITICAL_HIT_ROLL;
        final int rawDamage = DiceRoller.roll(2, 8) + attacker.getAttackPower();
        int netDamage = Math.max(GameConstants.MINIMUM_DAMAGE,
                rawDamage - target.getDefence());

        if (isCritical) {
            netDamage = (int) (netDamage * GameConstants.CRITICAL_MULTIPLIER);
        }

        final String description = isCritical
            ? attacker.getName() + " fires a perfect precision shot for "
                + netDamage + " damage!"
            : attacker.getName() + " looses an arrow, hitting for "
                + netDamage + " damage.";

        return new AttackResult(rawDamage, netDamage, isCritical, false, description);
    }
}