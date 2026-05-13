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
 * Factory for creating elite enemies with enhanced stats.
 *
 * <p>This factory produces enemies that are one level higher than
 * the requested floor level, making them more challenging. It implements
 * the Factory pattern for enemy creation.</p>
 */
public final class EliteEnemyFactory implements EnemyFactory {

    private static final int ELITE_LEVEL_BOOST = 1;

    @Override
    public Enemy create(final EnemyType type, final int floorLevel) {
        final int boosted = floorLevel + ELITE_LEVEL_BOOST;
        return switch (type) {
            case GOBLIN       -> new Goblin(boosted);
            case SKELETON     -> new Skeleton(boosted);
            case TROLL        -> new Troll(boosted);
            case DRAGON_WHELP -> new DragonWhelp(boosted);
            case STONE_GOLEM  -> new StoneGolem(boosted);
            case LICH         -> new Lich(boosted);
            case ABYSS_DRAGON -> new AbyssDragon(boosted);
        };
    }
}