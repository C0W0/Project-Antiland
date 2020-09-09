package com.walfen.antiland.entities.properties.effect.special;

import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.effect.StatusEffect;

public class Stung extends StatusEffect {

    public Stung(Creature carrier, long activeDuration) {
        super(carrier, activeDuration, 1);
    }

    public Stung(){
        super(1);
    }

    @Override
    public void onEffectActive() {
        carrier.disable();
    }

    @Override
    public void onEffectRemoved() {
        carrier.enable();
    }
}
