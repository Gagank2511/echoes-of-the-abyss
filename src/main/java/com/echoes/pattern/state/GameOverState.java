package com.echoes.pattern.state;

/**
 * Terminal game state representing game over after player defeat.
 *
 * <p>This state displays the game over screen. It is a terminal state,
 * meaning the game ends here.</p>
 */
public final class GameOverState implements GameState {

    @Override
    public void onEnter(final GameContext context) {
        context.getRenderer().displayGameOver(context.getPlayer());
    }

    @Override
    public void handleInput(final GameContext context, final String input) {}

    @Override
    public boolean isTerminal() { return true; }
}