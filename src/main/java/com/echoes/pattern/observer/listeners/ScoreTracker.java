package com.echoes.pattern.observer.listeners;

import com.echoes.pattern.observer.GameEvent;
import com.echoes.pattern.observer.GameEventListener;

/**
 * Observer that tracks the player's running score throughout the dungeon run.
 *
 * <p>Points are awarded for different event types. Defeating enemies scores
 * more than dealing damage alone; defeating a boss or clearing the dungeon
 * awards a large bonus. The score is read-only from outside and cannot be
 * modified directly by any other component.</p>
 *
 * @author Your Name
 * @version 1.0
 */
public final class ScoreTracker implements GameEventListener {

    private static final int POINTS_PER_DAMAGE    = 1;
    private static final int POINTS_ENEMY_KILL    = 50;
    private static final int POINTS_BOSS_KILL     = 200;
    private static final int POINTS_DUNGEON_CLEAR = 500;
    private static final int POINTS_ITEM_USED     = 5;

    private int score;

    /** Constructs a score tracker with an initial score of zero. */
    public ScoreTracker() {
        this.score = 0;
    }

    @Override
    public void onEvent(final GameEvent event) {
        switch (event.getType()) {
            case PLAYER_ATTACKED  -> score += event.getPayload() * POINTS_PER_DAMAGE;
            case ENEMY_DEFEATED   -> score += POINTS_ENEMY_KILL;
            case BOSS_DEFEATED    -> score += POINTS_BOSS_KILL;
            case DUNGEON_CLEARED  -> score += POINTS_DUNGEON_CLEAR;
            case ITEM_USED        -> score += POINTS_ITEM_USED;
            default               -> { /* Other events do not affect score */ }
        }
    }

    /**
     * Returns the current accumulated score.
     *
     * @return score points
     */
    public int getScore() { return score; }

    /** Resets the score to zero. */
    public void reset() { score = 0; }
}
