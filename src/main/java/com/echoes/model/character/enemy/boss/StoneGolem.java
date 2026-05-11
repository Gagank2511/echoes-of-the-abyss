package com.echoes.model.character.enemy.boss;

import com.echoes.model.character.Character;
import com.echoes.model.character.enemy.EnemyType;
import com.echoes.model.combat.AttackResult;
import com.echoes.model.combat.MeleeAttackStrategy;
import com.echoes.util.DiceRoller;
import com.echoes.util.GameConstants;

public final class StoneGolem extends BossEnemy {

    public StoneGolem(final int floorLevel) {
        super(
            "Stone Golem",
            90 + (floorLevel * 15),
            10 + (floorLevel * 3),
            6,
            new MeleeAttackStrategy(),
            EnemyType.STONE_GOLEM,
            60 + (floorLevel * 20),
            20 + (floorLevel * 8)
        );
    }

    @Override
    public String getSpecialAbilityName() {
        return "Seismic Slam";
    }

    @Override
    protected AttackResult executeSpecialAbility(final Character target) {
        int rawDamage = DiceRoller.roll(3, 10) + getAttackPower();
        int netDamage = Math.max(GameConstants.MINIMUM_DAMAGE, rawDamage);
        String desc = "The Stone Golem unleashes a Seismic Slam, cracking the "
                + "ground for " + netDamage + " damage — ignoring your armour!";
        return new AttackResult(rawDamage, netDamage, false, false, desc);
    }

    @Override
    public String getDescription() {
        return "A towering construct of ancient enchanted stone. Each step "
                + "shakes the dungeon floor. Its fists are the size of boulders.";
    }
}