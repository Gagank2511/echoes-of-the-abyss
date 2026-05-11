package com.echoes.model.character.enemy;

import com.echoes.model.combat.RangedAttackStrategy;

public final class Skeleton extends Enemy {

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