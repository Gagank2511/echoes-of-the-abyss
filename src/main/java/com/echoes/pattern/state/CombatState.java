package com.echoes.pattern.state;

import java.util.List;

import com.echoes.model.character.Player;
import com.echoes.model.character.enemy.Enemy;
import com.echoes.model.character.enemy.boss.BossEnemy;
import com.echoes.model.combat.AttackResult;
import com.echoes.model.combat.CombatResult;
import com.echoes.model.item.consumable.Consumable;
import com.echoes.pattern.command.AttackCommand;
import com.echoes.pattern.command.FleeCommand;
import com.echoes.pattern.observer.GameEvent;
import com.echoes.pattern.observer.GameEventType;

/**
 * Game state for handling turn-based combat encounters.
 *
 * <p>In this state, the player can attack, use items, or attempt to flee.
 * Combat continues until either the enemy is defeated or the player flees
 * or is defeated. This implements the State pattern for managing combat flow.</p>
 */
public final class CombatState implements GameState {

    private final Enemy enemy;

    /**
     * Constructs a combat state for fighting the given enemy.
     *
     * @param enemy the enemy to fight
     */
    public CombatState(final Enemy enemy) {
        this.enemy = java.util.Objects.requireNonNull(enemy);
    }

    @Override
    public void onEnter(final GameContext context) {
        context.getRenderer().displayCombatHeader(context.getPlayer(), enemy);
        context.getRenderer().displayCombatMenu(
                !context.getPlayer().getInventory().isEmpty(),
                !(enemy instanceof BossEnemy));
    }

    @Override
    public void handleInput(final GameContext context, final String input) {
        Player player = context.getPlayer();
        switch (input.trim().toLowerCase()) {
            case "a", "attack" -> {
                AttackCommand attack = new AttackCommand(
                        player, enemy,
                        context.getCombatEngine(),
                        context.getRenderer(),
                        context.getEventPublisher());
                attack.execute();
                context.getCommandHistory().record(attack);
                if (!enemy.isAlive()) {
                    handleEnemyDefeated(context, player);
                    return;
                }
                executeEnemyTurn(context, player);
            }
            case "i", "item" -> handleItemUse(context, player);
            case "f", "flee" -> {
                FleeCommand flee = new FleeCommand(
                        enemy,
                        context.getCombatEngine(),
                        context.getRenderer(),
                        context.getEventPublisher());
                flee.execute();
                context.getCommandHistory().record(flee);
                if (flee.isFleeSuccessful()) {
                    player.clearTemporaryBoosts();
                    context.setState(new ExploringState());
                    return;
                }
                executeEnemyTurn(context, player);
            }
            case "s", "stats" ->
                context.getRenderer().displayPlayerStats(player);
            default ->
                context.getRenderer().displayMessage(
                        "Unknown command. Try [a]ttack, [i]tem, [f]lee, [s]tats.");
        }
        if (enemy.isAlive() && player.isAlive()) {
            context.getRenderer().displayCombatStatus(player, enemy);
            context.getRenderer().displayCombatMenu(
                    !player.getInventory().isEmpty(),
                    !(enemy instanceof BossEnemy));
        }
    }

    @Override
    public boolean isTerminal() { return false; }

    private void executeEnemyTurn(final GameContext context,
                                   final Player player) {
        String preTurnEffect = context.getCombatEngine()
                .getPreTurnEffect(enemy);
        if (!preTurnEffect.isBlank()) {
            context.getRenderer().displayMessage(preTurnEffect);
        }
        AttackResult enemyResult = context.getCombatEngine()
                .executeEnemyTurn(enemy, player);
        context.getRenderer().displayAttackResult(
                enemyResult, enemy.getName());
        context.getEventPublisher().publish(
                new GameEvent(GameEventType.ENEMY_ATTACKED,
                        enemyResult.getNetDamage()));
        if (!player.isAlive()) {
            context.getEventPublisher().publish(
                    new GameEvent(GameEventType.PLAYER_DEFEATED, 0));
            context.setState(new GameOverState());
        }
    }

    private void handleEnemyDefeated(final GameContext context,
                                      final Player player) {
        boolean isBoss = enemy instanceof BossEnemy;
        CombatResult result = context.getCombatEngine()
                .buildVictoryResult(player, enemy);
        context.getRenderer().displayCombatVictory(result);
        player.clearTemporaryBoosts();
        context.getEventPublisher().publish(
                new GameEvent(GameEventType.ENEMY_DEFEATED,
                        result.getExperienceGained()));
        if (isBoss) {
            context.getEventPublisher().publish(
                    new GameEvent(GameEventType.BOSS_DEFEATED,
                            result.getGoldGained()));
        }
        context.getDungeon().getCurrentFloor()
                .getCurrentRoom().setCleared(true);
        context.setState(new ExploringState());
    }

    private void handleItemUse(final GameContext context,
                                final Player player) {
        List<Consumable> items = player.getInventory().getItems();
        if (items.isEmpty()) {
            context.getRenderer().displayMessage("Your inventory is empty.");
            return;
        }
        context.getRenderer().displayInventory(player.getInventory());
        context.getRenderer().displayMessage(
                "Enter item number to use (or 0 to cancel):");
        context.setState(new ItemSelectionState(enemy, items));
    }
}