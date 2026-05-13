package com.echoes.model.item.consumable;

/**
 * Represents the effect of using a consumable item.
 *
 * <p>This class encapsulates the various bonuses or changes that occur when
 * a consumable is used, including health restoration and stat boosts.</p>
 */
public final class ItemEffect {

    private final int healthRestored;
    private final int temporaryAttackBoost;
    private final int permanentAttackBoost;
    private final int permanentDefenceBoost;
    private final String description;

    /**
     * Constructs an item effect with the specified bonuses.
     *
     * @param healthRestored        amount of health restored
     * @param temporaryAttackBoost  temporary attack bonus
     * @param permanentAttackBoost  permanent attack bonus
     * @param permanentDefenceBoost permanent defence bonus
     * @param description           textual description of the effect
     */
    public ItemEffect(final int healthRestored,
                      final int temporaryAttackBoost,
                      final int permanentAttackBoost,
                      final int permanentDefenceBoost,
                      final String description) {
        this.healthRestored = healthRestored;
        this.temporaryAttackBoost = temporaryAttackBoost;
        this.permanentAttackBoost = permanentAttackBoost;
        this.permanentDefenceBoost = permanentDefenceBoost;
        this.description = description;
    }

    /**
     * Gets the amount of health restored by this effect.
     *
     * @return health restored
     */
    public int getHealthRestored() { return healthRestored; }

    /**
     * Gets the temporary attack boost from this effect.
     *
     * @return temporary attack boost
     */
    public int getTemporaryAttackBoost() { return temporaryAttackBoost; }

    /**
     * Gets the permanent attack boost from this effect.
     *
     * @return permanent attack boost
     */
    public int getPermanentAttackBoost() { return permanentAttackBoost; }

    /**
     * Gets the permanent defence boost from this effect.
     *
     * @return permanent defence boost
     */
    public int getPermanentDefenceBoost() { return permanentDefenceBoost; }

    /**
     * Gets the description of this effect.
     *
     * @return the description
     */
    public String getDescription() { return description; }
}