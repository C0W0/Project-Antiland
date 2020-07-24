package com.walfen.antiland.entities.properties.skills.active;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.skills.Skill;

public abstract class ActiveSkill extends Skill {

    public ActiveSkill(Handler handler, int maxLevel, Creature carrier) {
        super(handler, maxLevel, carrier);
    }
}
