package com.echoes.pattern.state;

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