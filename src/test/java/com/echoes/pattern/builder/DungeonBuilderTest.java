package com.echoes.pattern.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.echoes.model.dungeon.Dungeon;
import com.echoes.model.dungeon.Floor;
import com.echoes.model.dungeon.RoomType;

@DisplayName("DungeonBuilder Tests")
class DungeonBuilderTest {

    @Test
    @DisplayName("build creates dungeon with correct floor count")
    void build_createsCorrectFloorCount() {
        Dungeon dungeon = new DungeonBuilder()
                .withName("Test Dungeon")
                .withFloorCount(3)
                .build();
        assertEquals(3, dungeon.getTotalFloors());
    }

    @Test
    @DisplayName("build sets dungeon name correctly")
    void build_setsDungeonName() {
        Dungeon dungeon = new DungeonBuilder()
                .withName("The Dark Pit")
                .withFloorCount(1)
                .build();
        assertEquals("The Dark Pit", dungeon.getName());
    }

    @Test
    @DisplayName("Each floor starts at entrance room")
    void floor_startsAtEntranceRoom() {
        Dungeon dungeon = new DungeonBuilder().withFloorCount(2).build();
        for (Floor floor : dungeon.getFloors()) {
            assertEquals(RoomType.ENTRANCE,
                    floor.getCurrentRoom().getRoomType());
        }
    }

    @Test
    @DisplayName("Final room on each floor is a boss room")
    void floor_finalRoomIsBoss() {
        Dungeon dungeon = new DungeonBuilder().withFloorCount(3).build();
        for (Floor floor : dungeon.getFloors()) {
            RoomType lastType = floor.getRooms()
                    .get(floor.getRooms().size() - 1)
                    .getRoomType();
            assertEquals(RoomType.BOSS, lastType);
        }
    }

    @Test
    @DisplayName("Boss rooms contain an enemy")
    void bossRoom_containsEnemy() {
        Dungeon dungeon = new DungeonBuilder().withFloorCount(2).build();
        for (Floor floor : dungeon.getFloors()) {
            boolean bossHasEnemy = floor.getRooms()
                    .get(floor.getRooms().size() - 1)
                    .getEnemy()
                    .isPresent();
            assertTrue(bossHasEnemy);
        }
    }

    @Test
    @DisplayName("withFloorCount throws for floor count less than 1")
    void withFloorCount_throwsForInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new DungeonBuilder().withFloorCount(0));
    }

    @Test
    @DisplayName("Dungeon is not complete at start")
    void dungeon_notCompleteAtStart() {
        Dungeon dungeon = new DungeonBuilder().withFloorCount(5).build();
        assertFalse(dungeon.isComplete());
    }

    @Test
    @DisplayName("Dungeon can advance floor when first floor complete")
    void dungeon_cannotAdvanceFloorAtStart() {
        Dungeon dungeon = new DungeonBuilder().withFloorCount(2).build();
        assertFalse(dungeon.canAdvanceFloor());
    }
}