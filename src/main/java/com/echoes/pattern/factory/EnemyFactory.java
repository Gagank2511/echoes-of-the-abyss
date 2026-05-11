package com.echoes.pattern.factory;

import com.echoes.model.character.enemy.Enemy;
import com.echoes.model.character.enemy.EnemyType;

/**
 * Factory Method interface for creating {@link Enemy} instances.
 *
 * <p>This is the <em>Factory Method</em> design pattern. Concrete factories
 * ({@link StandardEnemyFactory}, {@link EliteEnemyFactory}) decide which
 * specific subclass to instantiate for a given {@link EnemyType} and floor
 * level. Callers such as {@link com.echoes.pattern.builder.FloorBuilder}
 * depend only on this interface.</p>
 *
 * <p>Adding a new enemy type requires only a new {@link Enemy} subclass and
 * a switch branch in each factory — no calling code needs to change.
 * Adding a new difficulty tier requires only a new factory implementation.</p>
 *
 * @author Your Name
 * @version 1.0
 */
public interface EnemyFactory {

    /**
     * Creates an enemy of the given type, scaled to the given floor level.
     *
     * @param type       the type of enemy to create
     * @param floorLevel 1-based floor level used for stat scaling
     * @return a new {@link Enemy} instance ready for use
     */
    Enemy create(EnemyType type, int floorLevel);
}
