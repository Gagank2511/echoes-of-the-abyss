package com.echoes.model.character.enemy.boss;

import com.echoes.model.character.Character;
import com.echoes.model.character.enemy.EnemyType;
import com.echoes.model.combat.AttackResult;
import com.echoes.model.combat.MagicAttackStrategy;
import com.echoes.util.DiceRoller;
import com.echoes.util.GameConstants;

public final class AbyssDragon extends BossEnemy {

    private static final double VOID_FIRE_ARMOUR_FRACTION = 0.25;

    public AbyssDragon(final int floorLevel) {
        super(
            "Abyss Dragon",
            120 + (floorLevel * 20),
            15 + (floorLevel * 5),
            7,
            new MagicAttackStrategy(),
            EnemyType.ABYSS_DRAGON,
            150 + (floorLevel * 40),
            50 + (floorLevel * 15)
        );
    }

    @Override
    public String getSpecialAbilityName() {
        return "Abyss Flame";
    }

    @Override
    protected AttackResult executeSpecialAbility(final Character target) {
        int rawDamage = DiceRoller.roll(5, 10) + getAttackPower();
        int defenceReduction = (int) (target.getDefence() * VOID_FIRE_ARMOUR_FRACTION);
        int netDamage = Math.max(GameConstants.MINIMUM_DAMAGE,
                rawDamage - defenceReduction);
        boolean isCritical = DiceRoller.rollD20() == GameConstants.CRITICAL_HIT_ROLL;
        if (isCritical) {
            netDamage = (int) (netDamage * GameConstants.CRITICAL_MULTIPLIER);
        }
        String desc = isCritical
            ? "The Abyss Dragon unleashes a CATASTROPHIC Abyss Flame for "
                + netDamage + " void damage!"
            : "The Abyss Dragon breathes Abyss Flame, scorching you for "
                + netDamage + " void damage!";
        return new AttackResult(rawDamage, netDamage, isCritical, false, desc);
    }

    @Override
    public String getDescription() {
        return "A colossal dragon of darkest obsidian, wreathed in violet flame. "
                + "Ancient beyond reckoning, it regards you with absolute contempt.";
    }
}