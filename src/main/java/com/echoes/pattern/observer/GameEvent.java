package com.echoes.pattern.observer;

import java.time.Instant;
import java.util.Objects;

/**
 * Represents an event that occurs during gameplay.
 *
 * <p>Events are published to listeners via the observer pattern, allowing
 * different parts of the system to react to game happenings like level-ups
 * or enemy defeats.</p>
 */
public final class GameEvent {

    private final GameEventType type;
    private final int payload;
    private final Instant timestamp;

    /**
     * Constructs a game event with the specified type and payload.
     *
     * @param type    the type of event
     * @param payload additional data associated with the event
     */
    public GameEvent(final GameEventType type, final int payload) {
        this.type = Objects.requireNonNull(type, "Event type must not be null");
        this.payload = payload;
        this.timestamp = Instant.now();
    }

    /**
     * Gets the type of this event.
     *
     * @return the event type
     */
    public GameEventType getType() { return type; }

    /**
     * Gets the payload data for this event.
     *
     * @return the payload
     */
    public int getPayload() { return payload; }

    /**
     * Gets the timestamp when this event was created.
     *
     * @return the timestamp
     */
    public Instant getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("GameEvent[%s, payload=%d]", type, payload);
    }
}