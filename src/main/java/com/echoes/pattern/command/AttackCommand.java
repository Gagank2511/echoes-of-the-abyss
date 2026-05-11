package com.echoes.pattern.command;

import com.echoes.model.character.Player;
import com.echoes.model.character.enemy.Enemy;
import com.echoes.model.combat.AttackResult;
import com.echoes.model.combat.CombatEngine;
import com.echoes.pattern.observer.GameEvent;
import com.echoes.pattern.observer.GameEventPublisher;
import com.echoes.pattern.observer.GameEventType;
import com.echoes.view.GameRenderer;

public final class AttackCommand implements Command {

    private final Player player;
    private final Enemy enemy;
    private final CombatEngine combatEngine;
    private final GameRenderer renderer;
    private final GameEventPublisher eventPublisher;
    private AttackResult result;

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