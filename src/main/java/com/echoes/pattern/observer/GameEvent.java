package com.echoes.pattern.observer;

import java.time.Instant;
import java.util.Objects;

public final class GameEvent {

    private final GameEventType type;
    private final int payload;
    private final Instant timestamp;

    public GameEvent(final GameEventType type, final int payload) {
        this.type = Objects.requireNonNull(type, "Event type must not be null");
        this.payload = payload;
        this.timestamp = Instant.now();
    }

    public GameEventType getType() { return type; }
    public int getPayload() { return payload; }
    public Instant getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("GameEvent[%s, payload=%d]", type, payload);
    }
}