package com.echoes.model.item.consumable;

import com.echoes.util.GameConstants;

/**
 * A consumable item that temporarily boosts attack power.
 *
 * <p>The Energy Elixir provides a combat-time attack bonus when consumed.
 * It is a single-use item that is exhausted after use.</p>
 */
public final class EnergyElixir extends AbstractConsumable {

    /**
     * Constructs a new Energy Elixir.
     */
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