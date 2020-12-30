package com.walfen.antiland.entities.properties.effect.special;

import android.graphics.Bitmap;

import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.entities.properties.effect.StatusEffect;
import com.walfen.antiland.gfx.Assets;

public class BraveHeart extends StatusEffect {

    private int defenceDelta, physAttackDelta;

    public BraveHeart(Player player, long activeDuration, int level) {
        super(player, activeDuration, 11);
        defenceDelta = player.getDefence()*level/10;
        physAttackDelta = player.getPhysicalDamage()*level/5;
    }

    public BraveHeart(){
        super(11);
    }

    @Override
    public void onEffectActive() {
        carrier.changePhysicalDamage(physAttackDelta);
        carrier.changeDefence(defenceDelta);
    }

    @Override
    public void onEffectRemoved() {
        int hp = carrier.getMaxHp()/10;
        carrier.changePhysicalDamage(-physAttackDelta);
        carrier.changeDefence(-defenceDelta);
        carrier.changeHealth(hp);
    }

    @Override
    public Bitmap getTexture() {
        return Assets.strengthSkills[5];
    }
}
