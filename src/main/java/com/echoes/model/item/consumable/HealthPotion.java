package com.echoes.model.item.consumable;

import com.echoes.util.GameConstants;

public final class HealthPotion extends AbstractConsumable {

    public HealthPotion() {
        super(
            "Health Potion",
            "Restores " + GameConstants.HEALTH_POTION_HEAL + " hit points.",
            1
        );
    }

    @Override
    protected ItemEffect calculateEffect() {
        return new ItemEffect(
            GameConstants.HEALTH_POTION_HEAL,
            0, 0, 0,
            "You drink the potion. Warmth surges through your veins, "
                + "restoring " + GameConstants.HEALTH_POTION_HEAL + " HP."
        );
    }
}