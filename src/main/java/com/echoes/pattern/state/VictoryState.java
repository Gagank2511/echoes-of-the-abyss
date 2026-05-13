package com.echoes.pattern.state;

import com.echoes.pattern.observer.GameEvent;
import com.echoes.pattern.observer.GameEventType;

/**
 * Terminal game state representing victory after clearing the dungeon.
 *
 * <p>This state displays the victory screen and publishes the dungeon cleared
 * event. It is a terminal state, meaning the game ends here.</p>
 */
public final class VictoryState implements GameState {

    @Override
    public void onEnter(final GameContext context) {
        context.getEventPublisher().publish(
                new GameEvent(GameEventType.DUNGEON_CLEARED, 0));
        context.getRenderer().displayVictory(context.getPlayer());
    }

    @Override
    public void handleInput(final GameContext context, final String input) {}

    @Override
    public boolean isTerminal() { return true; }
}