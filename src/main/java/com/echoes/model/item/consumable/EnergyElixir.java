package com.echoes.model.item.consumable;

import com.echoes.util.GameConstants;

public final class EnergyElixir extends AbstractConsumable {

    public EnergyElixir() {
        super(
            "Energy Elixir",
            "Boosts attack by +" + GameConstants.ENERGY_ELIXIR_BOOST
                + " until end of combat.",
            1
        );
    }

    @Override
    protected ItemEffect calculateEffect() {
        return new ItemEffect(
            0,
            GameConstants.ENERGY_ELIXIR_BOOST,
            0, 0,
            "You drink the crackling blue elixir. Your strikes feel sharper! "
                + "(+" + GameConstants.ENERGY_ELIXIR_BOOST + " ATK this combat)"
        );
    }
}