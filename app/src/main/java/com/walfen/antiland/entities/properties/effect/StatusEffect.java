package com.walfen.antiland.entities.properties.effect;

import android.graphics.Bitmap;

import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.gfx.Assets;

public abstract class StatusEffect {

    protected Creature carrier;
    protected long activeTime, activeDuration;

    public StatusEffect(Creature carrier, long activeDuration){
        this.carrier = carrier;
        this.activeDuration = activeDuration;
        activeTime = System.currentTimeMillis();
    }

    public abstract void onEffectActive();

    public abstract void onEffectRemoved();

    public boolean isValid(){
        return System.currentTimeMillis() - activeTime < activeDuration;
    }

    public String getRemainingDuration(){
        int second = (int)((activeDuration+activeTime-System.currentTimeMillis()+500)/1000);
        int minute = second/60;
        second %= 60;
        if(second < 10)
            return minute+":0"+second;
        else
            return minute+":"+second;
    }

    public Bitmap getTexture(){
        return Assets.grass; //TODO: add texture for status effects
    }

}
