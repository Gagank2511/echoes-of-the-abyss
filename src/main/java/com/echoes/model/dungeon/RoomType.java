package com.echoes.model.dungeon;

/**
 * Enumeration of room types in the dungeon.
 *
 * <p>Each room type determines the content and behaviour of the room,
 * such as whether it contains enemies, items, or serves as an entrance or boss room.</p>
 */
public enum RoomType {
    /** The entrance room at the start of a floor. */
    ENTRANCE,
    /** A room containing a monster to fight. */
    MONSTER,
    /** A room containing treasure or items. */
    TREASURE,
    /** The boss room at the end of a floor. */
    BOSS
}