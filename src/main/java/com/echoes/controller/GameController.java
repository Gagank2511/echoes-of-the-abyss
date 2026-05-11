package com.echoes.controller;

import com.echoes.model.character.Player;
import com.echoes.model.character.PlayerClass;
import com.echoes.model.combat.CombatEngine;
import com.echoes.model.dungeon.Dungeon;
import com.echoes.model.item.consumable.HealthPotion;
import com.echoes.pattern.command.CommandHistory;
import com.echoes.pattern.observer.GameEventPublisher;
import com.echoes.pattern.observer.listeners.AchievementTracker;
import com.echoes.pattern.observer.listeners.ScoreTracker;
import com.echoes.pattern.state.ExploringState;
import com.echoes.pattern.state.GameContext;
import com.echoes.util.GameConstants;
import com.echoes.view.GameRenderer;

public final class GameController {

    private final Dungeon dungeon;
    private final InputHandler inputHandler;
    private final GameRenderer renderer;
    private final GameEventPublisher eventPublisher;
    private final ScoreTracker scoreTracker;
    private final AchievementTracker achievementTracker;

    public GameController(final Dungeon dungeon,
                          final InputHandler inputHandler,
                          final GameRenderer renderer,
                          final GameEventPublisher eventPublisher,
                          final ScoreTracker scoreTracker) {
        this.dungeon = dungeon;
        this.inputHandler = inputHandler;
        this.renderer = renderer;
        this.eventPublisher = eventPublisher;
        this.scoreTracker = scoreTracker;
        this.achievementTracker = eventPublisher.getListeners().stream()
                .filter(l -> l instanceof AchievementTracker)
                .map(l -> (AchievementTracker) l)
                .findFirst()
                .orElse(new AchievementTracker());
    }

    public void run() {
        renderer.displayTitleScreen();
        Player player = createPlayer();
        GameContext context = new GameContext(
                player, dungeon,
                new CombatEngine(),
                renderer,
                eventPublisher,
                new CommandHistory(),
                new ExploringState());

        while (!context.isTerminal()) {
            System.out.print("\n  > ");
            String input = inputHandler.readLine();
            context.handleInput(input);
        }

        displayEndOfGameSummary();
    }

    private Player createPlayer() {
        String name = inputHandler.prompt(
                "  Enter your hero's name: ").trim();
        if (name.isBlank()) name = "Adventurer";
        renderer.displayClassSelection();
        PlayerClass playerClass = selectClass();
        Player player = new Player(name, playerClass);
        for (int i = 0; i < GameConstants.STARTING_POTIONS; i++) {
            player.getInventory().addItem(new HealthPotion());
        }
        renderer.displayMessage(
                "\nWelcome, " + name + " the "
                + playerClass.getDisplayName() + "! Your descent begins...\n");
        return player;
    }

    private PlayerClass selectClass() {
        String input = inputHandler.prompt(
                "  Choose class [1/2/3]: ").trim();
        return switch (input) {
            case "2" -> PlayerClass.MAGE;
            case "3" -> PlayerClass.RANGER;
            default  -> PlayerClass.WARRIOR;
        };
    }

    private void displayEndOfGameSummary() {
        System.out.println();
        System.out.println(
                "  ── FINAL SUMMARY ──────────────────────────────────────");
        System.out.println("  Final Score : " + scoreTracker.getScore());
        System.out.println("  Enemies Defeated : "
                + achievementTracker.getEnemiesDefeated());
        if (!achievementTracker.getUnlockedAchievements().isEmpty()) {
            System.out.println(
                    "\n  ── ACHIEVEMENTS ──────────────────────────────────────");
            achievementTracker.getUnlockedAchievements()
                    .forEach(a -> System.out.println("  ✔ " + a));
        }
        System.out.println(
                "  ─────────────────────────────────────────────────────");
        System.out.println("  Thank you for playing Echoes of the Abyss!");
        System.out.println();
    }
}