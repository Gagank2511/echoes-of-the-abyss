package com.echoes.pattern.command;

import com.echoes.model.character.Player;
import com.echoes.model.character.enemy.Enemy;
import com.echoes.model.combat.AttackResult;
import com.echoes.model.combat.CombatEngine;
import com.echoes.pattern.observer.GameEvent;
import com.echoes.pattern.observer.GameEventPublisher;
import com.echoes.pattern.observer.GameEventType;
import com.echoes.view.GameRenderer;

/**
 * Command to execute a player attack against an enemy.
 *
 * <p>This command performs the attack, displays the result, and publishes
 * the appropriate game event. It implements the Command pattern for
 * undoable actions.</p>
 */
public final class AttackCommand implements Command {

    private final Player player;
    private final Enemy enemy;
    private final CombatEngine combatEngine;
    private final GameRenderer renderer;
    private final GameEventPublisher eventPublisher;
    private AttackResult result;

    /**
     * Constructs an attack command with the necessary dependencies.
     *
     * @param player         the attacking player
     * @param enemy          the target enemy
     * @param combatEngine   the combat calculation service
     * @param renderer       the view for displaying results
     * @param eventPublisher the event bus for notifications
     */
    public AttackCommand(final Player player,
                         final Enemy enemy,
                         final CombatEngine combatEngine,
                         final GameRenderer renderer,
                         final GameEventPublisher eventPublisher) {
        this.player = player;
        this.enemy = enemy;
        this.combatEngine = combatEngine;
        this.renderer = renderer;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void execute() {
        result = combatEngine.executePlayerAttack(player, enemy);
        renderer.displayAttackResult(result, player.getName());
        eventPublisher.publish(
                new GameEvent(GameEventType.PLAYER_ATTACKED, result.getNetDamage()));
    }

    @Override
    public String getDescription() {
        return player.getName() + " attacks " + enemy.getName();
    }

    public AttackResult getResult() { return result; }
}