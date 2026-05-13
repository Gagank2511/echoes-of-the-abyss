package com.echoes.pattern.command;

import com.echoes.model.dungeon.Dungeon;
import com.echoes.model.dungeon.Floor;
import com.echoes.view.GameRenderer;

/**
 * Command to move the player forward in the dungeon.
 *
 * <p>This command advances the player to the next room or floor if possible,
 * following the rules of dungeon progression. It implements the Command pattern
 * for undoable actions.</p>
 */
public final class MoveCommand implements Command {

    private final Dungeon dungeon;
    private final GameRenderer renderer;
    private boolean moved;

    /**
     * Constructs a move command with the necessary dependencies.
     *
     * @param dungeon  the dungeon to navigate
     * @param renderer the renderer for displaying messages
     */
    public MoveCommand(final Dungeon dungeon, final GameRenderer renderer) {
        this.dungeon = dungeon;
        this.renderer = renderer;
        this.moved = false;
    }

    @Override
    public void execute() {
        Floor currentFloor = dungeon.getCurrentFloor();
        if (!currentFloor.getCurrentRoom().isCleared()) {
            renderer.displayMessage("You must clear this room before advancing.");
            moved = false;
            return;
        }
        if (currentFloor.canAdvance()) {
            currentFloor.advance();
            moved = true;
        } else if (dungeon.canAdvanceFloor()) {
            dungeon.advanceFloor();
            renderer.displayMessage("\nYou descend deeper into the dungeon...\n");
            moved = true;
        } else if (dungeon.isComplete()) {
            moved = true;
        } else {
            renderer.displayMessage("There is nowhere left to go on this floor.");
            moved = false;
        }
    }

    @Override
    public String getDescription() {
        return "Move forward through the dungeon";
    }

    public boolean hasMoved() { return moved; }
}