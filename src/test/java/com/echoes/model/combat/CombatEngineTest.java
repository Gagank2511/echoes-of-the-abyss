package com.echoes.model.combat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.echoes.model.character.Player;
import com.echoes.model.character.PlayerClass;
import com.echoes.model.character.enemy.Enemy;
import com.echoes.model.character.enemy.Goblin;
import com.echoes.util.DiceRoller;

@DisplayName("CombatEngine Tests")
class CombatEngineTest {

    private CombatEngine engine;
    private Player player;
    private Enemy goblin;

    @BeforeEach
    void setUp() {
        DiceRoller.seed(99L);
        engine = new CombatEngine();
        player = new Player("Test Hero", PlayerClass.WARRIOR);
        goblin = new Goblin(1);
    }

    @Test
    @DisplayName("executePlayerAttack returns non-null result")
    void executePlayerAttack_returnsResult() {
        AttackResult result = engine.executePlayerAttack(player, goblin);
        assertNotNull(result);
        assertNotNull(result.getDescription());
    }

    @Test
    @DisplayName("executePlayerAttack deals damage to enemy")
    void executePlayerAttack_dealsDamage() {
        int hpBefore = goblin.getCurrentHealth();
        AttackResult result = engine.executePlayerAttack(player, goblin);
        assertTrue(goblin.getCurrentHealth() < hpBefore
                || result.isMiss());
    }

    @Test
    @DisplayName("executeEnemyTurn deals damage to player")
    void executeEnemyTurn_dealsDamageToPlayer() {
        int hpBefore = player.getCurrentHealth();
        AttackResult result = engine.executeEnemyTurn(goblin, player);
        assertTrue(player.getCurrentHealth() < hpBefore
                || result.isMiss());
    }

    @Test
    @DisplayName("Net damage is never negative")
    void netDamage_neverNegative() {
        for (int i = 0; i < 50; i++) {
            AttackResult result = engine.executePlayerAttack(player, goblin);
            assertTrue(result.getNetDamage() >= 0);
        }
    }

    @Test
    @DisplayName("attemptFlee always fails against a boss")
    void attemptFlee_alwaysFailsForBoss() {
        for (int i = 0; i < 20; i++) {
            assertFalse(engine.attemptFlee(true));
        }
    }

    @Test
    @DisplayName("buildVictoryResult awards XP and gold to player")
    void buildVictoryResult_awardsRewards() {
        int goldBefore = player.getGold();
        CombatResult result = engine.buildVictoryResult(player, goblin);
        assertEquals(CombatOutcome.ENEMY_DEFEATED, result.getOutcome());
        assertTrue(result.getExperienceGained() > 0);
        assertTrue(result.getGoldGained() > 0);
        assertTrue(player.getGold() > goldBefore);
    }

    @Test
    @DisplayName("buildVictoryResult summary is not blank")
    void buildVictoryResult_summaryNotBlank() {
        CombatResult result = engine.buildVictoryResult(player, goblin);
        assertFalse(result.getSummary().isBlank());
    }

    @Test
    @DisplayName("getPreTurnEffect returns empty for non-Troll enemies")
    void getPreTurnEffect_nonTroll_returnsEmpty() {
        String effect = engine.getPreTurnEffect(goblin);
        assertTrue(effect.isBlank());
    }
}
