package com.echoes.model.character.enemy;

import com.echoes.model.combat.MeleeAttackStrategy;

/**
 * A basic melee enemy that attacks with a cleaver.
 *
 * <p>Goblins are weak individually but can be encountered in groups.
 * They use melee attacks and have low health and defence.</p>
 */
public final class Goblin extends Enemy {

    /**
     * Constructs a goblin scaled to the given floor level.
     *
     * @param floorLevel the dungeon floor level for stat scaling
     */
    public Goblin(final int floorLevel) {
        super(
            "Goblin",
            25 + (floorLevel * 5),
            5  + (floorLevel * 2),
            1,
            new MeleeAttackStrategy(),
            EnemyType.GOBLIN,
            10 + (floorLevel * 5),
            3  + (floorLevel * 2)
        );
    }

    @Override
    public String getDescription() {
        return "A wiry, green-skinned goblin brandishing a jagged cleaver. "
                + "Weak alone, but its eyes betray a cruel cunning.";
    }
}