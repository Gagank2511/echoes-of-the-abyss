package com.echoes.pattern.observer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.echoes.pattern.observer.listeners.ScoreTracker;

@DisplayName("GameEventPublisher Tests")
class GameEventPublisherTest {

    private final GameEventPublisher publisher = new GameEventPublisher();

    @Test
    @DisplayName("subscribe adds a listener")
    void subscribe_addsListener() {
        ScoreTracker tracker = new ScoreTracker();
        publisher.subscribe(tracker);
        assertTrue(publisher.getListeners().contains(tracker));
    }

    @Test
    @DisplayName("subscribe ignores duplicate listeners")
    void subscribe_ignoresDuplicate() {
        ScoreTracker tracker = new ScoreTracker();
        publisher.subscribe(tracker);
        publisher.subscribe(tracker);
        assertEquals(1, publisher.getListeners().size());
    }

    @Test
    @DisplayName("unsubscribe removes a listener")
    void unsubscribe_removesListener() {
        ScoreTracker tracker = new ScoreTracker();
        publisher.subscribe(tracker);
        publisher.unsubscribe(tracker);
        assertFalse(publisher.getListeners().contains(tracker));
    }

    @Test
    @DisplayName("publish notifies all subscribed listeners")
    void publish_notifiesAllListeners() {
        List<GameEvent> received = new ArrayList<>();
        publisher.subscribe(received::add);
        GameEvent event = new GameEvent(GameEventType.ENEMY_DEFEATED, 50);
        publisher.publish(event);
        assertEquals(1, received.size());
        assertSame(event, received.get(0));
    }

    @Test
    @DisplayName("publish ignores null events")
    void publish_ignoresNull() {
        List<GameEvent> received = new ArrayList<>();
        publisher.subscribe(received::add);
        publisher.publish(null);
        assertTrue(received.isEmpty());
    }

    @Test
    @DisplayName("ScoreTracker accumulates score from events")
    void scoreTracker_accumulatesScore() {
        ScoreTracker tracker = new ScoreTracker();
        publisher.subscribe(tracker);
        publisher.publish(new GameEvent(GameEventType.PLAYER_ATTACKED, 15));
        publisher.publish(new GameEvent(GameEventType.PLAYER_ATTACKED, 10));
        assertEquals(25, tracker.getScore());
    }

    @Test
    @DisplayName("ScoreTracker awards bonus for enemy defeated")
    void scoreTracker_awardsBonusForKill() {
        ScoreTracker tracker = new ScoreTracker();
        publisher.subscribe(tracker);
        publisher.publish(new GameEvent(GameEventType.ENEMY_DEFEATED, 0));
        assertTrue(tracker.getScore() > 0);
    }

    @Test
    @DisplayName("Multiple listeners all receive the same event")
    void publish_multipleListenersAllReceiveEvent() {
        List<GameEvent> first  = new ArrayList<>();
        List<GameEvent> second = new ArrayList<>();
        publisher.subscribe(first::add);
        publisher.subscribe(second::add);
        publisher.publish(new GameEvent(GameEventType.ITEM_USED, 1));
        assertEquals(1, first.size());
        assertEquals(1, second.size());
    }
}