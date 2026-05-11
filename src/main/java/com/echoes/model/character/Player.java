package com.echoes.model.character;

import java.util.Objects;

import com.echoes.model.inventory.Inventory;
import com.echoes.model.item.consumable.ItemEffect;
import com.echoes.util.GameConstants;

/**
 * Represents the player-controlled hero within the dungeon.
 *
 * <p>Extends {@link Character} with progression mechanics: experience points,
 * levels, and gold. A temporary attack boost from consumable items such as
 * the Energy Elixir is tracked separately from the base attack power and
 * cleared at the end of each combat via {@link #clearTemporaryBoosts()}.</p>
 *
 * <p>The override of {@link #getAttackPower()} ensures that all attack
 * strategies automatically use the effective (boosted) value without any
 * special-casing in the combat logic itself.</p>
 *
 * @author Your Name
 * @version 1.0
 */
public final class Player extends Character {

    private final PlayerClass playerClass;
    private final Inventory inventory;

    private int level;
    private int experience;
    private int experienceToNextLevel;
    private int gold;

    /** Temporary attack bonus — reset to zero when combat ends. */
    private int temporaryAttackBoost;

    /**
     * Constructs a new player with the given name and class.
     * Starting statistics are taken directly from the chosen {@link PlayerClass}.
     *
     * @param name        the hero's chosen name — must not be blank
     * @param playerClass the chosen class — must not be null
     */
    public Player(final String name, final PlayerClass playerClass) {
        super(
            name,
            Objects.requireNonNull(playerClass, "Player class must not be null")
                   .getMaxHealth(),
            playerClass.getAttackPower(),
            playerClass.getDefence(),
            playerClass.createAttackStrategy()
        );
        this.playerClass = playerClass;
        this.inventory = new Inventory();
        this.level = 1;
        this.experience = 0;
        this.experienceToNextLevel = GameConstants.BASE_XP_THRESHOLD;
        this.gold = 0;
        this.temporaryAttackBoost = 0;
    }

    /**
     * Returns the effective attack power, including any active temporary boost.
     * Overrides the base method so that strategies always receive the correct value.
     *
     * @return base attack power plus any temporary bonus currently active
     */
    @Override
    public int getAttackPower() {
        return super.getAttackPower() + temporaryAttackBoost;
    }

    @Override
    public String getDescription() {
        return String.format(
                "Level %d %s — HP: %d/%d  ATK: %d  DEF: %d  XP: %d/%d  Gold: %d",
                level, playerClass.getDisplayName(),
                getCurrentHealth(), getMaxHealth(),
                getAttackPower(), getDefence(),
                experience, experienceToNextLevel,
                gold);
    }

    /**
     * Awards experience points and triggers level-ups as required.
     * Multiple level-ups in a single call are handled correctly.
     *
     * @param xp experience to award — non-positive values are ignored
     */
    public void gainExperience(final int xp) {
        if (xp <= 0) return;
        this.experience += xp;
        while (this.experience >= this.experienceToNextLevel) {
            this.experience -= this.experienceToNextLevel;
            levelUp();
        }
    }

    /**
     * Increases the player's level by one and improves base statistics.
     * Current health is partially increased to reward the level-up without
     * acting as a free full heal.
     */
    private void levelUp() {
        level++;
        setMaxHealth(getMaxHealth() + GameConstants.HP_PER_LEVEL);
        heal(GameConstants.HP_PER_LEVEL);
        setAttackPower(super.getAttackPower() + GameConstants.ATTACK_PER_LEVEL);
        setDefence(getDefence() + GameConstants.DEFENCE_PER_LEVEL);
        experienceToNextLevel = (int) (experienceToNextLevel
                * GameConstants.XP_SCALING_FACTOR);
    }

    /**
     * Awards the given amount of gold.
     *
     * @param amount gold to add — non-positive values are ignored
     */
    public void gainGold(final int amount) {
        if (amount > 0) this.gold += amount;
    }

    /**
     * Applies an {@link ItemEffect} to this player, handling healing,
     * temporary attack boosts, and permanent stat increases.
     *
     * @param effect the effect to apply — must not be null
     */
    public void applyItemEffect(final ItemEffect effect) {
        Objects.requireNonNull(effect, "ItemEffect must not be null");
        if (effect.getHealthRestored() > 0) heal(effect.getHealthRestored());
        if (effect.getTemporaryAttackBoost() > 0)
            temporaryAttackBoost += effect.getTemporaryAttackBoost();
        if (effect.getPermanentAttackBoost() > 0)
            setAttackPower(super.getAttackPower() + effect.getPermanentAttackBoost());
        if (effect.getPermanentDefenceBoost() > 0)
            setDefence(getDefence() + effect.getPermanentDefenceBoost());
    }

    /**
     * Clears any temporary attack boosts accumulated during combat.
     * Must be called when a combat session ends.
     */
    public void clearTemporaryBoosts() {
        temporaryAttackBoost = 0;
    }

    /** @return the player's chosen class */
    public PlayerClass getPlayerClass() { return playerClass; }

    /** @return the player's current level */
    public int getLevel() { return level; }

    /** @return experience accumulated since the last level-up */
    public int getExperience() { return experience; }

    /** @return experience required to reach the next level */
    public int getExperienceToNextLevel() { return experienceToNextLevel; }

    /** @return gold currently carried */
    public int getGold() { return gold; }

    /** @return the player's inventory */
    public Inventory getInventory() { return inventory; }

    /** @return true if a temporary attack boost is currently active */
    public boolean hasActiveBoost() { return temporaryAttackBoost > 0; }
}
