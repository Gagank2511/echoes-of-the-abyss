package com.echoes.model.dungeon;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents the entire dungeon as an ordered sequence of {@link Floor}s.
 *
 * <p>A floor must be fully complete — boss defeated — before the player can
 * descend to the next. The dungeon is conquered when the final floor is cleared.
 * This class is deliberately simple: it tracks position and delegates all
 * room-level concerns to the {@link Floor} class.</p>
 *
 * @author Your Name
 * @version 1.0
 */
public final class Dungeon {

    private final String name;
    private final List<Floor> floors;
    private int currentFloorIndex;

    /**
     * Constructs a dungeon with the given name and ordered list of floors.
     *
     * @param name   the dungeon's display name — must not be null
     * @param floors the ordered list of floors — must not be null or empty
     * @throws IllegalArgumentException if the floors list is empty
     */
    public Dungeon(final String name, final List<Floor> floors) {
        this.name = Objects.requireNonNull(name, "Dungeon name must not be null");
        if (Objects.requireNonNull(floors, "Floors must not be null").isEmpty()) {
            throw new IllegalArgumentException("A dungeon must have at least one floor.");
        }
        this.floors = List.copyOf(floors);
        this.currentFloorIndex = 0;
    }

    /** @return the floor the player is currently on */
    public Floor getCurrentFloor() {
        return floors.get(currentFloorIndex);
    }

    /**
     * Returns true if the current floor is complete and there are more floors
     * below, meaning the player may descend.
     *
     * @return true if the player can advance to the next floor
     */
    public boolean canAdvanceFloor() {
        return getCurrentFloor().isComplete()
                && currentFloorIndex < floors.size() - 1;
    }

    /**
     * Advances to the next floor.
     *
     * @throws IllegalStateException if the current floor is not complete
     *                               or the player is already on the last floor
     */
    public void advanceFloor() {
        if (!canAdvanceFloor()) {
            throw new IllegalStateException(
                    "Cannot advance: floor incomplete or already at final floor.");
        }
        currentFloorIndex++;
    }

    /**
     * Returns true if the player is on the final floor and it is complete —
     * the dungeon has been fully conquered.
     *
     * @return true if the dungeon is complete
     */
    public boolean isComplete() {
        return currentFloorIndex == floors.size() - 1
                && getCurrentFloor().isComplete();
    }

    /** @return the dungeon's display name */
    public String getName() { return name; }

    /** @return an unmodifiable view of all floors */
    public List<Floor> getFloors() { return Collections.unmodifiableList(floors); }

    /** @return the 1-based current floor number */
    public int getCurrentFloorNumber() { return currentFloorIndex + 1; }

    /** @return total number of floors in this dungeon */
    public int getTotalFloors() { return floors.size(); }
}
