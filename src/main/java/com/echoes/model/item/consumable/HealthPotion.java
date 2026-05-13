package com.echoes.model.item.consumable;

import com.echoes.util.GameConstants;

/**
 * A consumable item that restores health to the player.
 *
 * <p>When used, this potion heals the player for a fixed amount of hit points,
 * helping them recover from combat damage.</p>
 */
public final class HealthPotion extends AbstractConsumable {

    /**
     * Constructs a new health potion.
     */
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