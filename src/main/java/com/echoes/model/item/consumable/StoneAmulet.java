package com.echoes.model.item.consumable;

import com.echoes.util.GameConstants;

public final class StoneAmulet extends AbstractConsumable {

    public StoneAmulet() {
        super(
            "Stone Amulet",
            "Permanently increases defence by +"
                + GameConstants.STONE_AMULET_BONUS + ".",
            1
        );
    }

    @Override
    protected ItemEffect calculateEffect() {
        return new ItemEffect(
            0, 0, 0,
            GameConstants.STONE_AMULET_BONUS,
            "The amulet pulses and melds with your skin, hardening it. "
                + "(+" + GameConstants.STONE_AMULET_BONUS + " DEF permanently)"
        );
    }
}