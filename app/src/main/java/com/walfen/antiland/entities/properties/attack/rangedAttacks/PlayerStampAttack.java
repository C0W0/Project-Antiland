package com.walfen.antiland.entities.properties.attack.rangedAttacks;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.effect.special.Stung;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.untils.AnimationSupplier;

import java.util.function.IntSupplier;

public class PlayerStampAttack extends PlayerAroundAttack {

    private IntSupplier stungChance;

    public PlayerStampAttack(Handler handler, IntSupplier damageSupplier, IntSupplier rangeSupplier, IntSupplier stungChance, AnimationSupplier animSupplier) {
        super(handler, damageSupplier, rangeSupplier, animSupplier);
        this.stungChance = stungChance;
        animXSize = 128;
        animYSize = 256;
    }

    @Override
    public void checkAttackCollision() {
        if(collisionQueue.size() == 0)
            return;
        for(Entity e: handler.getWorld().getEntityManager().getEntities()) {
            if (e.getCollisionBounds(0, 0).intersect(collisionQueue.get(0).getBound()) &&
                    !e.equals(handler.getPlayer())) {
                handler.getPlayer().getTracker().addTracking(e);
                e.receiveDamage(baseDamage, type);
                if(e.getHealth() > 0){
                    handler.getPlayer().getTracker().addTracking(e);
                    try {
                        Creature c = (Creature)e;
                        if(Math.random() < (float)stungChance.getAsInt()/100)
                            c.addEffect(new Stung(c, 2000));
                    } catch (ClassCastException ignored){}
                }
            }
        }
        collisionQueue.remove(0);
    }

    @Override
    public void draw(Canvas canvas) {
        for(int i = 0; i < animations.size(); i++){
            Animation a = animations.get(i);
            Rect r = new Rect((int)(x), (int)(y-128), (int)(x+128), (int)(y+128));
            int left = (int)(r.left - handler.getGameCamera().getxOffset());
            int top = (int)(r.top - handler.getGameCamera().getyOffset());
            a.draw(canvas, new Rect(left, top, left+r.width(), top+r.height()));
        }
    }
}
