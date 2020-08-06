package com.walfen.antiland.entities.properties.attack.rangedAttacks;


import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;

import java.util.function.IntSupplier;

public class PlayerDefaultAttack extends RangedAttacks {

    protected IntSupplier damageSupplier;

    public PlayerDefaultAttack(Handler handler, IntSupplier damageSupplier) {
        super(handler, 1, Type.PHYSICAL, 256, 10);
        this.damageSupplier = damageSupplier;
    }

    @Override
    public void checkAttackCollision() {
        for(RangedAttackCollision r: collisionQueue){
            if(r.getDistance() >= range){
                r.hit();
                continue;
            }
            for(Entity e : handler.getWorld().getEntityManager().getEntities()){
                if(r.intersect(e.getCollisionBounds(0,0))) {
                    if ((carrier == null && e.equals(handler.getPlayer())) || e.equals(carrier))
                        continue;
                    if (carrier != null) {
                        if (e.getFaction() != carrier.getFaction()) {
                            e.receiveDamage(baseDamage, type);
                            r.hit();
                        }
                    }
                    if (carrier == null) {
                        handler.getPlayer().getTracker().addTracking(e);
                        e.receiveDamage(baseDamage, type);
                        r.hit();
                    }
                }
            }
        }
    }

    @Override
    public void generateAttack(float dX, float dY) {
        RangedAttackCollision rc = new RangedAttackCollision((int)x, (int)y, (int)(x) + 128, (int)(y) + 128, dX, dY, (int)travelSpeed);
        collisionQueue.add(rc);
        float angle = (float) Math.toDegrees(Math.atan(dY/dX));
        Matrix matrix = new Matrix();
        matrix.setRotate(dX>=0?angle:180+angle, 0, 0);
        matrix.postScale(1f, 1f);
        Animation a = new Animation(0.15f, new Bitmap[]{Assets.player_Attack});
        a.setMatrix(matrix);
        animations.add(a);
    }

    @Override
    public void update() {
        super.update();
        baseDamage = damageSupplier.getAsInt();
    }
}
