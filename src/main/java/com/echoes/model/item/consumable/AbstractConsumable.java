package com.echoes.model.item.consumable;

import com.echoes.model.item.ItemType;

/**
 * Abstract base class for all consumable items — implements the
 * <em>Template Method</em> design pattern.
 *
 * <p>The {@link #use()} method defines the invariant algorithm for consuming
 * an item:</p>
 * <ol>
 *   <li>Validate the item is not exhausted.</li>
 *   <li>Delegate to {@link #calculateEffect()} — the hook method provided
 *       by each concrete subclass.</li>
 *   <li>Decrement the quantity counter.</li>
 *   <li>Return the effect to the caller.</li>
 * </ol>
 *
 * <p>Subclasses implement only {@link #calculateEffect()}, meaning the
 * validation and quantity logic is written once here and never duplicated.
 * Adding a new item type requires only a new subclass and an implementation
 * of the hook — no changes to existing code.</p>
 *
 * @author Your Name
 * @version 1.0
 */
public abstract class AbstractConsumable implements Consumable {

    private final String name;
    private final String description;
    private int quantity;

    /**
     * Constructs a consumable with the given attributes.
     *
     * @param name        display name of the item
     * @param description brief description for the inventory screen
     * @param quantity    initial number of uses — must be at least 1
     * @throws IllegalArgumentException if quantity is less than 1
     */
    protected AbstractConsumable(final String name,
                                  final String description,
                                  final int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException(
                    "Initial quantity must be >= 1, got: " + quantity);
        }
        this.name = name;
        this.description = description;
        this.quantity = quantity;
    }

    /**
     * Uses one charge of this item — the <strong>Template Method</strong>.
     * The structure is fixed here; only {@link #calculateEffect()} is
     * deferred to concrete subclasses.
     *
     * @return the {@link ItemEffect} produced by this item
     * @throws IllegalStateException if the item has no remaining charges
     */
    @Override
    public final ItemEffect use() {
        if (isExhausted()) {
            throw new IllegalStateException(
                    "Cannot use '" + name + "': no charges remaining.");
        }
        ItemEffect effect = calculateEffect();
        quantity--;
        return effect;
    }

    /**
     * Calculates the specific {@link ItemEffect} for this item.
     * This is the <strong>hook method</strong> — each concrete subclass
     * provides its own implementation without duplicating the use logic.
     *
     * @return the item's effect
     */
    protected abstract ItemEffect calculateEffect();

    @Override
    public boolean isExhausted() { return quantity <= 0; }

    @Override
    public int getQuantity() { return quantity; }

    @Override
    public String getName() { return name; }

    @Override
    public String getDescription() { return description; }

    @Override
    public ItemType getItemType() { return ItemType.CONSUMABLE; }
}
