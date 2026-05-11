package com.echoes.model.item.consumable;

public final class ItemEffect {

    private final int healthRestored;
    private final int temporaryAttackBoost;
    private final int permanentAttackBoost;
    private final int permanentDefenceBoost;
    private final String description;

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

    public int getHealthRestored() { return healthRestored; }
    public int getTemporaryAttackBoost() { return temporaryAttackBoost; }
    public int getPermanentAttackBoost() { return permanentAttackBoost; }
    public int getPermanentDefenceBoost() { return permanentDefenceBoost; }
    public String getDescription() { return description; }
}