package com.echoes.model.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.echoes.model.item.consumable.Consumable;
import com.echoes.util.GameConstants;

/**
 * Manages the player's collection of consumable items with a capacity limit.
 *
 * <p>Enforces a maximum size defined by {@link GameConstants#MAX_INVENTORY_SIZE}.
 * Exhausted items (quantity zero) are not removed automatically — callers
 * should invoke {@link #removeExhaustedItems()} after use, or check
 * {@link Consumable#isExhausted()} before display to keep the inventory clean.</p>
 */
public final class Inventory {

    private final List<Consumable> items;

    /** Constructs an empty inventory. */
    public Inventory() {
        this.items = new ArrayList<>();
    }

    /**
     * Attempts to add an item to the inventory.
     *
     * @param item the item to add — null is rejected
     * @return true if added; false if the inventory is full or item is null
     */
    public boolean addItem(final Consumable item) {
        if (item == null || items.size() >= GameConstants.MAX_INVENTORY_SIZE) {
            return false;
        }
        items.add(item);
        return true;
    }

    /**
     * Removes the given item from the inventory if present.
     *
     * @param item the item to remove
     * @return true if the item was found and removed
     */
    public boolean removeItem(final Consumable item) {
        return items.remove(item);
    }

    /**
     * Removes all items whose {@link Consumable#isExhausted()} returns true.
     * Useful for post-combat cleanup.
     */
    public void removeExhaustedItems() {
        items.removeIf(Consumable::isExhausted);
    }

    /**
     * Returns the item at the given 0-based index, or empty if out of bounds.
     *
     * @param index 0-based item index
     * @return Optional containing the item, or empty
     */
    public Optional<Consumable> getItem(final int index) {
        if (index < 0 || index >= items.size()) return Optional.empty();
        return Optional.of(items.get(index));
    }

    /** @return an unmodifiable view of all items in the inventory */
    public List<Consumable> getItems() {
        return Collections.unmodifiableList(items);
    }

    /** @return true if the inventory contains no items */
    public boolean isEmpty() { return items.isEmpty(); }

    /** @return the current number of items */
    public int size() { return items.size(); }

    /** @return true if the inventory is at maximum capacity */
    public boolean isFull() {
        return items.size() >= GameConstants.MAX_INVENTORY_SIZE;
    }

    @Override
    public String toString() {
        if (items.isEmpty()) return "Inventory: [empty]";
        StringBuilder sb = new StringBuilder("Inventory:\n");
        for (int i = 0; i < items.size(); i++) {
            sb.append(String.format("  [%d] %s%n", i + 1, items.get(i).getName()));
        }
        return sb.toString();
    }
}
