package com.echoes.pattern.command;

import com.echoes.model.character.Player;
import com.echoes.model.item.consumable.Consumable;
import com.echoes.model.item.consumable.ItemEffect;
import com.echoes.pattern.observer.GameEvent;
import com.echoes.pattern.observer.GameEventPublisher;
import com.echoes.pattern.observer.GameEventType;
import com.echoes.view.GameRenderer;

/**
 * Command to use a consumable item.
 *
 * <p>This command applies the item's effect to the player, displays
 * the result, removes exhausted items from inventory, and publishes
 * the appropriate game event. It implements the Command pattern for
 * undoable actions.</p>
 */
public final class UseItemCommand implements Command {

    private final Player player;
    private final Consumable item;
    private final GameRenderer renderer;
    private final GameEventPublisher eventPublisher;

    /**
     * Constructs a use item command with the necessary dependencies.
     *
     * @param player         the player using the item
     * @param item           the consumable item to use
     * @param renderer       the view for displaying results
     * @param eventPublisher the event bus for notifications
     */
    public UseItemCommand(final Player player,
                          final Consumable item,
                          final GameRenderer renderer,
                          final GameEventPublisher eventPublisher) {
        this.player = player;
        this.item = item;
        this.renderer = renderer;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void execute() {
        ItemEffect effect = item.use();
        player.applyItemEffect(effect);
        renderer.displayMessage(effect.getDescription());
        if (item.isExhausted()) {
            player.getInventory().removeItem(item);
        }
        eventPublisher.publish(new GameEvent(GameEventType.ITEM_USED, 1));
    }

    @Override
    public String getDescription() {
        return player.getName() + " uses " + item.getName();
    }
}