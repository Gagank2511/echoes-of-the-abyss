package com.echoes.pattern.command;

import com.echoes.model.character.Player;
import com.echoes.model.item.consumable.Consumable;
import com.echoes.model.item.consumable.ItemEffect;
import com.echoes.pattern.observer.GameEvent;
import com.echoes.pattern.observer.GameEventPublisher;
import com.echoes.pattern.observer.GameEventType;
import com.echoes.view.GameRenderer;

public final class UseItemCommand implements Command {

    private final Player player;
    private final Consumable item;
    private final GameRenderer renderer;
    private final GameEventPublisher eventPublisher;

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