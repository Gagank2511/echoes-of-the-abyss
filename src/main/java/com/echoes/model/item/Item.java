package com.echoes.model.item;

/**
 * Represents an item that can be found or used within the game.
 *
 * <p>Items have a name, description, and type that determine their behaviour
 * and how they interact with the player and the game world.</p>
 */
public interface Item {
    /**
     * Gets the display name of the item.
     *
     * @return the item's name
     */
    String getName();

    /**
     * Gets a description of the item's effects or properties.
     *
     * @return the item's description
     */
    String getDescription();

    /**
     * Gets the type of the item, which categorises its usage.
     *
     * @return the item's type
     */
    ItemType getItemType();
}