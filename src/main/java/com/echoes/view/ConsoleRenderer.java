package com.echoes.view;

import java.util.List;

import com.echoes.model.character.Player;
import com.echoes.model.character.enemy.Enemy;
import com.echoes.model.character.enemy.boss.BossEnemy;
import com.echoes.model.combat.AttackResult;
import com.echoes.model.combat.CombatResult;
import com.echoes.model.dungeon.Floor;
import com.echoes.model.dungeon.Room;
import com.echoes.model.inventory.Inventory;
import com.echoes.model.item.consumable.Consumable;
import com.echoes.pattern.observer.GameEvent;
import com.echoes.util.TextColour;

public final class ConsoleRenderer implements GameRenderer {

    private static final int SEPARATOR_WIDTH = 60;
    private static final String SEPARATOR = "=".repeat(SEPARATOR_WIDTH);
    private static final String THIN_SEPARATOR = "-".repeat(SEPARATOR_WIDTH);

    @Override
    public void displayTitleScreen() {
        println(TextColour.wrap(SEPARATOR, TextColour.MAGENTA));
        println(TextColour.wrap("        ★  ECHOES OF THE ABYSS  ★",
                TextColour.BOLD + TextColour.MAGENTA));
        println(TextColour.wrap("     A Turn-Based Dungeon Adventure", TextColour.CYAN));
        println(TextColour.wrap(SEPARATOR, TextColour.MAGENTA));
        println();
    }

    @Override
    public void displayClassSelection() {
        println(TextColour.wrap("Choose your class:", TextColour.YELLOW));
        println(TextColour.wrap(THIN_SEPARATOR, TextColour.YELLOW));
        println(TextColour.wrap("  [1] Warrior", TextColour.RED)
                + " — HP: 110  ATK:  9  DEF: 5 | Reliable melee fighter.");
        println(TextColour.wrap("  [2] Mage   ", TextColour.BLUE)
                + " — HP:  75  ATK: 13  DEF: 1 | Magic pierces armour.");
        println(TextColour.wrap("  [3] Ranger ", TextColour.GREEN)
                + " — HP:  90  ATK: 10  DEF: 3 | Agile archer.");
        println(TextColour.wrap(THIN_SEPARATOR, TextColour.YELLOW));
    }

    @Override
    public void displayRoomHeader(final Floor floor, final Room room) {
        println();
        println(TextColour.wrap(SEPARATOR, TextColour.CYAN));
        println(TextColour.wrap(
                "  Floor " + floor.getFloorNumber() + "/"
                + floor.getRooms().size() + "  ›  " + floor.getName(),
                TextColour.BOLD + TextColour.CYAN));
        println(TextColour.wrap(
                "  Room " + room.getRoomNumber() + ": " + room.getName(),
                TextColour.WHITE));
        println(TextColour.wrap(SEPARATOR, TextColour.CYAN));
        println(TextColour.wrap("  " + room.getFlavourText(), TextColour.WHITE));
        println();
    }

    @Override
    public void displayEnemyEncounter(final Enemy enemy) {
        boolean isBoss = enemy instanceof BossEnemy;
        String colour = isBoss ? TextColour.RED : TextColour.YELLOW;
        String label  = isBoss ? "⚔  BOSS ENCOUNTER" : "⚔  Enemy Encountered";
        println(TextColour.wrap(label + ": " + enemy.getName(), colour));
        println(TextColour.wrap("  " + enemy.getDescription(), TextColour.WHITE));
        println();
    }

    @Override
    public void displayCombatHeader(final Player player, final Enemy enemy) {
        println(TextColour.wrap(SEPARATOR, TextColour.RED));
        println(TextColour.wrap("  ⚔  COMBAT", TextColour.BOLD + TextColour.RED));
        println(TextColour.wrap(SEPARATOR, TextColour.RED));
        displayCombatStatus(player, enemy);
    }

    @Override
    public void displayCombatStatus(final Player player, final Enemy enemy) {
        println(String.format("  %s  HP: %s",
                TextColour.wrap(player.getName(), TextColour.GREEN),
                renderHealthBar(player.getCurrentHealth(), player.getMaxHealth())));
        println(String.format("  %s  HP: %s",
                TextColour.wrap(enemy.getName(), TextColour.RED),
                renderHealthBar(enemy.getCurrentHealth(), enemy.getMaxHealth())));
        println();
    }

    @Override
    public void displayAttackResult(final AttackResult result,
                                    final String attackerName) {
        String colour = result.isCritical() ? TextColour.YELLOW
                      : result.isMiss()     ? TextColour.CYAN
                      : TextColour.WHITE;
        println(TextColour.wrap("  › " + result.getDescription(), colour));
    }

    @Override
    public void displayCombatVictory(final CombatResult result) {
        println();
        println(TextColour.wrap("  ✔ VICTORY!", TextColour.GREEN + TextColour.BOLD));
        println(TextColour.wrap("  " + result.getSummary(), TextColour.GREEN));
        println();
    }

