package com.echoes.pattern.factory;

import com.echoes.model.character.enemy.Enemy;
import com.echoes.model.character.enemy.EnemyType;
import com.echoes.model.character.enemy.Goblin;
import com.echoes.model.character.enemy.Skeleton;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EnemyFactory Tests")
class EnemyFactoryTest {

    private final EnemyFactory standardFactory = new StandardEnemyFactory();
    private final EnemyFactory eliteFactory    = new EliteEnemyFactory();

    @ParameterizedTest
    @EnumSource(EnemyType.class)
    @DisplayName("StandardEnemyFactory creates enemy for every EnemyType")
    void standardFactory_createsEnemyForEachType(final EnemyType type) {
        Enemy enemy = standardFactory.create(type, 1);
        assertNotNull(enemy);
        assertEquals(type, enemy.getEnemyType());
    }

    @ParameterizedTest
    @EnumSource(EnemyType.class)
    @DisplayName("EliteEnemyFactory creates enemy for every EnemyType")
    void eliteFactory_createsEnemyForEachType(final EnemyType type) {
        Enemy enemy = eliteFactory.create(type, 1);
        assertNotNull(enemy);
        assertEquals(type, enemy.getEnemyType());
    }

    @Test
    @DisplayName("Elite factory creates stronger enemies than standard")
    void eliteFactory_createsStrongerEnemies() {
        Enemy standard = standardFactory.create(EnemyType.GOBLIN, 2);
        Enemy elite    = eliteFactory.create(EnemyType.GOBLIN, 2);
        assertTrue(elite.getMaxHealth() > standard.getMaxHealth());
    }

    @Test
    @DisplayName("StandardFactory creates correct Goblin type")
    void standardFactory_createsGoblin() {
        Enemy enemy = standardFactory.create(EnemyType.GOBLIN, 1);
        assertInstanceOf(Goblin.class, enemy);
    }

    @Test
    @DisplayName("StandardFactory creates correct Skeleton type")
    void standardFactory_createsSkeleton() {
        Enemy enemy = standardFactory.create(EnemyType.SKELETON, 1);
        assertInstanceOf(Skeleton.class, enemy);
    }

    @Test
    @DisplayName("Created enemies are alive with full health")
    void createdEnemy_isAliveWithFullHealth() {
        Enemy enemy = standardFactory.create(EnemyType.TROLL, 1);
        assertTrue(enemy.isAlive());
        assertEquals(enemy.getMaxHealth(), enemy.getCurrentHealth());
    }

    @Test
    @DisplayName("Enemy rewards are positive")
    void createdEnemy_hasPositiveRewards() {
        Enemy enemy = standardFactory.create(EnemyType.DRAGON_WHELP, 3);
        assertTrue(enemy.getXpReward() > 0);
        assertTrue(enemy.getGoldReward() > 0);
    }
}