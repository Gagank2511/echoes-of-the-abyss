package com.echoes.model.character.enemy.boss;

import com.echoes.model.character.Character;
import com.echoes.model.character.enemy.EnemyType;
import com.echoes.model.combat.AttackResult;
import com.echoes.model.combat.MagicAttackStrategy;
import com.echoes.util.DiceRoller;
import com.echoes.util.GameConstants;

/**
 * A powerful undead sorcerer boss with soul-draining abilities.
 *
 * <p>Liches are magic-focused bosses with high attack power. Their special
 * ability drains health from the player and heals the lich, making prolonged
 * fights risky.</p>
 */
public final class Lich extends BossEnemy {

    private static final double SOUL_DRAIN_LEECH = 0.4;

    /**
     * Constructs a lich scaled to the given floor level.
     *
     * @param floorLevel the dungeon floor level for stat scaling
     */
    public Lich(final int floorLevel) {
        super(
            "The Lich",
            80 + (floorLevel * 15),
            12 + (floorLevel * 4),
            4,
            new MagicAttackStrategy(),
            EnemyType.LICH,
            80 + (floorLevel * 25),
            25 + (floorLevel * 10)
        );
    }

    @Override
    public String getSpecialAbilityName() {
        return "Soul Drain";
    }

    @Override
    protected AttackResult executeSpecialAbility(final Character target) {
        int rawDamage = DiceRoller.roll(4, 8) + getAttackPower();
        int defenceReduction = (int) (target.getDefence()
                * GameConstants.MAGIC_DEFENCE_PENETRATION);
        int netDamage = Math.max(GameConstants.MINIMUM_DAMAGE,
                rawDamage - defenceReduction);
        int healAmount = (int) (netDamage * SOUL_DRAIN_LEECH);
        heal(healAmount);
        String desc = "The Lich channels Soul Drain, dealing " + netDamage
                + " necrotic damage and leeching " + healAmount + " HP!";
        return new AttackResult(rawDamage, netDamage, false, false, desc);
    }

    @Override
    public String getDescription() {
        return "A skeletal archmage draped in tattered obsidian robes. "
                + "Dark energy crackles around its outstretched fingers.";
    }
}