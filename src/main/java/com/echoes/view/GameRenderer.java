package com.echoes.view;

import com.echoes.model.character.Player;
import com.echoes.model.character.enemy.Enemy;
import com.echoes.model.combat.AttackResult;
import com.echoes.model.combat.CombatResult;
import com.echoes.model.dungeon.Floor;
import com.echoes.model.dungeon.Room;
import com.echoes.model.inventory.Inventory;
import com.echoes.pattern.observer.GameEventListener;

/**
 * View interface defining all rendering operations used by the game.
 *
 * <p>Separating the rendering contract from its implementation follows the
 * Dependency Inversion Principle. The controller and state classes depend
 * only on this interface — swapping the console renderer for a graphical
 * implementation requires no changes anywhere outside the view layer.</p>
 *
 * <p>Extending {@link GameEventListener} allows any renderer to subscribe
 * to the event bus and react inline to events such as level-ups, without
 * needing a separate notification mechanism.</p>
 *
 * @author Your Name
 * @version 1.0
 */
public interface GameRenderer extends GameEventListener {

    /** Displays the game's title screen and welcome banner. */
    void displayTitleScreen();

    /** Displays the class-selection screen. */
    void displayClassSelection();

    /**
     * Displays the room header as the player enters a room.
     *
     * @param floor the current floor
     * @param room  the room being entered
     */
    void displayRoomHeader(Floor floor, Room room);

    /**
     * Displays an encounter message when a living enemy is found.
     *
     * @param enemy the enemy encountered
     */
    void displayEnemyEncounter(Enemy enemy);

    /**
     * Displays the combat header showing both combatants' status.
     *
     * @param player the player character
     * @param enemy  the opposing enemy
     */
    void displayCombatHeader(Player player, Enemy enemy);

    /**
     * Displays live HP bars during combat.
     *
     * @param player the player
     * @param enemy  the enemy
     */
    void displayCombatStatus(Player player, Enemy enemy);

    /**
     * Displays the narrative result of a single attack.
     *
     * @param result       the attack outcome
     * @param attackerName the name of the attacker
     */
    void displayAttackResult(AttackResult result, String attackerName);

    /**
     * Displays the post-combat victory summary.
     *
     * @param result the full combat result including rewards
     */
    void displayCombatVictory(CombatResult result);

    /**
     * Displays the player's current statistics panel.
     *
     * @param player the player whose stats to display
     */
    void displayPlayerStats(Player player);

    /**
     * Displays the player's inventory.
     *
     * @param inventory the inventory to display
     */
    void displayInventory(Inventory inventory);

    /** Displays the exploration action menu. */
    void displayExploreMenu();

    /**
     * Displays the combat action menu.
     *
     * @param hasItems whether the player has usable items
     * @param canFlee  whether fleeing is permitted
     */
    void displayCombatMenu(boolean hasItems, boolean canFlee);

    /**
     * Displays a generic informational message.
     *
     * @param message the message to show
     */
    void displayMessage(String message);

    /**
     * Displays the game-over screen.
     *
     * @param player the defeated player
     */
    void displayGameOver(Player player);

    /**
     * Displays the victory screen.
     *
     * @param player the victorious player
     */
    void displayVictory(Player player);
}
