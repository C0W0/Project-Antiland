package com.walfen.antiland.entities.properties.skills.passive;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.skills.Skill;

public abstract class PassiveSkill extends Skill {

    public PassiveSkill(Handler handler, int maxLevel, Creature carrier) {
        super(handler, maxLevel, carrier);
    }

    public void levelUp(){
        if(level+1 > maxLevel)
            return;
        level += 1;
        onLevelUp();
    }

    protected abstract void onLevelUp();
}
