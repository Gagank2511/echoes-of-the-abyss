package com.echoes.pattern.state;

import java.util.List;

import com.echoes.model.dungeon.Floor;
import com.echoes.model.dungeon.Room;
import com.echoes.model.item.consumable.Consumable;
import com.echoes.pattern.command.MoveCommand;

public final class ExploringState implements GameState {

    @Override
    public void onEnter(final GameContext context) {
        if (context.getDungeon().isComplete()) {
            context.setState(new VictoryState());
            return;
        }
        Floor floor = context.getDungeon().getCurrentFloor();
        Room room = floor.getCurrentRoom();
        context.getRenderer().displayRoomHeader(floor, room);
        collectItems(context, room);
        if (room.hasLivingEnemy()) {
            room.getEnemy().ifPresent(enemy -> {
                context.getRenderer().displayEnemyEncounter(enemy);
                context.setState(new CombatState(enemy));
            });
        } else {
            context.getRenderer().displayExploreMenu();
        }
    }

    @Override
    public void handleInput(final GameContext context, final String input) {
        switch (input.trim().toLowerCase()) {
            case "m", "move" -> {
                MoveCommand move = new MoveCommand(
                        context.getDungeon(), context.getRenderer());
                move.execute();
                context.getCommandHistory().record(move);
                context.setState(new ExploringState());
            }
            case "s", "stats" ->
                context.getRenderer().displayPlayerStats(context.getPlayer());
            case "i", "inventory" ->
                context.getRenderer().displayInventory(
                        context.getPlayer().getInventory());
            case "h", "help" ->
                context.getRenderer().displayExploreMenu();
            default ->
                context.getRenderer().displayMessage(
                        "Unknown command. Type 'h' for help.");
        }
    }

    @Override
    public boolean isTerminal() { return false; }

    private void collectItems(final GameContext context, final Room room) {
        List<Consumable> items = room.getItems();
        if (items.isEmpty()) return;
        for (Consumable item : items) {
            boolean added = context.getPlayer().getInventory().addItem(item);
            if (added) {
                context.getRenderer().displayMessage(
                        "You found: " + item.getName()
                                + " — " + item.getDescription());
            } else {
                context.getRenderer().displayMessage(
                        "Your inventory is full! Could not carry the "
                                + item.getName() + ".");
            }
        }
        room.clearItems();
    }
}