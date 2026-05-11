package com.echoes.model.item.consumable;

import com.echoes.util.GameConstants;

public final class PowerCrystal extends AbstractConsumable {

    public PowerCrystal() {
        super(
            "Power Crystal",
            "Permanently increases attack power by +"
                + GameConstants.POWER_CRYSTAL_BONUS + ".",
            1
        );
    }

    @Override
    protected ItemEffect calculateEffect() {
        return new ItemEffect(
            0, 0,
            GameConstants.POWER_CRYSTAL_BONUS,
            0,
            "The crystal shatters and infuses your body with raw power! "
                + "(+" + GameConstants.POWER_CRYSTAL_BONUS + " ATK permanently)"
        );
    }
}