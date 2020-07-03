package com.walfen.antiland.entities.properties.attack.rangedAttacks;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.active.Active;
import com.walfen.antiland.entities.properties.attack.Attacks;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.untils.Utils;

import java.util.ArrayList;

public abstract class RangedAttacks extends Attacks {


    protected float travelSpeed;
    protected ArrayList<RangedAttackCollision> collisionQueue = new ArrayList<>();
    protected ArrayList<Animation> animations = new ArrayList<>();
    protected ArrayList<Point> attackLocation = new ArrayList<>();
    protected int range;

    public RangedAttacks(Handler handler, int baseDamage, int type, int range,
                         int travelSpeed, Active carrier) {
        super(handler, baseDamage, type, carrier);
        this.range = range;
        this.travelSpeed = travelSpeed;
    }

    public RangedAttacks(Handler handler, int baseDamage, int type, int range,
                          int travelSpeed){
        super(handler, baseDamage, type);
        this.range = range;
        this.travelSpeed = travelSpeed;
    }

    @Override
    public void update() {
        super.update();
        for(int i = 0; i < collisionQueue.size(); i++) {
            if(collisionQueue.get(i).isHit()){
                collisionQueue.remove(i);
                animations.remove(i);
                i --;
                continue;
            }
            collisionQueue.get(i).update();
            animations.get(i).update();
        }
        checkAttackCollision();
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
                    if ((carrier == null && e.equals(handler.getWorld().getPlayer())) || e.equals(carrier))
                        continue;
                    if (carrier != null) {
                        if (e.getFaction() != carrier.getFaction()) {
                            e.receiveDamage(baseDamage);
                            r.hit();
                        }
                    }
                    if (carrier == null) {
                        e.receiveDamage(baseDamage);
                        r.hit();
                    }
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        for(int i = 0; i < animations.size(); i++){
            Animation a = animations.get(i);
            Rect r = collisionQueue.get(i).getBound();
            int left = (int)(r.left - handler.getGameCamera().getxOffset());
            int top = (int)(r.top - handler.getGameCamera().getyOffset());
            a.draw(canvas, new Rect(left, top, left+r.width(), top+r.height()));
//            canvas.drawRect(r, new Paint());
        }
    }

    public abstract void generateAttack(float dX, float dY);
}
