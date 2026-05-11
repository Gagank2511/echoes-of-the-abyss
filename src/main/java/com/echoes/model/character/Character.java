package com.echoes.model.character;

import java.util.Objects;

import com.echoes.model.combat.AttackResult;
import com.echoes.model.combat.AttackStrategy;

/**
 * Abstract base class for every entity that participates in combat.
 *
 * <p>Encapsulates the core statistics shared by all combatants: health,
 * attack power, defence, and an injected {@link AttackStrategy}. Delegating
 * the attack calculation to a strategy object means combat behaviour can
 * be changed independently of the character hierarchy — this is the
 * <em>Strategy</em> design pattern in action.</p>
 *
 * <p>Subclasses extend this with domain-specific behaviour. {@link Player}
 * adds experience tracking and an inventory; enemy subclasses add drop
 * rewards and special abilities.</p>
 *
 * @author Your Name
 * @version 1.0
 */
public abstract class Character {

    private final String name;
    private int maxHealth;
    private int currentHealth;
    private int attackPower;
    private int defence;
    private AttackStrategy attackStrategy;

    /**
     * Constructs a character with fully specified base statistics.
     *
     * @param name           display name — must not be blank
     * @param maxHealth      maximum hit points — must be greater than zero
     * @param attackPower    base attack power passed to the strategy
     * @param defence        flat damage reduction applied to incoming hits
     * @param attackStrategy the combat strategy used when this character attacks
     * @throws IllegalArgumentException if name is blank or maxHealth is zero or below
     * @throws NullPointerException     if name or attackStrategy is null
     */
    protected Character(final String name,
                        final int maxHealth,
                        final int attackPower,
                        final int defence,
                        final AttackStrategy attackStrategy) {
        if (Objects.requireNonNull(name, "Name must not be null").isBlank()) {
            throw new IllegalArgumentException("Character name must not be blank.");
        }
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("Max health must be > 0, got: " + maxHealth);
        }
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.attackPower = attackPower;
        this.defence = Math.max(0, defence);
        this.attackStrategy = Objects.requireNonNull(attackStrategy,
                "Attack strategy must not be null");
    }

    /**
     * Performs an attack against the target using the current strategy.
     * Note: this method does not apply damage — the caller is responsible
     * for passing the net damage result to {@link #takeDamage(int)}.
     *
     * @param target the defending character
     * @return the full result of the attack including damage and description
     */
    public AttackResult performAttack(final Character target) {
        return attackStrategy.execute(this, target);
    }

    /**
     * Reduces current health by the given amount, floored at zero.
     * Negative values are treated as zero — this method cannot heal.
     *
     * @param amount the damage to apply
     */
    public void takeDamage(final int amount) {
        this.currentHealth = Math.max(0, this.currentHealth - Math.max(0, amount));
    }

    /**
     * Restores current health by the given amount, capped at max health.
     * Negative values are treated as zero — this method cannot damage.
     *
     * @param amount the hit points to restore
     */
    public void heal(final int amount) {
        this.currentHealth = Math.min(maxHealth,
                this.currentHealth + Math.max(0, amount));
    }

    /**
     * Returns true if this character has any remaining hit points.
     *
     * @return true if the character is alive
     */
    public boolean isAlive() {
        return currentHealth > 0;
    }

    /**
     * Returns a short description of this character for display purposes.
     * Each subclass provides a meaningful, flavourful implementation.
     *
     * @return the character description
     */
    public abstract String getDescription();

    /** @return the character's display name */
    public String getName() { return name; }

    /** @return the character's maximum hit points */
    public int getMaxHealth() { return maxHealth; }

    /** @return the character's current hit points */
    public int getCurrentHealth() { return currentHealth; }

    /**
     * Returns the effective attack power. Subclasses may override this
     * to include temporary or permanent bonuses on top of the base value.
     *
     * @return effective attack power
     */
    public int getAttackPower() { return attackPower; }

    /** @return the character's defence rating */
    public int getDefence() { return defence; }

    /** @param maxHealth new maximum health — must be greater than zero */
    protected void setMaxHealth(final int maxHealth) {
        if (maxHealth <= 0) throw new IllegalArgumentException("Max health must be > 0");
        this.maxHealth = maxHealth;
    }

    /** @param attackPower new base attack power */
    protected void setAttackPower(final int attackPower) {
        this.attackPower = Math.max(0, attackPower);
    }

    /** @param defence new defence value */
    protected void setDefence(final int defence) {
        this.defence = Math.max(0, defence);
    }

    /** @param strategy the replacement attack strategy — must not be null */
    protected void setAttackStrategy(final AttackStrategy strategy) {
        this.attackStrategy = Objects.requireNonNull(strategy,
                "Attack strategy must not be null");
    }

    @Override
    public String toString() {
        return String.format("%s [HP: %d/%d  ATK: %d  DEF: %d]",
                name, currentHealth, maxHealth, getAttackPower(), defence);
    }
}
