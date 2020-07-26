package com.walfen.antiland.entities.properties.attack.rangedAttacks;

import com.walfen.antiland.Handler;

import java.util.function.IntSupplier;

public class PlayerAbilityAttack extends PlayerDefaultAttack {

    public PlayerAbilityAttack(Handler handler, IntSupplier damageSupplier) {
        super(handler, damageSupplier);
    }

    //TODO: add animation so can just call super
    @Override
    public void update() {
        x = handler.getPlayer().getX();
        y = handler.getPlayer().getY();
        for(int i = 0; i < collisionQueue.size(); i++) {
            if(collisionQueue.get(i).isHit()){
                collisionQueue.remove(i);
                i --;
                continue;
            }
            collisionQueue.get(i).update();
        }
        baseDamage = damageSupplier.getAsInt();
        checkAttackCollision();
    }

    @Override
    public void generateAttack(float dX, float dY) {
        RangedAttackCollision rc = new RangedAttackCollision((int)x, (int)y, (int)(x) + 128, (int)(y) + 128, dX, dY, (int)travelSpeed);
        collisionQueue.add(rc);
        handler.getPlayer().resetAttack();
    }
}
