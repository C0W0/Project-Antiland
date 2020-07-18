package com.walfen.antiland.entities.properties.attack.meleeAttacks;


import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.active.Active;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;

public class SlimeBash extends MeleeAttacks {
    public SlimeBash(Handler handler, Active carrier) {
        super(handler, 1, 1, carrier);
        carrierAnimations.add(0, new Animation(0.3f, Assets.slimeAttackLeft));
        carrierAnimations.add(1, new Animation(0.3f, Assets.slimeAttackRight));
    }
}
