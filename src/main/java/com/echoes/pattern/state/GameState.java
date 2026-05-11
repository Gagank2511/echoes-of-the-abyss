package com.echoes.pattern.state;

/**
 * State interface for the <em>State</em> design pattern.
 *
 * <p>Each concrete state ({@link ExploringState}, {@link CombatState},
 * {@link GameOverState}, {@link VictoryState}) encapsulates the behaviour
 * appropriate to one phase of the game. The {@link GameContext} delegates
 * all input handling to the current state, and states trigger transitions
 * by calling {@link GameContext#setState(GameState)}.</p>
 *
 * <p>Benefits of this approach over a large if-else chain:</p>
 * <ul>
 *   <li>Each state is cohesive, self-contained, and independently testable.</li>
 *   <li>New states such as a merchant screen require only a new implementation
 *       of this interface — no existing state is modified.</li>
 *   <li>Invalid transitions are prevented by design: a CombatState cannot
 *       accidentally execute exploration logic.</li>
 * </ul>
 *
 * @author Your Name
 * @version 1.0
 */
public interface GameState {

    /**
     * Called once when this state becomes the active state in the context.
     * Use for one-time setup such as rendering headers or prompting the player.
     *
     * @param context the game context that owns this state
     */
    void onEnter(GameContext context);

    /**
     * Handles the player's input for the current state and applies any
     * resulting model changes or state transitions.
     *
     * @param context the game context
     * @param input   the raw input string entered by the player
     */
    void handleInput(GameContext context, String input);

    /**
     * Returns true if this state represents a terminal condition —
     * either game over or victory — and the main loop should stop.
     *
     * @return true if the game has ended
     */
    boolean isTerminal();
}
