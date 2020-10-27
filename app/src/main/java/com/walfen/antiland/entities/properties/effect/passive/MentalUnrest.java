package com.walfen.antiland.entities.properties.effect.passive;

import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.effect.StatusEffect;

public class MentalUnrest extends StatusEffect {

    public MentalUnrest(Creature carrier) {
        super(carrier, 60000, 21);
    }

    public MentalUnrest() {
        super(21);
    }

    @Override
    public void onEffectActive() {

    }

    @Override
    public void onEffectRemoved() {

    }
}
