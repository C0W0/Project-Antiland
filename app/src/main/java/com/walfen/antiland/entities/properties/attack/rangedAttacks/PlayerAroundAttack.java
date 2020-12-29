package com.walfen.antiland.entities.properties.attack.rangedAttacks;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.untils.AnimationSupplier;

import java.util.function.IntSupplier;

public class PlayerAroundAttack extends RangedAttack {

    protected IntSupplier damageSupplier, rangeSupplier;
    protected AnimationSupplier animSupplier;
    protected long lastAttack;
    protected int animXSize, animYSize;

    public PlayerAroundAttack(Handler handler, IntSupplier damageSupplier, IntSupplier rangeSupplier, AnimationSupplier animSupplier) {
        super(handler, 1, Type.PHYSICAL, 0, 0);
        this.damageSupplier = damageSupplier;
        this.rangeSupplier = rangeSupplier;
        this.animSupplier = animSupplier;
        animXSize = 2*rangeSupplier.getAsInt()+128;
        animYSize = 2*rangeSupplier.getAsInt()+128;
    }

    @Override
    public void update() {
        super.update();
        if(animations.size() > collisionQueue.size())
            for(int i = collisionQueue.size(); i < animations.size(); i++)
                animations.get(i).update();
        if(animations.size() > 0){
            Animation a = animations.get(0);
            if(System.currentTimeMillis() > lastAttack+a.getCycleDuration())
                animations.remove(0);
        }
        baseDamage = damageSupplier.getAsInt();
        range = rangeSupplier.getAsInt();
    }

    @Override
    public void checkAttackCollision() {
        if(collisionQueue.size() == 0)
            return;
        for(Entity e: handler.getWorld().getEntityManager().getEntities()) {
            if (e.getCollisionBounds(0, 0).intersect(collisionQueue.get(0).getBound()) &&
                    !e.equals(handler.getPlayer())) {
                e.receiveDamage(baseDamage, type);
                if(e.getHealth() > 0)
                    handler.getPlayer().getTracker().addTracking(e);
            }
        }
        collisionQueue.remove(0);
    }

    @Override
    public void generateAttack(float dX, float dY) {
        int range = rangeSupplier.getAsInt();
        RangedAttackCollision rc = new RangedAttackCollision((int)(x-range), (int)(y-range), (int)(x+128+range), (int)(y+128+range), 0, 0, 0);
        collisionQueue.add(rc);
        Animation a = animSupplier.getAnimation();
        a.scaleForced(animXSize, animYSize);
        animations.add(a);
        lastAttack = System.currentTimeMillis();
    }

    @Override
    public void draw(Canvas canvas) {
        for(int i = 0; i < animations.size(); i++){
            Animation a = animations.get(i);
            Rect r = new Rect((int)(x-range), (int)(y-range), (int)(x+128+range), (int)(y+128+range));
            int left = (int)(r.left - handler.getGameCamera().getxOffset());
            int top = (int)(r.top - handler.getGameCamera().getyOffset());
            a.draw(canvas, new Rect(left, top, left+r.width(), top+r.height()));
        }
    }
}
