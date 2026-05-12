package com.echoes.pattern.observer;

/**
 * Observer interface for receiving {@link GameEvent}s from the
 * {@link GameEventPublisher}.
 *
 * <p>Any component that needs to react to game events implements this
 * interface and registers itself with the publisher. The publisher fires
 * events without knowing or caring which listeners are attached — this
 * loose coupling is the core benefit of the <em>Observer</em> pattern.</p>
 */
public interface GameEventListener {

    /**
     * Called by the {@link GameEventPublisher} whenever a game event occurs.
     *
     * @param event the event that occurred — never null
     */
    void onEvent(GameEvent event);
}
