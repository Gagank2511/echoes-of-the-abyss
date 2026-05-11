package com.echoes.model.character.enemy;

import com.echoes.model.combat.MeleeAttackStrategy;

public final class Troll extends Enemy {

    private static final int REGEN_PER_TURN = 6;
    private String lastTurnEffect = "";

    public Troll(final int floorLevel) {
        super(
            "Cave Troll",
            55 + (floorLevel * 8),
            8  + (floorLevel * 3),
            4,
            new MeleeAttackStrategy(),
            EnemyType.TROLL,
            30 + (floorLevel * 8),
            10 + (floorLevel * 3)
        );
    }

    @Override
    protected void onBeforeTurn() {
        if (isAlive()) {
            heal(REGEN_PER_TURN);
            lastTurnEffect = String.format(
                    "The Cave Troll's wounds knit closed (+%d HP)!", REGEN_PER_TURN);
        }
    }

    @Override
    public String getDescription() {
        return "A hulking cave troll with mottled grey skin and a tree-trunk "
                + "club. Its regenerative flesh stitches shut even as you watch.";
    }

    public String getLastTurnEffect() { return lastTurnEffect; }
}