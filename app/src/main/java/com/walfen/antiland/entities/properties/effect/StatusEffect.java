package com.walfen.antiland.entities.properties.effect;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.effect.passive.ArmourDebuff;
import com.walfen.antiland.entities.properties.effect.special.BraveHeart;
import com.walfen.antiland.entities.properties.effect.special.Stung;
import com.walfen.antiland.gfx.Assets;

public abstract class StatusEffect implements Cloneable{

    protected Creature carrier;
    protected long activeTime, activeDuration;
    protected int id;

    /* Status Effect id:
    Stung: 1
    ArmourDebuff: 2
    BraveHeart: 11
    (UPDATE HERE)
     */
    public static StatusEffect[] statusEffects = new StatusEffect[256];

    public static void initEffects(){
        new Stung();
        new ArmourDebuff();
        new BraveHeart();
    }

    public StatusEffect(Creature carrier, long activeDuration, int id){
        this.carrier = carrier;
        this.activeDuration = activeDuration;
        this.id = id;
        activeTime = System.currentTimeMillis();
    }

    public StatusEffect(int id){
        this.id = id;
        statusEffects[id] = this;
    }

    public abstract void onEffectActive();

    public abstract void onEffectRemoved();

    public boolean isValid(){
        return System.currentTimeMillis() - activeTime < activeDuration;
    }

    public String getRemainingDuration(){
        int second = (int)((getMSRemainingDuration()+500)/1000);
        int minute = second/60;
        second %= 60;
        if(second < 10)
            return minute+":0"+second;
        else
            return minute+":"+second;
    }

    public long getMSRemainingDuration(){
        return activeDuration+activeTime-System.currentTimeMillis();
    }

    @NonNull
    @Override
    public StatusEffect clone(){
        StatusEffect result = null;
        try {
            result = (StatusEffect)super.clone();
        }catch (Exception e){
            e.printStackTrace();
        }
        assert result != null;
        return result;
    }

    public void initialize(Creature carrier, long activeDuration){
        this.carrier = carrier;
        this.activeDuration = activeDuration;
        activeTime = System.currentTimeMillis();
    }

    public Bitmap getTexture(){
        return Assets.grass; //TODO: add texture for status effects
    }

    public int getId() {
        return id;
    }
}
