package com.echoes.model.dungeon;

import com.echoes.model.character.enemy.Enemy;
import com.echoes.model.item.consumable.Consumable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class Room {

    private final int roomNumber;
    private final RoomType roomType;
    private final String name;
    private final String flavourText;
    private Enemy enemy;
    private final List<Consumable> items;
    private boolean cleared;

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

    public void setEnemy(final Enemy enemy) { this.enemy = enemy; }

    public Optional<Enemy> getEnemy() { return Optional.ofNullable(enemy); }

    public boolean hasLivingEnemy() {
        return enemy != null && enemy.isAlive();
    }

    public void addItem(final Consumable item) {
        if (item != null) items.add(item);
    }

    public List<Consumable> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void clearItems() { items.clear(); }

    public void setCleared(final boolean cleared) { this.cleared = cleared; }

    public boolean isCleared() { return cleared; }

    public int getRoomNumber() { return roomNumber; }
    public RoomType getRoomType() { return roomType; }
    public String getName() { return name; }
    public String getFlavourText() { return flavourText; }
}