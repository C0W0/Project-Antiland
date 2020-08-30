package com.walfen.antiland.entities.properties.skills.passive;

import android.graphics.Bitmap;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.skills.Skill;

public abstract class PassiveSkill extends Skill {

    public PassiveSkill(Handler handler, int maxLevel, Creature carrier, Bitmap texture) {
        super(handler, maxLevel, carrier, texture);
    }

    @Override
    public void setLevel(int level) {
        int d = level-this.level;
        for(int i = 0; i < d; i++){
            this.level ++;
            onLevelUp();
        }
    }
}
