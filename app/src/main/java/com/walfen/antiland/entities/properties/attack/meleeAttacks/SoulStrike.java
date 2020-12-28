package com.walfen.antiland.entities.properties.attack.meleeAttacks;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.active.Active;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;

public class SoulStrike extends MeleeAttack {
    public SoulStrike(Handler handler, int baseDamage, Active carrier) {
        super(handler, baseDamage, Type.MAGICAL_DARK, carrier);
        carrierAnimations.add(0, new Animation(0.6f, Assets.lostGhostAttack));
    }
}
