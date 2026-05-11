package com.echoes.model.dungeon;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Floor {

    private final int floorNumber;
    private final String name;
    private final List<Room> rooms;
    private int currentRoomIndex;

    public Floor(final int floorNumber, final String name, final List<Room> rooms) {
        if (Objects.requireNonNull(rooms, "Rooms must not be null").isEmpty()) {
            throw new IllegalArgumentException("A floor must have at least one room.");
        }
        this.floorNumber = floorNumber;
        this.name = name;
        this.rooms = List.copyOf(rooms);
        this.currentRoomIndex = 0;
    }

    public boolean canAdvance() {
        return currentRoomIndex < rooms.size() - 1;
    }

    public void advance() {
        if (!canAdvance()) {
            throw new IllegalStateException(
                    "Cannot advance past the last room on floor " + floorNumber);
        }
        currentRoomIndex++;
    }

    public Room getCurrentRoom() {
        return rooms.get(currentRoomIndex);
    }

    public boolean isComplete() {
        return rooms.get(rooms.size() - 1).isCleared();
    }

    public int getFloorNumber() { return floorNumber; }
    public String getName() { return name; }
    public List<Room> getRooms() { return Collections.unmodifiableList(rooms); }
    public int getCurrentRoomIndex() { return currentRoomIndex; }
}