    @Override
    public void displayExploreMenu() {
        println(TextColour.wrap(THIN_SEPARATOR, TextColour.CYAN));
        println(TextColour.wrap(
                "  [m] Move forward    [s] Stats    [i] Inventory    [h] Help",
                TextColour.CYAN));
        println(TextColour.wrap(THIN_SEPARATOR, TextColour.CYAN));
    }

    @Override
    public void displayCombatMenu(final boolean hasItems, final boolean canFlee) {
        println(TextColour.wrap(THIN_SEPARATOR, TextColour.RED));
        String menu = "  [a] Attack    [s] Stats";
        if (hasItems) menu += "    [i] Use Item";
        if (canFlee)  menu += "    [f] Flee";
        println(TextColour.wrap(menu, TextColour.RED));
        println(TextColour.wrap(THIN_SEPARATOR, TextColour.RED));
    }

    @Override
    public void displayPlayerStats(final Player player) {
        println();
        println(TextColour.wrap(THIN_SEPARATOR, TextColour.GREEN));
        println(TextColour.wrap("  PLAYER STATS", TextColour.BOLD + TextColour.GREEN));
        println(TextColour.wrap(THIN_SEPARATOR, TextColour.GREEN));
        println("  Name  : " + player.getName());
        println("  Class : " + player.getPlayerClass().getDisplayName());
        println("  Level : " + player.getLevel());
        println(String.format("  HP    : %d / %d",
                player.getCurrentHealth(), player.getMaxHealth()));
        println("  ATK   : " + player.getAttackPower()
                + (player.hasActiveBoost()
                ? TextColour.wrap(" (boosted!)", TextColour.YELLOW) : ""));
        println("  DEF   : " + player.getDefence());
        println(String.format("  XP    : %d / %d",
                player.getExperience(), player.getExperienceToNextLevel()));
        println("  Gold  : " + player.getGold());
        println(TextColour.wrap(THIN_SEPARATOR, TextColour.GREEN));
        println();
    }

    @Override
    public void displayInventory(final Inventory inventory) {
        println();
        println(TextColour.wrap(THIN_SEPARATOR, TextColour.YELLOW));
        println(TextColour.wrap("  INVENTORY", TextColour.BOLD + TextColour.YELLOW));
        println(TextColour.wrap(THIN_SEPARATOR, TextColour.YELLOW));
        if (inventory.isEmpty()) {
            println("  [empty]");
        } else {
            List<Consumable> items = inventory.getItems();
            for (int i = 0; i < items.size(); i++) {
                println(String.format("  [%d] %-20s — %s",
                        i + 1, items.get(i).getName(),
                        items.get(i).getDescription()));
            }
        }
        println(TextColour.wrap(THIN_SEPARATOR, TextColour.YELLOW));
        println();
    }

    @Override
    public void displayGameOver(final Player player) {
        println();
        println(TextColour.wrap(SEPARATOR, TextColour.RED));
        println(TextColour.wrap("          ✖  GAME OVER  ✖",
                TextColour.BOLD + TextColour.RED));
        println(TextColour.wrap(SEPARATOR, TextColour.RED));
        println(TextColour.wrap(
                "  " + player.getName() + " has fallen in the abyss.",
                TextColour.WHITE));
        println(TextColour.wrap(SEPARATOR, TextColour.RED));
        println();
    }

    @Override
    public void displayVictory(final Player player) {
        println();
        println(TextColour.wrap(SEPARATOR, TextColour.MAGENTA));
        println(TextColour.wrap("        ★  DUNGEON CLEARED!  ★",
                TextColour.BOLD + TextColour.MAGENTA));
        println(TextColour.wrap(SEPARATOR, TextColour.MAGENTA));
        println(TextColour.wrap(
                "  " + player.getName() + " has conquered the Abyss!",
                TextColour.GREEN));
        println(TextColour.wrap(SEPARATOR, TextColour.MAGENTA));
        println();
    }

    @Override
    public void onEvent(final GameEvent event) {
        switch (event.getType()) {
            case PLAYER_LEVELLED_UP -> println(TextColour.wrap(
                    "  ★ LEVEL UP! You grow stronger.",
                    TextColour.YELLOW + TextColour.BOLD));
            case BOSS_DEFEATED -> println(TextColour.wrap(
                    "  ★ BOSS DEFEATED! A great evil is vanquished.",
                    TextColour.MAGENTA + TextColour.BOLD));
            default -> {}
        }
    }

    @Override
    public void displayMessage(final String message) {
        println("  " + message);
    }

    private String renderHealthBar(final int current, final int max) {
        final int barWidth = 20;
        int filled = (max == 0) ? 0
                : (int) ((double) current / max * barWidth);
        filled = Math.max(0, Math.min(barWidth, filled));
        String bar = "█".repeat(filled) + "░".repeat(barWidth - filled);
        double percent = (max == 0) ? 0 : (double) current / max;
        String colour = percent > 0.5 ? TextColour.GREEN
                      : percent > 0.25 ? TextColour.YELLOW
                      : TextColour.RED;
        return TextColour.wrap("[" + bar + "]", colour)
                + String.format(" %d/%d", current, max);
    }

    private void println(final String text) { System.out.println(text); }
    private void println() { System.out.println(); }
}