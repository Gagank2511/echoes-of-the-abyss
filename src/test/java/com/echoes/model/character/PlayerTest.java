package com.echoes.model.character;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.echoes.model.item.consumable.ItemEffect;
import com.echoes.util.GameConstants;

@DisplayName("Player Tests")
class PlayerTest {

    private Player warrior;

    @BeforeEach
    void setUp() {
        warrior = new Player("Gareth", PlayerClass.WARRIOR);
    }

    @Test
    @DisplayName("Player initialises with correct class statistics")
    void player_initialisesWithCorrectStats() {
        assertEquals(PlayerClass.WARRIOR.getMaxHealth(), warrior.getMaxHealth());
        assertEquals(PlayerClass.WARRIOR.getAttackPower(), warrior.getAttackPower());
        assertEquals(PlayerClass.WARRIOR.getDefence(), warrior.getDefence());
        assertEquals(1, warrior.getLevel());
        assertEquals(0, warrior.getExperience());
        assertEquals(0, warrior.getGold());
    }

    @Test
    @DisplayName("takeDamage reduces health correctly")
    void takeDamage_reducesHealth() {
        warrior.takeDamage(20);
        assertEquals(warrior.getMaxHealth() - 20, warrior.getCurrentHealth());
    }

    @Test
    @DisplayName("takeDamage cannot reduce health below zero")
    void takeDamage_cannotGoBelowZero() {
        warrior.takeDamage(warrior.getMaxHealth() + 999);
        assertEquals(0, warrior.getCurrentHealth());
        assertFalse(warrior.isAlive());
    }

    @Test
    @DisplayName("heal restores health correctly")
    void heal_restoresHealth() {
        warrior.takeDamage(50);
        warrior.heal(30);
        assertEquals(warrior.getMaxHealth() - 20, warrior.getCurrentHealth());
    }

    @Test
    @DisplayName("heal cannot exceed max health")
    void heal_cannotExceedMax() {
        warrior.heal(500);
        assertEquals(warrior.getMaxHealth(), warrior.getCurrentHealth());
    }

    @Test
    @DisplayName("gainExperience triggers level-up at threshold")
    void gainExperience_triggersLevelUp() {
        int initialLevel = warrior.getLevel();
        warrior.gainExperience(GameConstants.BASE_XP_THRESHOLD);
        assertEquals(initialLevel + 1, warrior.getLevel());
    }

    @Test
    @DisplayName("gainGold increases gold correctly")
    void gainGold_increasesGold() {
        warrior.gainGold(100);
        assertEquals(100, warrior.getGold());
    }

    @Test
    @DisplayName("gainGold ignores negative values")
    void gainGold_ignoresNegative() {
        warrior.gainGold(-10);
        assertEquals(0, warrior.getGold());
    }

    @Test
    @DisplayName("Temporary attack boost increases effective attack power")
    void temporaryBoost_increasesAttackPower() {
        int base = warrior.getAttackPower();
        warrior.applyItemEffect(new ItemEffect(
                0, GameConstants.ENERGY_ELIXIR_BOOST, 0, 0, "test"));
        assertEquals(base + GameConstants.ENERGY_ELIXIR_BOOST,
                warrior.getAttackPower());
        assertTrue(warrior.hasActiveBoost());
    }

    @Test
    @DisplayName("clearTemporaryBoosts resets attack power to base")
    void clearTemporaryBoosts_resetsAttackPower() {
        int base = warrior.getAttackPower();
        warrior.applyItemEffect(new ItemEffect(
                0, GameConstants.ENERGY_ELIXIR_BOOST, 0, 0, "test"));
        warrior.clearTemporaryBoosts();
        assertEquals(base, warrior.getAttackPower());
        assertFalse(warrior.hasActiveBoost());
    }

    @Test
    @DisplayName("Player constructor throws on blank name")
    void constructor_throwsOnBlankName() {
        assertThrows(IllegalArgumentException.class,
                () -> new Player("  ", PlayerClass.WARRIOR));
    }

    @Test
    @DisplayName("Player constructor throws on null class")
    void constructor_throwsOnNullClass() {
        assertThrows(NullPointerException.class,
                () -> new Player("Hero", null));
    }
}