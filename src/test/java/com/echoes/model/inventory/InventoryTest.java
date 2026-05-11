package com.echoes.model.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.echoes.model.item.consumable.HealthPotion;
import com.echoes.util.GameConstants;

@DisplayName("Inventory Tests")
class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @Test
    @DisplayName("New inventory is empty")
    void newInventory_isEmpty() {
        assertTrue(inventory.isEmpty());
        assertEquals(0, inventory.size());
    }

    @Test
    @DisplayName("addItem increases size by one")
    void addItem_increasesSizeByOne() {
        boolean added = inventory.addItem(new HealthPotion());
        assertTrue(added);
        assertEquals(1, inventory.size());
    }

    @Test
    @DisplayName("addItem rejects null")
    void addItem_rejectsNull() {
        assertFalse(inventory.addItem(null));
        assertEquals(0, inventory.size());
    }

    @Test
    @DisplayName("addItem rejects items when inventory is full")
    void addItem_rejectsWhenFull() {
        for (int i = 0; i < GameConstants.MAX_INVENTORY_SIZE; i++) {
            inventory.addItem(new HealthPotion());
        }
        assertTrue(inventory.isFull());
        assertFalse(inventory.addItem(new HealthPotion()));
        assertEquals(GameConstants.MAX_INVENTORY_SIZE, inventory.size());
    }

    @Test
    @DisplayName("removeItem decreases size by one")
    void removeItem_decreasesSizeByOne() {
        HealthPotion potion = new HealthPotion();
        inventory.addItem(potion);
        assertTrue(inventory.removeItem(potion));
        assertTrue(inventory.isEmpty());
    }

    @Test
    @DisplayName("removeItem returns false for absent item")
    void removeItem_returnsFalseForAbsent() {
        assertFalse(inventory.removeItem(new HealthPotion()));
    }

    @Test
    @DisplayName("getItem returns correct item at valid index")
    void getItem_returnsCorrectItem() {
        HealthPotion potion = new HealthPotion();
        inventory.addItem(potion);
        assertTrue(inventory.getItem(0).isPresent());
        assertSame(potion, inventory.getItem(0).get());
    }

    @Test
    @DisplayName("getItem returns empty for invalid index")
    void getItem_returnsEmptyForInvalidIndex() {
        assertTrue(inventory.getItem(0).isEmpty());
        assertTrue(inventory.getItem(-1).isEmpty());
    }

    @Test
    @DisplayName("removeExhaustedItems clears used items")
    void removeExhaustedItems_clearsUsedItems() {
        HealthPotion potion = new HealthPotion();
        inventory.addItem(potion);
        potion.use();
        inventory.removeExhaustedItems();
        assertTrue(inventory.isEmpty());
    }
}