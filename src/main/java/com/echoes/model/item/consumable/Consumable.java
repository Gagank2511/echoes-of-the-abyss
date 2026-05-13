package com.echoes.model.item.consumable;

import com.echoes.model.item.Item;

/**
 * Interface for items that can be consumed by the player.
 *
 * <p>Consumable items provide temporary effects when used, such as healing
 * or stat boosts. They have a limited quantity and can be exhausted.</p>
 */
public interface Consumable extends Item {
    /**
     * Uses the consumable and returns its effect.
     *
     * @return the effect of using this item
     */
    ItemEffect use();

    /**
     * Checks if this consumable has been fully used up.
     *
     * @return true if exhausted
     */
    boolean isExhausted();

    /**
     * Gets the remaining quantity of this consumable.
     *
     * @return the quantity
     */
    int getQuantity();
}