package com.echoes.model.character.enemy;

import com.echoes.model.combat.MagicAttackStrategy;

/**
 * A young dragon enemy that uses magic attacks.
 *
 * <p>Dragon whelps are powerful foes with high attack power and defence.
 * They use magic that penetrates armour, making them effective against
 * heavily defended opponents.</p>
 */
public final class DragonWhelp extends Enemy {

    /**
     * Constructs a dragon whelp scaled to the given floor level.
     *
     * @param floorLevel the dungeon floor level for stat scaling
     */
    public DragonWhelp(final int floorLevel) {
        super(
            "Dragon Whelp",
            40 + (floorLevel * 6),
            10 + (floorLevel * 3),
            3,
            new MagicAttackStrategy(),
            EnemyType.DRAGON_WHELP,
            40 + (floorLevel * 10),
            12 + (floorLevel * 4)
        );
    }

    @Override
    public String getDescription() {
        return "A sleek, crimson-scaled dragon whelp no larger than a horse. "
                + "Its belly glows molten orange, and smoke curls from its nostrils.";
    }
}