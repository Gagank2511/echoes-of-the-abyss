# Echoes of the Abyss

> A turn-based dungeon exploration game written in Java 23, built to
> demonstrate object-oriented programming principles and software
> design patterns as part of a university coursework assignment.

---

## What Is This Project?

Echoes of the Abyss is a single-player console dungeon game where the
player creates a hero and descends through five themed floors, each
containing enemies, treasure, and a powerful floor boss. Every
mechanic in the game is driven by a recognised design pattern,
making the codebase a practical demonstration of how patterns solve
real software engineering problems.

The project was built incrementally using Git feature branches,
with each branch representing a distinct development stage.

---

## System Requirements

| Requirement | Minimum Version |
|---|---|
| Java JDK | 23 |
| Apache Maven | 3.9+ |
| Operating System | Windows 10/11, macOS, Linux |
| Terminal | Any terminal supporting ANSI colour codes |

---

## How to Build

```bash
mvn clean compile
```

Expected output:
```
Compiling 68 source files
BUILD SUCCESS
```

---

## How to Run

**On Windows (PowerShell):**
```powershell
mvn package -DskipTests
& "C:\Program Files\Java\jdk-23\bin\java.exe" -jar target/echoes-of-the-abyss.jar
```

**On macOS / Linux:**
```bash
mvn package -DskipTests
java -jar target/echoes-of-the-abyss.jar
```

> **Note for Windows users:** If you see `?` symbols instead of
> coloured icons, run this first to enable UTF-8 in your terminal:
> ```powershell
> [Console]::OutputEncoding = [System.Text.Encoding]::UTF8
> ```

---

## How to Run Tests

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

| Key | Action | Available In |
|---|---|---|
| `m` | Move to the next room | Exploration |
| `s` | View player statistics | Exploration + Combat |
| `i` | Open inventory | Exploration + Combat |
| `a` | Attack the enemy | Combat |
| `f` | Attempt to flee | Combat |
| `h` | Show help menu | Exploration |

---

## Playable Classes

| Class | HP | Attack | Defence | Combat Style |
|---|---|---|---|---|
| Warrior | 110 | 9 | 5 | Reliable melee fighter |
| Mage | 75 | 13 | 1 | Magic that ignores armour |
| Ranger | 90 | 10 | 3 | Agile archer with high damage |

---

## Enemies

| Enemy | Type | Found On Floor |
|---|---|---|
| Goblin | Standard | 1 |
| Skeleton | Standard | 1–2 |
| Troll | Standard — regenerates each turn | 2–3 |
| Dragon Whelp | Standard — magic attacks | 3–4 |
| Stone Golem | Floor Boss — Seismic Slam ability | Floor 1 |
| Lich | Floor Boss — Soul Drain life-steal | Floor 3 |
| Abyss Dragon | Final Boss — Abyss Flame ability | Floor 5 |

Bosses activate their special ability when their health drops below
40%, making the final phases of each boss fight significantly harder.

---

## Items

| Item | Effect |
|---|---|
| Health Potion | Restores 40 HP |
| Energy Elixir | +4 attack power for the current combat |
| Power Crystal | +3 permanent attack increase |
| Stone Amulet | +2 permanent defence increase |

The inventory holds a maximum of 8 items.

---

## Design Patterns

### 1. Factory Method
**Files:** `EnemyFactory`, `StandardEnemyFactory`, `EliteEnemyFactory`

Decouples floor construction from concrete enemy classes. The
`FloorBuilder` calls `EnemyFactory.create(type, level)` without
knowing which subclass is returned. Adding a new enemy requires
only a new class and a factory branch — no existing logic changes.

### 2. Strategy
**Files:** `AttackStrategy`, `MeleeAttackStrategy`, `RangedAttackStrategy`, `MagicAttackStrategy`

Each attack algorithm is encapsulated in its own class. The
`Character` base class holds a reference to a strategy and
delegates to it, meaning combat behaviour can be changed without
modifying the character hierarchy.

### 3. Observer
**Files:** `GameEventPublisher`, `GameEventListener`, `ScoreTracker`, `AchievementTracker`

An event bus completely decouples producers from consumers. Combat
states fire events; score and achievement trackers react. Adding a
new listener requires zero changes to existing classes.

### 4. State
**Files:** `GameState`, `ExploringState`, `CombatState`, `ItemSelectionState`, `GameOverState`, `VictoryState`

Each game phase is a self-contained class with its own input
handling. The `GameContext` delegates all processing to the active
state. Invalid transitions are prevented by design.

### 5. Command
**Files:** `Command`, `AttackCommand`, `UseItemCommand`, `FleeCommand`, `MoveCommand`, `CommandHistory`

Every player action is an object. The `CommandHistory` records a
full action log without knowing what any command does. This
decouples the controller from action execution entirely.

### 6. Builder
**Files:** `DungeonBuilder`, `FloorBuilder`

Complex dungeon construction is exposed as a fluent API:
```java
new DungeonBuilder().withName("The Abyss").withFloorCount(5).build();
```
All room layout and enemy placement logic is hidden inside the builders.

