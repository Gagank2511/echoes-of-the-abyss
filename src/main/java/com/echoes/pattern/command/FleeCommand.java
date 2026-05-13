package com.echoes.pattern.command;

import com.echoes.model.character.enemy.Enemy;
import com.echoes.model.character.enemy.boss.BossEnemy;
import com.echoes.model.combat.CombatEngine;
import com.echoes.pattern.observer.GameEvent;
import com.echoes.pattern.observer.GameEventPublisher;
import com.echoes.pattern.observer.GameEventType;
import com.echoes.view.GameRenderer;

/**
 * Command to attempt fleeing from combat.
 *
 * <p>This command tries to escape the current fight. Fleeing from bosses
 * is impossible, and regular enemies have a chance to prevent escape.
 * It implements the Command pattern for undoable actions.</p>
 */
public final class FleeCommand implements Command {

    private final Enemy enemy;
    private final CombatEngine combatEngine;
    private final GameRenderer renderer;
    private final GameEventPublisher eventPublisher;
    private boolean fleeSuccessful;

    /**
     * Constructs a flee command with the necessary dependencies.
     *
     * @param enemy          the enemy being fled from
     * @param combatEngine   the combat calculation service
     * @param renderer       the view for displaying results
     * @param eventPublisher the event bus for notifications
     */
    public FleeCommand(final Enemy enemy,
                       final CombatEngine combatEngine,
                       final GameRenderer renderer,
                       final GameEventPublisher eventPublisher) {
        this.enemy = enemy;
        this.combatEngine = combatEngine;
        this.renderer = renderer;
        this.eventPublisher = eventPublisher;
        this.fleeSuccessful = false;
    }

    @Override
    public void execute() {
        boolean isBoss = enemy instanceof BossEnemy;
        fleeSuccessful = combatEngine.attemptFlee(isBoss);
        if (fleeSuccessful) {
            renderer.displayMessage("You turn and flee into the shadows!");
            eventPublisher.publish(new GameEvent(GameEventType.PLAYER_FLED, 0));
        } else if (isBoss) {
            renderer.displayMessage(
                    enemy.getName() + " blocks every escape route. You cannot flee from a boss!");
        } else {
            renderer.displayMessage("You stumble as you try to run — escape denied!");
        }
    }

    @Override
    public String getDescription() {
        return "Flee from " + enemy.getName();
    }

    public boolean isFleeSuccessful() { return fleeSuccessful; }
}