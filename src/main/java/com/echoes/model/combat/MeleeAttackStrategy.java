package com.echoes.model.combat;

import com.echoes.model.character.Character;
import com.echoes.util.DiceRoller;
import com.echoes.util.GameConstants;

public final class MeleeAttackStrategy implements AttackStrategy {

    @Override
    public AttackResult execute(final Character attacker, final Character target) {
        final int accuracyRoll = DiceRoller.rollD20();
        final boolean isCritical = accuracyRoll == GameConstants.CRITICAL_HIT_ROLL;

        final int rawDamage = DiceRoller.roll(2, 6) + attacker.getAttackPower();
        int netDamage = Math.max(GameConstants.MINIMUM_DAMAGE,
                rawDamage - target.getDefence());

        if (isCritical) {
            netDamage = (int) (netDamage * GameConstants.CRITICAL_MULTIPLIER);
        }

        final String description = isCritical
            ? attacker.getName() + " lands a crushing critical strike for "
                + netDamage + " damage!"
            : attacker.getName() + " swings hard, dealing "
                + netDamage + " damage.";

        return new AttackResult(rawDamage, netDamage, isCritical, false, description);
    }
}