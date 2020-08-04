package com.walfen.antiland.entities.properties.skills;

import android.graphics.Bitmap;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Creature;

public abstract class Skill {

    protected Handler handler;
    protected int level;
    protected int maxLevel;
    protected Creature carrier;
    protected Bitmap texture;

    public Skill(Handler handler, int maxLevel, Creature carrier, Bitmap texture){
        this.handler = handler;
        this.maxLevel = maxLevel;
        level = 0;
        this.carrier = carrier;
        this.texture = texture;
    }

    public void levelUp(){
        if(level+1 > maxLevel)
            return;
        level += 1;
        onLevelUp();
    }

    protected abstract void onLevelUp();

    public abstract boolean levelUpReqMeet();

    public abstract String getTitle();

    public abstract String getDesc();

    public abstract String getEffect();

    public abstract String getReq();

    public boolean isActive(){
        return level != 0;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void changeLevel(int deltaLevel){
        level += deltaLevel;
    }

    public Bitmap getTexture() {
        return texture;
    }
}
