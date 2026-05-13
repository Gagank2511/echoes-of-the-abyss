package com.echoes.model.character;

import com.echoes.model.combat.AttackStrategy;
import com.echoes.model.combat.MagicAttackStrategy;
import com.echoes.model.combat.MeleeAttackStrategy;
import com.echoes.model.combat.RangedAttackStrategy;

/**
 * Enumeration of available player classes, each with unique statistics and combat styles.
 *
 * <p>Each class defines base health, attack power, defence, and an attack strategy
 * that determines how combat is resolved. Classes are implemented as enum constants
 * with overridden methods for their specific behaviour.</p>
 */
public enum PlayerClass {

    /** A melee-focused class with high health and defence. */
    WARRIOR("Warrior", 110, 9, 5) {
        @Override
        public AttackStrategy createAttackStrategy() {
            return new MeleeAttackStrategy();
        }
        @Override
        public String getClassDescription() {
            return "A seasoned warrior who excels in close-quarters combat. "
                    + "High endurance and reliable, crushing strikes.";
        }
    },

    /** A magic-focused class with high attack but low defence. */
    MAGE("Mage", 75, 13, 1) {
        @Override
        public AttackStrategy createAttackStrategy() {
            return new MagicAttackStrategy();
        }
        @Override
        public String getClassDescription() {
            return "A master of the arcane arts. Fragile but devastating — "
                    + "magic ignores half of any armour.";
        }
    },

    /** A ranged-focused class with balanced stats. */
    RANGER("Ranger", 90, 10, 3) {
        @Override
        public AttackStrategy createAttackStrategy() {
            return new RangedAttackStrategy();
        }
        @Override
        public String getClassDescription() {
            return "A swift and precise archer. Balanced in all areas with "
                    + "the potential for deadly precision strikes from range.";
        }
    };

    private final String displayName;
    private final int maxHealth;
    private final int attackPower;
    private final int defence;

    PlayerClass(final String displayName,
                final int maxHealth,
                final int attackPower,
                final int defence) {
        this.displayName = displayName;
        this.maxHealth = maxHealth;
        this.attackPower = attackPower;
        this.defence = defence;
    }

    /**
     * Creates the attack strategy specific to this class.
     *
     * @return the attack strategy for this class
     */
    public abstract AttackStrategy createAttackStrategy();

    /**
     * Gets a description of this class's playstyle and abilities.
     *
     * @return the class description
     */
    public abstract String getClassDescription();

    /**
     * Gets the display name of this class.
     *
     * @return the display name
     */
    public String getDisplayName() { return displayName; }

    /**
     * Gets the maximum health for this class.
     *
     * @return the max health
     */
    public int getMaxHealth() { return maxHealth; }

    /**
     * Gets the base attack power for this class.
     *
     * @return the attack power
     */
    public int getAttackPower() { return attackPower; }

    /**
     * Gets the base defence for this class.
     *
     * @return the defence
     */
    public int getDefence() { return defence; }
}