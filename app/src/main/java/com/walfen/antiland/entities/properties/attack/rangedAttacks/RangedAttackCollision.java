package com.walfen.antiland.entities.properties.attack.rangedAttacks;


import android.graphics.Rect;

import com.walfen.antiland.untils.Utils;

public class RangedAttackCollision {

    private int oX, oY;
    private final Rect bound;
    private float xMove, yMove;
    private int hitCount;

    public RangedAttackCollision(int left, int top, int right, int bottom, float dX, float dY, int speed){
        bound = new Rect(left, top, right, bottom);
        oX = left;
        oY = top;
        xMove = dX* speed;
        yMove = dY* speed;
        hitCount = 0;
    }

    public float getDistance(){
        return Utils.Py.getC(bound.left-oX, bound.top-oY);
    }

    public void update(){
//        System.out.println(bound.left+" "+
//                bound.top+" "+bound.right+" "+bound.bottom);
        bound.left += xMove;
        bound.top += yMove;
        bound.right += xMove;
        bound.bottom += yMove;
    }

    public boolean intersect(Rect r){
        return new Rect(bound).intersect(r);
    }

    public boolean isHit() {
        return hitCount > 0;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void hit(){
        hitCount++;
    }

    public void setHit(int hit) {
        hitCount = hit;
    }

    public Rect getBound() {
        return new Rect(bound);
    }
}
