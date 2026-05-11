package com.echoes.pattern.builder;

import java.util.ArrayList;
import java.util.List;

import com.echoes.model.character.enemy.EnemyType;
import com.echoes.model.dungeon.Floor;
import com.echoes.model.dungeon.Room;
import com.echoes.model.dungeon.RoomType;
import com.echoes.model.item.consumable.Consumable;
import com.echoes.model.item.consumable.EnergyElixir;
import com.echoes.model.item.consumable.HealthPotion;
import com.echoes.model.item.consumable.PowerCrystal;
import com.echoes.model.item.consumable.StoneAmulet;
import com.echoes.pattern.factory.EnemyFactory;
import com.echoes.pattern.factory.StandardEnemyFactory;
import com.echoes.util.DiceRoller;
import com.echoes.util.GameConstants;

public final class FloorBuilder {

    private static final EnemyFactory FACTORY = new StandardEnemyFactory();
    private final int floorNumber;

    public FloorBuilder(final int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Floor build() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(buildEntranceRoom());
        for (int i = 2; i <= GameConstants.ROOMS_PER_FLOOR - 1; i++) {
            rooms.add((i % 3 == 0) ? buildTreasureRoom(i) : buildMonsterRoom(i));
        }
        rooms.add(buildBossRoom(GameConstants.ROOMS_PER_FLOOR));
        return new Floor(floorNumber, getFloorName(), rooms);
    }

    private Room buildEntranceRoom() {
        return new Room(1, RoomType.ENTRANCE,
                "Floor " + floorNumber + " Entrance",
                "You descend into " + getFloorName()
                        + ". The air is cold and thick with the smell of decay.");
    }

    private Room buildMonsterRoom(final int roomIndex) {
        Room room = new Room(roomIndex, RoomType.MONSTER,
                selectRoomName(roomIndex),
                selectRoomFlavour(roomIndex));
        room.setEnemy(FACTORY.create(selectEnemyType(), floorNumber));
        if (DiceRoller.chance(0.30)) {
            room.addItem(new HealthPotion());
        }
        return room;
    }

    private Room buildTreasureRoom(final int roomIndex) {
        Room room = new Room(roomIndex, RoomType.TREASURE,
                "Forgotten Vault",
                "A side chamber untouched for centuries. Something glimmers "
                        + "in a stone alcove.");
        room.addItem(selectTreasureItem());
        return room;
    }

    private Room buildBossRoom(final int roomIndex) {
        Room room = new Room(roomIndex, RoomType.BOSS,
                getBossRoomName(),
                getBossRoomFlavour());
        room.setEnemy(FACTORY.create(getBossType(), floorNumber));
        room.addItem(new StoneAmulet());
        return room;
    }

    private String getFloorName() {
        return switch (floorNumber) {
            case 1 -> "The Shattered Gatehouse";
            case 2 -> "The Drowning Crypts";
            case 3 -> "The Ashen Corridors";
            case 4 -> "The Obsidian Citadel";
            case 5 -> "The Abyss Throne";
            default -> "Dungeon Floor " + floorNumber;
        };
    }

    private EnemyType selectEnemyType() {
        return switch (floorNumber) {
            case 1 -> EnemyType.GOBLIN;
            case 2 -> EnemyType.SKELETON;
            case 3 -> DiceRoller.chance(0.5) ? EnemyType.TROLL : EnemyType.SKELETON;
            case 4 -> DiceRoller.chance(0.5) ? EnemyType.DRAGON_WHELP : EnemyType.TROLL;
            default -> EnemyType.DRAGON_WHELP;
        };
    }

    private EnemyType getBossType() {
        return switch (floorNumber) {
            case 1 -> EnemyType.STONE_GOLEM;
            case 2, 3 -> EnemyType.LICH;
            default -> EnemyType.ABYSS_DRAGON;
        };
    }

    private String getBossRoomName() {
        return switch (floorNumber) {
            case 1 -> "The Throne of Stone";
            case 2, 3 -> "The Chamber of Eternity";
            default -> "The Abyss Throne";
        };
    }

    private String getBossRoomFlavour() {
        return switch (floorNumber) {
            case 1 -> "Enormous stone pillars have cracked under ancient weight. "
                    + "A grinding, reverberating sound fills the chamber.";
            case 2, 3 -> "Necromantic runes sear every surface. A chill takes root "
                    + "in your chest. Something ancient stirs in the darkness.";
            default -> "The very stone glows with void-heat. The ceiling writhes "
                    + "with shadow-wings. A deep, contemptuous rumble shakes your bones.";
        };
    }

    private Consumable selectTreasureItem() {
        return switch (floorNumber) {
            case 1, 2 -> new HealthPotion();
            case 3    -> DiceRoller.chance(0.5) ? new EnergyElixir() : new HealthPotion();
            default   -> DiceRoller.chance(0.5) ? new PowerCrystal() : new EnergyElixir();
        };
    }

    private String selectRoomName(final int roomIndex) {
        String[] names = {
            "Crumbling Antechamber", "Mould-Slick Passage",
            "Collapsed Great Hall", "Flooded Lower Vault",
            "Scorched Barracks", "Twisted Corridor"
        };
        return names[(roomIndex + floorNumber) % names.length];
    }

    private String selectRoomFlavour(final int roomIndex) {
        String[] flavours = {
            "Water drips steadily from a crack in the ceiling. Something moves in the dark.",
            "Rusted iron sconces line the walls, their torches long extinguished.",
            "The floor is strewn with the remnants of a long-dead expedition.",
            "A low growl echoes from ahead. You tighten your grip on your weapon.",
            "Claw marks score the stone walls at chest height. Fresh ones."
        };
        return flavours[(roomIndex + floorNumber) % flavours.length];
    }
}