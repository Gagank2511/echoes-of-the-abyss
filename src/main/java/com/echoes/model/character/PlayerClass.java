package com.echoes.model.character;

import com.echoes.model.combat.AttackStrategy;
import com.echoes.model.combat.MagicAttackStrategy;
import com.echoes.model.combat.MeleeAttackStrategy;
import com.echoes.model.combat.RangedAttackStrategy;

public enum PlayerClass {

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

    public abstract AttackStrategy createAttackStrategy();
    public abstract String getClassDescription();

    public String getDisplayName() { return displayName; }
    public int getMaxHealth() { return maxHealth; }
    public int getAttackPower() { return attackPower; }
    public int getDefence() { return defence; }
}