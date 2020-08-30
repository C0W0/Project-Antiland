package com.walfen.antiland.entities.properties.attack.rangedAttacks;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.creatures.active.Active;
import com.walfen.antiland.entities.properties.effect.special.Stung;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;

import java.util.function.IntSupplier;

public class IceSpike extends RangedAttack {

    private int targetX, targetY;
    private long lastAttack;
    private IntSupplier dmgSupplier;

    public IceSpike(Handler handler, IntSupplier dmgSupplier, Active carrier) {
        super(handler, 2, Type.MAGICAL_WATER, 1024, 0, carrier); //set range to 1024 to be safe
        this.dmgSupplier = dmgSupplier;
    }

    @Override
    public void update() {
        super.update();
        baseDamage = dmgSupplier.getAsInt();
        if(animations.size() > collisionQueue.size())
            for(int i = collisionQueue.size(); i < animations.size(); i++)
                animations.get(i).update();
        if(animations.size() > 0){
            Animation a = animations.get(0);
            if(System.currentTimeMillis() > lastAttack+a.getCycleDuration())
                animations.remove(0);
        }
    }

    @Override
    public void checkAttackCollision() {
        if(collisionQueue.size() == 0)
            return;
        for(Entity e: handler.getWorld().getEntityManager().getEntities()) {
            if (e.getCollisionBounds(0, 0).intersect(collisionQueue.get(0).getBound())) {
                if ((carrier == null && e.equals(handler.getPlayer())) || e.equals(carrier))
                    continue;
                if (carrier != null)
                    if (e.getFaction() != carrier.getFaction())
                        e.receiveDamage(baseDamage, type);
            }
        }
        collisionQueue.remove(0);
    }

    @Override
    public void generateAttack(float dX, float dY) {
        if(dX == 100 && dY == 100 && carrier != null){
            Entity target = carrier.getTarget();
            Rect b = target.getCollisionBounds(0, 0);
            RangedAttackCollision rc = new RangedAttackCollision(b.right-b.width()/2-64, b.bottom-128, b.right-b.width()/2+64, b.bottom, 0, 0, 0);
            collisionQueue.add(rc);
            Animation a = new Animation(1.0f, Assets.ice_spike);
            a.scaleForced(128, 128);
            animations.add(a);
            lastAttack = System.currentTimeMillis();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        for(int i = 0; i < animations.size(); i++){
            Animation a = animations.get(i);
            if(carrier != null){
                Entity target = carrier.getTarget();
                Rect b = target.getCollisionBounds(0, 0);
                Rect r = new Rect(b.right-b.width()/2-64, b.bottom-96, b.right-b.width()/2+64, b.bottom+32);
                int left = (int)(r.left - handler.getGameCamera().getxOffset());
                int top = (int)(r.top - handler.getGameCamera().getyOffset());
                a.draw(canvas, new Rect(left, top, left+r.width(), top+r.height()));
            }
        }
    }
}
