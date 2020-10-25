package com.walfen.antiland.entities.properties.attack.meleeAttacks;

import android.graphics.Bitmap;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.active.Active;
import com.walfen.antiland.entities.properties.attack.Attack;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;

public class SoulBash extends MeleeAttack {

    public SoulBash(Handler handler, int baseDamage, Active carrier) {
        super(handler, baseDamage, Type.MAGICAL_DARK, carrier);
        carrierAnimations.add(0, new Animation(0.6f, Assets.trappedSpiritAttackLeft));
        carrierAnimations.add(1, new Animation(0.6f, Assets.trappedSpiritAttackRight));
        carrierAnimations.add(2, new Animation(0.6f, new Bitmap[]{Assets.trappedSpiritMovementUp,
                Assets.trappedSpiritAttackUp, Assets.trappedSpiritMovementUp}));
        carrierAnimations.add(3, new Animation(0.6f, new Bitmap[]{Assets.trappedSpiritMovementDown,
                Assets.trappedSpiritAttackDown, Assets.trappedSpiritMovementDown}));
    }

}
