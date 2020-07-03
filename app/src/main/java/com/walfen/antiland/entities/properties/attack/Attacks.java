package com.walfen.antiland.entities.properties.attack;


import android.graphics.Canvas;

import com.walfen.antiland.GameHierarchyElement;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.active.Active;
import com.walfen.antiland.gfx.Animation;

import java.util.ArrayList;

public abstract class Attacks implements GameHierarchyElement {

    protected float x, y;
    protected Handler handler;
    protected int type;
    protected int baseDamage;
    protected final Active carrier;
    protected ArrayList<Animation> carrierAnimations;

    public Attacks(Handler handler, int baseDamage, int type, Active carrier){
        carrierAnimations = new ArrayList<>();
        this.handler = handler;
        this.baseDamage = baseDamage;
        this.type = type;
        this.carrier = carrier;
    }

    public Attacks(Handler handler, int baseDamage, int type){
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
            x = handler.getWorld().getPlayer().getX();
            y = handler.getWorld().getPlayer().getY();
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
}
