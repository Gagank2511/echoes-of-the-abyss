package com.echoes.model.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.echoes.model.character.enemy.Enemy;
import com.echoes.model.item.consumable.Consumable;

/**
 * Represents a single room in the dungeon.
 *
 * <p>Rooms can contain enemies, items, or be empty. They have a type that
 * determines their purpose and initial state. Rooms are cleared when any
 * enemies are defeated or when they contain no enemies.</p>
 */
public final class Room {

    private final int roomNumber;
    private final RoomType roomType;
    private final String name;
    private final String flavourText;
    private Enemy enemy;
    private final List<Consumable> items;
    private boolean cleared;

    /**
     * Constructs a room with the specified properties.
     *
     * @param roomNumber  the number of this room on its floor
     * @param roomType    the type of this room
     * @param name        the display name of this room
     * @param flavourText descriptive text shown when entering the room
     */
    public Room(final int roomNumber,
                final RoomType roomType,
                final String name,
                final String flavourText) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.name = name;
        this.flavourText = flavourText;
        this.items = new ArrayList<>();
        this.cleared = (roomType == RoomType.ENTRANCE
                     || roomType == RoomType.TREASURE);
    }

    /**
     * Sets the enemy in this room.
     *
     * @param enemy the enemy to place in this room
     */
    public void setEnemy(final Enemy enemy) { this.enemy = enemy; }

    /**
     * Gets the enemy in this room, if any.
     *
     * @return an Optional containing the enemy, or empty if none
     */
    public Optional<Enemy> getEnemy() { return Optional.ofNullable(enemy); }

    /**
     * Checks if this room has a living enemy.
     *
     * @return true if there is a living enemy in this room
     */
    public boolean hasLivingEnemy() {
        return enemy != null && enemy.isAlive();
    }

    /**
     * Adds an item to this room.
     *
     * @param item the item to add
     */
    public void addItem(final Consumable item) {
        if (item != null) items.add(item);
    }

    /**
     * Gets an unmodifiable list of items in this room.
     *
     * @return the list of items
     */
    public List<Consumable> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Removes all items from this room.
     */
    public void clearItems() { items.clear(); }

    public void setCleared(final boolean cleared) { this.cleared = cleared; }

    public boolean isCleared() { return cleared; }

    public int getRoomNumber() { return roomNumber; }
    public RoomType getRoomType() { return roomType; }
    public String getName() { return name; }
    public String getFlavourText() { return flavourText; }
}