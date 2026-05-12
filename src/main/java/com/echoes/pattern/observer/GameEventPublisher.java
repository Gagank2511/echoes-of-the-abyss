package com.echoes.pattern.observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Event bus (Subject) for the <em>Observer</em> design pattern.
 *
 * <p>Any component that generates a meaningful game event calls
 * {@link #publish(GameEvent)}, and all registered
 * {@link GameEventListener}s are notified in registration order.
 * This completely decouples producers (combat commands, states) from
 * consumers (score tracker, achievement tracker, renderer).</p>
 *
 * <p>Adding a new listener requires no changes to any existing class —
 * simply subscribe it here. This satisfies the Open/Closed Principle:
 * the system is open for extension without modification of existing code.</p>
 */
public final class GameEventPublisher {

    private final List<GameEventListener> listeners = new ArrayList<>();

    /**
     * Registers a listener to receive future events.
     * Duplicate registrations are silently ignored.
     *
     * @param listener the listener to add — null is silently ignored
     */
    public void subscribe(final GameEventListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Removes a previously registered listener.
     * Has no effect if the listener is not currently registered.
     *
     * @param listener the listener to remove
     */
    public void unsubscribe(final GameEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Publishes an event to all registered listeners synchronously.
     * A snapshot of the listener list is taken before dispatch, so
     * listeners may safely unsubscribe during event handling.
     *
     * @param event the event to publish — null is silently ignored
     */
    public void publish(final GameEvent event) {
        if (event == null) return;
        List<GameEventListener> snapshot = List.copyOf(listeners);
        for (GameEventListener listener : snapshot) {
            listener.onEvent(event);
        }
    }

    /**
     * Returns an unmodifiable view of the currently registered listeners.
     * Intended for testing and diagnostics only.
     *
     * @return unmodifiable listener list
     */
    public List<GameEventListener> getListeners() {
        return Collections.unmodifiableList(listeners);
    }
}
