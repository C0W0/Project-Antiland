package com.walfen.antiland.entities.properties.skills;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Creature;

public abstract class Skill {

    protected Handler handler;
    protected int level;
    protected int maxLevel;
    protected Creature carrier;

    public Skill(Handler handler, int maxLevel, Creature carrier){
        this.handler = handler;
        this.maxLevel = maxLevel;
        level = 0;
        this.carrier = carrier;
    }

    public boolean isActive(){
        return level == 0;
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
}
