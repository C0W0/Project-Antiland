package com.walfen.antiland.entities.properties.attack;


import android.graphics.Canvas;

import com.walfen.antiland.GameHierarchyElement;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.creatures.active.Active;
import com.walfen.antiland.gfx.Animation;

import java.util.ArrayList;

public abstract class Attack implements GameHierarchyElement {

    protected float x, y;
    protected Handler handler;
    protected int type;
    protected int baseDamage;
    protected final Active carrier;
    protected ArrayList<Animation> carrierAnimations;

    public Attack(Handler handler, int baseDamage, int type, Active carrier){
        carrierAnimations = new ArrayList<>();
        this.handler = handler;
        this.baseDamage = baseDamage;
        this.type = type;
        this.carrier = carrier;
    }

    public Attack(Handler handler, int baseDamage, int type){
        this.handler = handler;
        this.baseDamage = baseDamage;
        this.type = type;
        this.carrier = null;
    }

    @Override
    public void update(){
        if(carrier != null) {
            x = carrier.getX();
            y = carrier.getY();
        }else {
            x = handler.getPlayer().getX();
            y = handler.getPlayer().getY();
        }
    }

    //leave empty until the attack animation is fixed
    @Override
    public void draw(Canvas canvas){

    }

    public abstract void checkAttackCollision();

    public ArrayList<Animation> getOverridingAnimations(){
        return carrierAnimations;
    }


    public static class Type{
        public static final int SPECIAL_IGNORE_DEFENCE = 0;
        public static final int PHYSICAL = 1;
        public static final int MAGICAL_LIGHT = 2;
        public static final int MAGICAL_DARK = 3;
        public static final int MAGICAL_PURE = 4;
        public static final int MAGICAL_NATURE = 5;
        public static final int MAGICAL_WATER = 6;
        public static final int MAGICAL_FIRE = 7;
    }
}
