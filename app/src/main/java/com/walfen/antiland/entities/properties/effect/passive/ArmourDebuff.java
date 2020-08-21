package com.walfen.antiland.entities.properties.effect.passive;

import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.effect.StatusEffect;

public class ArmourDebuff extends StatusEffect {

    private int defenceDelta;
    private float percentDeduction;

    public ArmourDebuff(Creature carrier, long activeDuration, float percentDeduction) {
        super(carrier, activeDuration);
        this.percentDeduction = percentDeduction;
    }

    @Override
    public void onEffectActive() {
        defenceDelta = (int)(percentDeduction*carrier.getDefence());
        carrier.changeDefence(-defenceDelta);
    }

    @Override
    public void onEffectRemoved() {
        carrier.changeDefence(defenceDelta);
    }
}
