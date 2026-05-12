package com.echoes.pattern.observer.listeners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.echoes.pattern.observer.GameEvent;
import com.echoes.pattern.observer.GameEventListener;

/**
 * Observer that tracks in-game achievements earned during the dungeon run.
 *
 * <p>Achievements are unlocked the first time a qualifying event occurs.
 * They are stored as strings and displayed at the end of the run. Adding
 * new achievements requires only adding logic in {@link #onEvent(GameEvent)}
 * — no other class needs to change.</p>
 */
public final class AchievementTracker implements GameEventListener {

    private final List<String> unlockedAchievements = new ArrayList<>();
    private int enemiesDefeated = 0;
    private int itemsUsed = 0;
    private int timesPlayerFled = 0;

    @Override
    public void onEvent(final GameEvent event) {
        switch (event.getType()) {
            case ENEMY_DEFEATED -> {
                enemiesDefeated++;
                checkEnemyMilestones();
            }
            case BOSS_DEFEATED   -> unlock("Boss Slayer — Defeated a floor boss!");
            case DUNGEON_CLEARED -> unlock("Abyssal Conqueror — Cleared the entire dungeon!");
            case ITEM_USED -> {
                itemsUsed++;
                if (itemsUsed == 1) unlock("Resourceful — Used your first item in battle.");
            }
            case PLAYER_FLED -> {
                timesPlayerFled++;
                if (timesPlayerFled == 3) unlock("Tactical Retreat — Fled from combat 3 times.");
            }
            default -> { /* No achievement logic for other events */ }
        }
    }

    /**
     * Checks and unlocks enemy-kill milestone achievements.
     */
    private void checkEnemyMilestones() {
        if (enemiesDefeated == 1)  unlock("First Blood — Defeated your first enemy.");
        if (enemiesDefeated == 5)  unlock("Dungeon Delver — Defeated 5 enemies.");
        if (enemiesDefeated == 10) unlock("Monster Slayer — Defeated 10 enemies.");
    }

    /**
     * Unlocks an achievement if it has not already been unlocked.
     *
     * @param achievement the achievement description to unlock
     */
    private void unlock(final String achievement) {
        if (!unlockedAchievements.contains(achievement)) {
            unlockedAchievements.add(achievement);
        }
    }

    /**
     * Returns all achievements unlocked during this run.
     *
     * @return unmodifiable list of achievement descriptions
     */
    public List<String> getUnlockedAchievements() {
        return Collections.unmodifiableList(unlockedAchievements);
    }

    /** @return total enemies defeated this run */
    public int getEnemiesDefeated() { return enemiesDefeated; }
}
