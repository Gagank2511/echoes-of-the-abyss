package com.echoes.pattern.builder;

import java.util.ArrayList;
import java.util.List;

import com.echoes.model.dungeon.Dungeon;
import com.echoes.model.dungeon.Floor;
import com.echoes.util.GameConstants;

/**
 * Fluent builder for constructing the complete dungeon — the <em>Builder</em> pattern.
 *
 * <p>Exposes a readable, declarative API that hides all the complexity of
 * floor layout, enemy placement, and item seeding. Each floor is delegated
 * to a {@link FloorBuilder}, keeping this class free of room-level concerns.
 * Adding a difficulty setting in the future would only affect this builder
 * and the floor builder — no model or controller code would need to change.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     Dungeon dungeon = new DungeonBuilder()
 *             .withName("The Abyss")
 *             .withFloorCount(5)
 *             .build();
 * </pre>
 *
 * @author Your Name
 * @version 1.0
 */
public final class DungeonBuilder {

    private String name = "The Abyss";
    private int floorCount = GameConstants.FLOOR_COUNT;

    /**
     * Sets the dungeon's display name.
     *
     * @param name the dungeon name to use
     * @return this builder for method chaining
     */
    public DungeonBuilder withName(final String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the number of floors to generate.
     *
     * @param floorCount total floors — must be at least 1
     * @return this builder for method chaining
     * @throws IllegalArgumentException if floorCount is less than 1
     */
    public DungeonBuilder withFloorCount(final int floorCount) {
        if (floorCount < 1) {
            throw new IllegalArgumentException(
                    "Floor count must be >= 1, got: " + floorCount);
        }
        this.floorCount = floorCount;
        return this;
    }

    /**
     * Builds and returns the fully constructed dungeon.
     * Each floor is created by a dedicated {@link FloorBuilder}.
     *
     * @return the constructed dungeon
     */
    public Dungeon build() {
        List<Floor> floors = new ArrayList<>();
        for (int i = 1; i <= floorCount; i++) {
            floors.add(new FloorBuilder(i).build());
        }
        return new Dungeon(name, floors);
    }
}
