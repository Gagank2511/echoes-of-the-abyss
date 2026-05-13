package com.echoes.pattern.observer;

/**
 * Enumeration of game event types for the observer pattern.
 *
 * <p>These types categorise different events that can occur during gameplay,
 * allowing listeners to react appropriately to specific happenings.</p>
 */
public enum GameEventType {
    /** The player performed an attack. */
    PLAYER_ATTACKED,
    /** An enemy performed an attack. */
    ENEMY_ATTACKED,
    /** The player successfully fled from combat. */
    PLAYER_FLED,
    /** The player was defeated. */
    PLAYER_DEFEATED,
    /** An enemy was defeated. */
    ENEMY_DEFEATED,
    /** An item was used by the player. */
    ITEM_USED,
    /** The player gained a level. */
    PLAYER_LEVELLED_UP,
    /** A boss enemy was defeated. */
    BOSS_DEFEATED,
    /** The entire dungeon was cleared. */
    DUNGEON_CLEARED
}