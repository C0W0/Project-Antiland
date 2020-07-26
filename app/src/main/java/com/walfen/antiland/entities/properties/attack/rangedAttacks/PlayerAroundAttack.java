package com.walfen.antiland.entities.properties.attack.rangedAttacks;

import android.graphics.Rect;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.creatures.active.Active;

import java.util.function.IntSupplier;

public class PlayerAroundAttack extends RangedAttacks {

    private IntSupplier damageSupplier;

    public PlayerAroundAttack(Handler handler, IntSupplier damageSupplier) {
        super(handler, 1, 1, 0, 0);
        this.damageSupplier = damageSupplier;
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
    public void checkAttackCollision() {
        if(collisionQueue.size() == 0)
            return;
        for(Entity e: handler.getWorld().getEntityManager().getEntities()) {
            if (e.getCollisionBounds(0, 0).intersect(collisionQueue.get(0).getBound()) &&
                    !e.equals(handler.getPlayer())) {
                handler.getPlayer().getTracker().addTracking(e);
                e.receiveDamage(baseDamage);
            }
        }

        collisionQueue.remove(0);
    }

    @Override
    public void generateAttack(float dX, float dY) {
        RangedAttackCollision rc = new RangedAttackCollision((int)(x-128), (int)(y-128), (int)(x+256), (int)(y+256), 0, 0, 0);
        collisionQueue.add(rc);
        //TODO: add animation here
    }
}
