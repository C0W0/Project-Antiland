package com.walfen.antiland.entities.properties.attack.rangedAttacks;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.GameHierarchyElement;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.creatures.Player;
import com.walfen.antiland.entities.creatures.active.Active;
import com.walfen.antiland.entities.properties.attack.Attack;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;

import java.util.ArrayList;
import java.util.function.IntSupplier;

public class PlayerIceSpike implements GameHierarchyElement {

    private IntSupplier dmgSupplier;
    private Handler handler;
    private ArrayList<Animation> animations;
    private ArrayList<Rect> animBounds;
    private long lastAttack;

    public PlayerIceSpike(Handler handler, IntSupplier dmgSupplier) {
        this.handler = handler;
        this.dmgSupplier = dmgSupplier;
        animations = new ArrayList<>();
        animBounds = new ArrayList<>();
    }

    @Override
    public void update() {
        for(int i = 0; i < animations.size(); i++){
            animations.get(i).update();
            if(System.currentTimeMillis() > animations.get(i).getCycleDuration()+lastAttack){
                animations.remove(i);
                animBounds.remove(i);
                i --;
            }
        }
    }

    public void generateAttack(ArrayList<Entity> targetEntities) {
        for(Entity e: targetEntities){
            Animation a = new Animation(1.0f, Assets.ice_spike);
            a.scaleForced(128, 128);
            animations.add(a);
            e.receiveDamage(dmgSupplier.getAsInt(), Attack.Type.MAGICAL_WATER);
            animBounds.add(e.getCollisionBounds(0, 0));
        }
        lastAttack = System.currentTimeMillis();
    }

    @Override
    public void draw(Canvas canvas) {
        for(int i = 0; i < animations.size(); i++){
            Animation a = animations.get(i);
            Rect r = animBounds.get(i);
            int left = (int)(r.left+r.width()/2-64-handler.getGameCamera().getxOffset());
            int top = (int)(r.bottom-128-handler.getGameCamera().getyOffset());
            a.draw(canvas, new Rect(left, top, left+128, top+128));
        }
    }
}
