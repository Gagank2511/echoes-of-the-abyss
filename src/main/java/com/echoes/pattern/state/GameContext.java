package com.echoes.pattern.state;

import java.util.Objects;

import com.echoes.model.character.Player;
import com.echoes.model.combat.CombatEngine;
import com.echoes.model.dungeon.Dungeon;
import com.echoes.pattern.command.CommandHistory;
import com.echoes.pattern.observer.GameEventPublisher;
import com.echoes.view.GameRenderer;

/**
 * Context class for the <em>State</em> design pattern.
 *
 * <p>Holds references to all shared game subsystems and delegates input
 * handling to the currently active {@link GameState}. States trigger
 * transitions by calling {@link #setState(GameState)}, which automatically
 * calls {@link GameState#onEnter(GameContext)} on the incoming state.</p>
 *
 * <p>Keeping all shared services in the context avoids repetitive constructor
 * arguments across every state class and provides a clean central point for
 * the game's runtime dependencies.</p>
 *
 * @author Your Name
 * @version 1.0
 */
public final class GameContext {

    private final Player player;
    private final Dungeon dungeon;
    private final CombatEngine combatEngine;
    private final GameRenderer renderer;
    private final GameEventPublisher eventPublisher;
    private final CommandHistory commandHistory;
    private GameState currentState;

    /**
     * Constructs a game context with all shared subsystems and an initial state.
     *
     * @param player         the player character
     * @param dungeon        the dungeon to explore
     * @param combatEngine   the stateless combat service
     * @param renderer       the view layer
     * @param eventPublisher the event bus
     * @param commandHistory the command history recorder
     * @param initialState   the first state to activate
     */
    public GameContext(final Player player,
                       final Dungeon dungeon,
                       final CombatEngine combatEngine,
                       final GameRenderer renderer,
                       final GameEventPublisher eventPublisher,
                       final CommandHistory commandHistory,
                       final GameState initialState) {
        this.player = Objects.requireNonNull(player);
        this.dungeon = Objects.requireNonNull(dungeon);
        this.combatEngine = Objects.requireNonNull(combatEngine);
        this.renderer = Objects.requireNonNull(renderer);
        this.eventPublisher = Objects.requireNonNull(eventPublisher);
        this.commandHistory = Objects.requireNonNull(commandHistory);
        this.currentState = Objects.requireNonNull(initialState);
        this.currentState.onEnter(this);
    }

    /**
     * Transitions to a new state, calling {@link GameState#onEnter(GameContext)}
     * on the incoming state automatically.
     *
     * @param newState the state to transition to — must not be null
     */
    public void setState(final GameState newState) {
        this.currentState = Objects.requireNonNull(newState);
        this.currentState.onEnter(this);
    }

    /**
     * Delegates the player's input to the current state for handling.
     *
     * @param input the raw input string
     */
    public void handleInput(final String input) {
        currentState.handleInput(this, input);
    }

    /**
     * Returns true if the current state is terminal, indicating the main
     * game loop should exit.
     *
     * @return true if the game has ended
     */
    public boolean isTerminal() {
        return currentState.isTerminal();
    }

    /** @return the player character */
    public Player getPlayer() { return player; }

    /** @return the dungeon model */
    public Dungeon getDungeon() { return dungeon; }

    /** @return the combat engine */
    public CombatEngine getCombatEngine() { return combatEngine; }

    /** @return the view renderer */
    public GameRenderer getRenderer() { return renderer; }

    /** @return the event publisher */
    public GameEventPublisher getEventPublisher() { return eventPublisher; }

    /** @return the command history */
    public CommandHistory getCommandHistory() { return commandHistory; }

    /** @return the currently active state */
    public GameState getCurrentState() { return currentState; }
}
