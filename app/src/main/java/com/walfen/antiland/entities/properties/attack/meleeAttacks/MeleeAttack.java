package com.walfen.antiland.entities.properties.attack.meleeAttacks;


import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.creatures.active.Active;
import com.walfen.antiland.entities.properties.attack.Attack;

public abstract class MeleeAttack extends Attack {
    protected Entity target;

    public MeleeAttack(Handler handler, int baseDamage, int type, Active carrier) {
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
