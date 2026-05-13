package com.echoes.model.dungeon;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a single floor in the dungeon, containing a sequence of rooms.
 *
 * <p>A floor consists of multiple rooms that the player must progress through.
 * The floor is complete when the final room (typically a boss room) is cleared.</p>
 */
public final class Floor {

    private final int floorNumber;
    private final String name;
    private final List<Room> rooms;
    private int currentRoomIndex;

    /**
     * Constructs a floor with the specified number, name, and rooms.
     *
     * @param floorNumber the number of this floor
     * @param name        the display name of this floor
     * @param rooms       the ordered list of rooms on this floor
     * @throws IllegalArgumentException if rooms is empty
     */
    public Floor(final int floorNumber, final String name, final List<Room> rooms) {
        if (Objects.requireNonNull(rooms, "Rooms must not be null").isEmpty()) {
            throw new IllegalArgumentException("A floor must have at least one room.");
        }
        this.floorNumber = floorNumber;
        this.name = name;
        this.rooms = List.copyOf(rooms);
        this.currentRoomIndex = 0;
    }

    /**
     * Checks if the player can advance to the next room.
     *
     * @return true if there are more rooms to explore
     */
    public boolean canAdvance() {
        return currentRoomIndex < rooms.size() - 1;
    }

    /**
     * Advances the player to the next room.
     *
     * @throws IllegalStateException if already at the last room
     */
    public void advance() {
        if (!canAdvance()) {
            throw new IllegalStateException(
                    "Cannot advance past the last room on floor " + floorNumber);
        }
        currentRoomIndex++;
    }

    /**
     * Gets the current room the player is in.
     *
     * @return the current room
     */
    public Room getCurrentRoom() {
        return rooms.get(currentRoomIndex);
    }

    /**
     * Checks if this floor is complete (all rooms cleared).
     *
     * @return true if the floor is complete
     */
    public boolean isComplete() {
        return rooms.get(rooms.size() - 1).isCleared();
    }

    /**
     * Gets the floor number.
     *
     * @return the floor number
     */
    public int getFloorNumber() { return floorNumber; }

    /**
     * Gets the display name of the floor.
     *
     * @return the floor name
     */
    public String getName() { return name; }

    /**
     * Gets an unmodifiable list of all rooms on this floor.
     *
     * @return the list of rooms
     */
    public List<Room> getRooms() { return Collections.unmodifiableList(rooms); }

    /**
     * Gets the index of the current room.
     *
     * @return the current room index
     */
    public int getCurrentRoomIndex() { return currentRoomIndex; }
}