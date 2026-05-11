package com.echoes.pattern.command;

/**
 * Command interface — the root of the <em>Command</em> design pattern.
 *
 * <p>Each concrete command encapsulates a complete, self-contained player
 * action as an object: attacking, using an item, fleeing, or moving.
 * Benefits of this approach include:</p>
 * <ul>
 *   <li>The controller is decoupled from direct action execution — it simply
 *       calls {@link #execute()} without knowing the details.</li>
 *   <li>{@link CommandHistory} can record every action taken without knowing
 *       what any command does internally.</li>
 *   <li>Future features such as action replay or undo require no changes to
 *       existing command implementations.</li>
 * </ul>
 *
 * @author Your Name
 * @version 1.0
 */
public interface Command {

    /**
     * Executes the encapsulated action, applying all relevant changes to
     * the game model and notifying the view as required.
     */
    void execute();

    /**
     * Returns a brief human-readable description of this command.
     * Used for display in the command history log.
     *
     * @return command description
     */
    String getDescription();
}
