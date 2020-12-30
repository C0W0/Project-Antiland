package com.walfen.antiland.entities.properties.effect.special;

import android.graphics.Bitmap;

import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.entities.properties.effect.StatusEffect;
import com.walfen.antiland.gfx.Assets;

public class SpeedBoost extends StatusEffect {

    private int boostFactor;
    private int deltaSpeed;

    public SpeedBoost(Player player, long activeDuration, int boostFactor) {
        super(player, activeDuration, 3);
        this.boostFactor = boostFactor;
    }

    public SpeedBoost() {
        super(3);
    }

    @Override
    public void onEffectActive() {
        deltaSpeed = (int) (carrier.getSpeed()*(boostFactor/100.f));
        carrier.changeSpeed(deltaSpeed);
    }

    @Override
    public void onEffectRemoved() {
        carrier.changeSpeed(-deltaSpeed);
    }

    @Override
    public Bitmap getTexture() {
        return Assets.agilityR;
    }
}
