package com.echoes.application;

import com.echoes.controller.GameController;
import com.echoes.controller.InputHandler;
import com.echoes.model.dungeon.Dungeon;
import com.echoes.pattern.builder.DungeonBuilder;
import com.echoes.pattern.observer.GameEventPublisher;
import com.echoes.pattern.observer.listeners.AchievementTracker;
import com.echoes.pattern.observer.listeners.ScoreTracker;
import com.echoes.view.ConsoleRenderer;

/**
 * Composition root — assembles and wires all application components.
 *
 * <p>This class owns the full dependency graph. All concrete types are
 * instantiated here and injected into their consumers as interfaces or
 * abstractions. Nothing outside this class performs construction of
 * cross-cutting dependencies, making the system testable and maintainable.</p>
 *
 * <p>Assembly order:</p>
 * <ol>
 *   <li>Event bus — created first so listeners can register before events fire.</li>
 *   <li>Listeners — subscribe before dungeon or controller can fire events.</li>
 *   <li>Dungeon — built via the Builder pattern.</li>
 *   <li>View — the renderer also subscribes to the event bus for inline notifications.</li>
 *   <li>Input — isolated in its own handler class.</li>
 *   <li>Controller — receives all dependencies and starts the game loop.</li>
 * </ol>
 *
 * @author Your Name
 * @version 1.0
 */
public final class GameApplication {

    /**
     * Initialises all subsystems and starts the main game loop.
     */
    public void start() {
        // Step 1: Set up the Observer event bus and listeners
        final GameEventPublisher eventPublisher = new GameEventPublisher();
        final ScoreTracker scoreTracker = new ScoreTracker();
        final AchievementTracker achievementTracker = new AchievementTracker();
        eventPublisher.subscribe(scoreTracker);
        eventPublisher.subscribe(achievementTracker);

        // Step 2: Build the dungeon using the Builder pattern
        final Dungeon dungeon = new DungeonBuilder()
                .withName("The Abyss")
                .withFloorCount(5)
                .build();

        // Step 3: Set up view — renderer also subscribes for event notifications
        final ConsoleRenderer renderer = new ConsoleRenderer();
        eventPublisher.subscribe(renderer);

        // Step 4: Set up input handler
        final InputHandler inputHandler = new InputHandler();

        // Step 5: Wire everything into the controller and start
        final GameController controller = new GameController(
                dungeon, inputHandler, renderer, eventPublisher, scoreTracker);
        controller.run();

        inputHandler.close();
    }
}
