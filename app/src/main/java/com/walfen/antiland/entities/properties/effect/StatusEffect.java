package com.walfen.antiland.entities.properties.effect;

import com.walfen.antiland.entities.creatures.Creature;

public abstract class StatusEffect {

    protected Creature carrier;
    protected long activeTime, activeDuration;

    public StatusEffect(Creature carrier, long activeDuration){
        this.carrier = carrier;
        this.activeDuration = activeDuration;
        activeTime = System.currentTimeMillis();
        onEffectActive();
    }

    public abstract void onEffectActive();

    public abstract void onEffectRemoved();

    public boolean isValid(){
        return System.currentTimeMillis() - activeTime < activeDuration;
    }

}
