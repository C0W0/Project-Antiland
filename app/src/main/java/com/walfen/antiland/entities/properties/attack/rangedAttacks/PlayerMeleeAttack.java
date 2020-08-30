package com.walfen.antiland.entities.properties.attack.rangedAttacks;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;

import java.util.function.IntSupplier;

public class PlayerMeleeAttack extends PlayerDefaultAttack {

    //TODO: overriding player animation
    public PlayerMeleeAttack(Handler handler, int type, IntSupplier damageSupplier) {
        super(handler, type, 1024, 0, damageSupplier); // ranged cannot be reached, set to 1024 to be safe
    }

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
    public void checkAttackCollision() {
        for(RangedAttackCollision r: collisionQueue) {
            for (Entity e : handler.getWorld().getEntityManager().getEntities()){
                if (r.intersect(e.getCollisionBounds(0, 0))) {
                    if (e.equals(handler.getPlayer()))
                        continue;
                    e.receiveDamage(baseDamage, type);
                    handler.getPlayer().getTracker().addTracking(e);
                }
            }
            r.hit();
        }
    }

    @Override
    public void generateAttack(float dX, float dY) {
        int fixX = (int)(x+11+99*dX);
        int fixY = (int)(y+28+96*dY);
        System.out.println(dX+" "+dY);
        RangedAttackCollision rc = new RangedAttackCollision(fixX, fixY, fixX+128, fixY+128, dX, dY, (int)travelSpeed);
        collisionQueue.add(rc);
        handler.getPlayer().resetAttack();
    }
}