### 7. Template Method
**Files:** `AbstractConsumable`, `Enemy`

The item-use algorithm is fixed in `AbstractConsumable.use()`;
subclasses implement only `calculateEffect()`. The enemy turn
algorithm is fixed in `Enemy.takeTurn()`; subclasses override
only the hooks they need. This eliminates duplicated logic across
all item and enemy types.

---

## Object-Oriented Principles

| Principle | Where Applied |
|---|---|
| **Encapsulation** | All fields private; state changed only via controlled methods |
| **Abstraction** | `AttackStrategy`, `GameState`, `GameRenderer`, `EnemyFactory`, `Consumable` |
| **Inheritance** | `Player extends Character`, `Enemy extends Character`, `BossEnemy extends Enemy` |
| **Polymorphism** | `CombatEngine` operates on `Character`; all states implement `GameState` |
| **Composition** | `Player` owns `Inventory`; `GameContext` holds all shared services |

---

## Project Structure

```
src/
├── main/java/com/echoes/
│   ├── Main.java
│   ├── application/        GameApplication.java (composition root)
│   ├── controller/         GameController.java, InputHandler.java
│   ├── model/
│   │   ├── character/      Character.java, Player.java, PlayerClass.java
│   │   │   └── enemy/      Enemy.java, Goblin.java, Skeleton.java,
│   │   │       │           Troll.java, DragonWhelp.java
│   │   │       └── boss/   BossEnemy.java, StoneGolem.java,
│   │   │                   Lich.java, AbyssDragon.java
│   │   ├── combat/         AttackStrategy.java, CombatEngine.java,
│   │   │                   AttackResult.java, CombatResult.java
│   │   ├── dungeon/        Dungeon.java, Floor.java, Room.java, RoomType.java
│   │   ├── inventory/      Inventory.java
│   │   └── item/
│   │       └── consumable/ AbstractConsumable.java, HealthPotion.java,
│   │                       EnergyElixir.java, PowerCrystal.java, StoneAmulet.java
│   ├── pattern/
│   │   ├── builder/        DungeonBuilder.java, FloorBuilder.java
│   │   ├── command/        Command.java, CommandHistory.java,
│   │   │                   AttackCommand.java, UseItemCommand.java,
│   │   │                   FleeCommand.java, MoveCommand.java
│   │   ├── factory/        EnemyFactory.java, StandardEnemyFactory.java,
│   │   │                   EliteEnemyFactory.java
│   │   ├── observer/       GameEventPublisher.java, GameEventListener.java,
│   │   │   └── listeners/  ScoreTracker.java, AchievementTracker.java
│   │   └── state/          GameState.java, GameContext.java,
│   │                       ExploringState.java, CombatState.java,
│   │                       ItemSelectionState.java, GameOverState.java,
│   │                       VictoryState.java
│   ├── util/               GameConstants.java, DiceRoller.java, TextColour.java
│   └── view/               GameRenderer.java, ConsoleRenderer.java
└── test/java/com/echoes/
    ├── model/character/    PlayerTest.java (12 tests)
    ├── model/combat/       CombatEngineTest.java (8 tests)
    ├── model/inventory/    InventoryTest.java (9 tests)
    ├── pattern/builder/    DungeonBuilderTest.java (8 tests)
    ├── pattern/command/    CommandHistoryTest.java (7 tests)
    ├── pattern/factory/    EnemyFactoryTest.java (19 tests)
    └── pattern/observer/   GameEventPublisherTest.java (8 tests)
```

---

## Git Development Process

This project was developed using a feature-branch workflow across
15 branches, each representing a distinct development stage:

```
feature/utility-classes       Utility and constant classes
feature/combat-model          Attack strategies and combat engine
feature/character-model       Character base class and Player
feature/enemy-model           All enemy and boss types
feature/item-system           Item hierarchy and inventory
feature/dungeon-model         Dungeon, floor and room structure
feature/factory-pattern       Enemy factory implementation
feature/builder-pattern       Dungeon builder implementation
feature/observer-pattern      Event bus and listeners
feature/command-pattern       Command objects and history
feature/state-pattern         Game state machine
feature/view-layer            Console renderer
feature/controller            Game controller and entry point
feature/tests                 All 71 unit tests
feature/javadoc-and-refactor  JavaDoc and code improvements
```

Each branch was merged into `main` using `--no-ff` merge commits
to preserve the full development history in the Git log.

Total commits: 92

---

## Known Limitations

- The game runs in the console only — no graphical interface
- Special characters require UTF-8 terminal encoding on Windows
- Save/load functionality is not implemented
- Multiplayer is not supported

---

## Possible Future Improvements

- Add a JavaFX graphical user interface
- Implement save and load functionality using serialisation
- Add a merchant room where gold can be spent on items
- Introduce a difficulty setting affecting enemy stat scaling
- Add more dungeon floors and enemy types

---

## Author

Gagandeep Kaur
