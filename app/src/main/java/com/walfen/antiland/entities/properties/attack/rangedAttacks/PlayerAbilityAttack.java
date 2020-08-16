package com.walfen.antiland.entities.properties.attack.rangedAttacks;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;

import java.util.function.IntSupplier;

public class PlayerAbilityAttack extends PlayerDefaultAttack {

    public PlayerAbilityAttack(Handler handler, IntSupplier damageSupplier) {
        super(handler, damageSupplier);
    }

    @Override
    public void generateAttack(float dX, float dY) {
        RangedAttackCollision rc = new RangedAttackCollision((int)x, (int)y, (int)(x) + 128, (int)(y) + 128, dX, dY, (int)travelSpeed);
        collisionQueue.add(rc);
        float angle = (float) Math.toDegrees(Math.atan(dY/dX));
        Matrix matrix = new Matrix();
        matrix.setRotate(dX>=0?angle:180+angle, 0, 0);
        matrix.postScale(1f, 1f);
        Animation a = new Animation(2, Assets.player_SharpWind);
        a.setMatrix(matrix);
        animations.add(a);
        handler.getPlayer().resetAttack();
    }
}
