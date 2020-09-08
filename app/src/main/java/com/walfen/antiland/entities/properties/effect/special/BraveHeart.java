package com.walfen.antiland.entities.properties.effect.special;

import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.entities.properties.effect.StatusEffect;

public class BraveHeart extends StatusEffect {

    private int defenceDelta, physAttackDelta;

    public BraveHeart(Player player, long activeDuration, int level) {
        super(player, activeDuration);
        defenceDelta = player.getDefence()*level/10;
        physAttackDelta = player.getDefence()*level/5;
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
}
