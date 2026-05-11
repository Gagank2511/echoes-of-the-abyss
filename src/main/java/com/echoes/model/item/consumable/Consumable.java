package com.echoes.model.item.consumable;

import com.echoes.model.item.Item;

public interface Consumable extends Item {
    ItemEffect use();
    boolean isExhausted();
    int getQuantity();
}