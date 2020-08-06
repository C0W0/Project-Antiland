package com.walfen.antiland.entities.properties.attack.meleeAttacks;


import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.creatures.active.Active;
import com.walfen.antiland.entities.properties.attack.Attacks;

public abstract class MeleeAttacks extends Attacks {
    protected Entity target;

    public MeleeAttacks(Handler handler, int baseDamage, int type, Active carrier) {
        super(handler, baseDamage, type, carrier);
    }

    @Override
    public void update() {
        super.update();
        if(carrier != null)
             target = carrier.getTarget();
    }

    @Override
    public void checkAttackCollision() {
        if(target != null)
            target.receiveDamage(baseDamage, type);
    }
}
