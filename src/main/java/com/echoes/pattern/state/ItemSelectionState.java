package com.echoes.pattern.state;

import com.echoes.model.character.enemy.Enemy;
import com.echoes.model.item.consumable.Consumable;
import com.echoes.pattern.command.UseItemCommand;
import java.util.List;

/**
 * Handles inventory item selection and item usage during gameplay.
 * This state allows the player to interact with consumable items
 * separately from exploration and combat behaviour.
 */

public final class ItemSelectionState implements GameState {

    private final Enemy enemy;
    private final List<Consumable> items;

    public ItemSelectionState(final Enemy enemy, final List<Consumable> items) {
        this.enemy = enemy;
        this.items = items;
    }

    @Override
    public void onEnter(final GameContext context) {}

    @Override
    public void handleInput(final GameContext context, final String input) {
        int choice;
        try {
            choice = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            context.getRenderer().displayMessage("Please enter a valid number.");
            return;
        }

        if (choice == 0) {
            context.setState(new CombatState(enemy));
            return;
        }

        if (choice < 1 || choice > items.size()) {
            context.getRenderer().displayMessage(
                    "Invalid selection. Enter a number between 1 and "
                            + items.size() + ".");
            return;
        }

        Consumable selected = items.get(choice - 1);
        UseItemCommand useItem = new UseItemCommand(
                context.getPlayer(), selected,
                context.getRenderer(),
                context.getEventPublisher());
        useItem.execute();
        context.getCommandHistory().record(useItem);
        context.setState(new CombatState(enemy));
    }

    @Override
    public boolean isTerminal() { return false; }
}