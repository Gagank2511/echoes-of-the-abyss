package com.echoes.model.character.enemy;

import com.echoes.model.combat.RangedAttackStrategy;

/**
 * An undead ranged enemy that attacks with a bow.
 *
 * <p>Skeletons are fragile but can attack from a distance, making them
 * dangerous in groups. They have low health but decent defence for their type.</p>
 */
public final class Skeleton extends Enemy {

    /**
     * Constructs a skeleton scaled to the given floor level.
     *
     * @param floorLevel the dungeon floor level for stat scaling
     */
    public Skeleton(final int floorLevel) {
        super(
            "Skeleton",
            20 + (floorLevel * 5),
            4  + (floorLevel * 2),
            2,
            new RangedAttackStrategy(),
            EnemyType.SKELETON,
            15 + (floorLevel * 5),
            5  + (floorLevel * 2)
        );
    }

    @Override
    public String getDescription() {
        return "A rattling skeleton drawing an ancient longbow. "
                + "Its hollow eye-sockets glow with pale, malevolent light.";
    }
}