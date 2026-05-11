# Echoes of the Abyss

> A turn-based dungeon exploration game built in Java, demonstrating
> advanced object-oriented design and multiple design patterns.

---

## Project Overview

**Echoes of the Abyss** is a single-player, console-based dungeon
exploration game written in Java 23. The player creates a hero —
Warrior, Mage, or Ranger — and descends through five unique dungeon
floors, each filled with enemies, treasure rooms, and a powerful
floor boss.

This project was developed as a university coursework assignment to
demonstrate knowledge of object-oriented programming techniques and
design patterns. The game serves as a practical implementation
showcasing how recognised software engineering principles can be
applied to a real, working application.

---

## Features

- Three playable classes — Warrior, Mage, Ranger
- Seven enemy types including three unique floor bosses
- Five themed dungeon floors with atmospheric descriptions
- Turn-based combat with critical hits, miss chance, and armour penetration
- Boss fights with special abilities that activate at low health
- Item system — Health Potions, Energy Elixirs, Power Crystals, Stone Amulets
- Player progression — levelling, stat increases, gold accumulation
- Score tracking and achievements via the Observer pattern
- Coloured ANSI terminal output with health bars

---

## Technologies Used

| Technology | Version | Purpose |
|---|---|---|
| Java | 23 | Core language |
| Maven | 3.9.15 | Build tool |
| JUnit Jupiter | 5.10.1 | Unit testing |

---

## Design Patterns Used

### 1. Factory Method
**Files:** `EnemyFactory`, `StandardEnemyFactory`, `EliteEnemyFactory`

Decouples dungeon construction from concrete enemy types. The
`FloorBuilder` calls `EnemyFactory.create(type, level)` without
knowing which concrete class is returned. Adding a new enemy type
requires only a new class — no changes to existing building logic.

### 2. Strategy
**Files:** `AttackStrategy`, `MeleeAttackStrategy`, `RangedAttackStrategy`, `MagicAttackStrategy`

Each character class attack algorithm is encapsulated in its own
strategy. The `Character` class holds a reference and delegates.
Swapping attack behaviour requires no changes to `Character` itself.

### 3. Observer
**Files:** `GameEventPublisher`, `GameEventListener`, `ScoreTracker`, `AchievementTracker`

The event bus completely decouples producers from consumers. Adding
a new listener requires no changes to any existing class.

### 4. State
**Files:** `GameState`, `ExploringState`, `CombatState`, `GameOverState`, `VictoryState`

Each game phase is a self-contained class owning its own input
handling logic. Adding a new phase requires only a new implementation
of the `GameState` interface.

### 5. Command
**Files:** `Command`, `AttackCommand`, `UseItemCommand`, `FleeCommand`, `MoveCommand`, `CommandHistory`

Every player action is encapsulated as an object. This enables
`CommandHistory` to record a complete action log without knowledge
of what any command does internally.

### 6. Builder
**Files:** `DungeonBuilder`, `FloorBuilder`

Complex dungeon construction is exposed as a fluent API.
`new DungeonBuilder().withName("...").withFloorCount(5).build()`
is readable and hides all room layout and enemy placement decisions.

### 7. Template Method
**Files:** `AbstractConsumable`, `Enemy`

The item-use algorithm is fixed in `AbstractConsumable.use()`;
subclasses provide only `calculateEffect()`. The enemy turn
algorithm is fixed in `Enemy.takeTurn()`; subclasses override
only the hooks they need.

---

## OOP Principles Demonstrated

| Principle | Where |
|---|---|
| **Encapsulation** | All fields private; controlled via protected setters in `Character` |
| **Abstraction** | `AttackStrategy`, `GameState`, `GameRenderer`, `EnemyFactory`, `Consumable` |
| **Inheritance** | `Player extends Character`, `Enemy extends Character`, `BossEnemy extends Enemy` |
| **Polymorphism** | `CombatEngine` operates on `Character`; states implement `GameState` |
| **Composition** | `Player` owns `Inventory`; `GameContext` holds all shared services |

---

## Project Structure

```
src/
├── main/java/com/echoes/
│   ├── Main.java
│   ├── application/GameApplication.java
│   ├── controller/GameController.java, InputHandler.java
│   ├── model/
│   │   ├── character/Character.java, Player.java, PlayerClass.java
│   │   │   └── enemy/Enemy.java, Goblin.java, Skeleton.java, Troll.java,
│   │   │             DragonWhelp.java
│   │   │       └── boss/BossEnemy.java, StoneGolem.java, Lich.java, AbyssDragon.java
│   │   ├── combat/AttackStrategy.java, CombatEngine.java, AttackResult.java
│   │   ├── dungeon/Dungeon.java, Floor.java, Room.java, RoomType.java
│   │   ├── inventory/Inventory.java
│   │   └── item/consumable/HealthPotion.java, EnergyElixir.java,
│   │                       PowerCrystal.java, StoneAmulet.java
│   ├── pattern/
│   │   ├── builder/DungeonBuilder.java, FloorBuilder.java
│   │   ├── command/Command.java, AttackCommand.java, CommandHistory.java
│   │   ├── factory/EnemyFactory.java, StandardEnemyFactory.java
│   │   ├── observer/GameEventPublisher.java, ScoreTracker.java
│   │   └── state/GameState.java, ExploringState.java, CombatState.java
│   ├── util/GameConstants.java, DiceRoller.java, TextColour.java
│   └── view/GameRenderer.java, ConsoleRenderer.java
└── test/java/com/echoes/
    ├── model/character/PlayerTest.java
    ├── model/combat/CombatEngineTest.java
    ├── model/inventory/InventoryTest.java
    ├── pattern/builder/DungeonBuilderTest.java
    ├── pattern/command/CommandHistoryTest.java
    ├── pattern/factory/EnemyFactoryTest.java
    └── pattern/observer/GameEventPublisherTest.java
```

---

## How to Build

```bash
mvn clean compile
```

## How to Run

```bash
mvn package -DskipTests
java -jar target/echoes-of-the-abyss.jar
```

## How to Test

```bash
mvn test
```

Expected output:
```
Tests run: 71, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

---

## Game Controls

| Command | Action |
|---|---|
| `m` | Move to next room |
| `a` | Attack enemy |
| `i` | View / use inventory |
| `f` | Flee from combat |
| `s` | View player stats |
| `h` | Show help menu |

---

## Author

Gagandeep Kaur
