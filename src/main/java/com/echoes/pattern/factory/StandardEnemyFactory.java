package com.echoes.pattern.factory;

import com.echoes.model.character.enemy.DragonWhelp;
import com.echoes.model.character.enemy.Enemy;
import com.echoes.model.character.enemy.EnemyType;
import com.echoes.model.character.enemy.Goblin;
import com.echoes.model.character.enemy.Skeleton;
import com.echoes.model.character.enemy.Troll;
import com.echoes.model.character.enemy.boss.AbyssDragon;
import com.echoes.model.character.enemy.boss.Lich;
import com.echoes.model.character.enemy.boss.StoneGolem;

/**
 * Standard implementation of the enemy factory.
 *
 * <p>This factory creates standard-difficulty enemies for the given type
 * and floor level, scaling their stats appropriately.</p>
 */
public final class StandardEnemyFactory implements EnemyFactory {

    @Override
    public Enemy create(final EnemyType type, final int floorLevel) {
        return switch (type) {
            case GOBLIN       -> new Goblin(floorLevel);
            case SKELETON     -> new Skeleton(floorLevel);
            case TROLL        -> new Troll(floorLevel);
            case DRAGON_WHELP -> new DragonWhelp(floorLevel);
            case STONE_GOLEM  -> new StoneGolem(floorLevel);
            case LICH         -> new Lich(floorLevel);
            case ABYSS_DRAGON -> new AbyssDragon(floorLevel);
        };
    }
}