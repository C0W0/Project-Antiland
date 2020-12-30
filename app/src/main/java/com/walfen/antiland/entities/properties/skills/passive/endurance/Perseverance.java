package com.walfen.antiland.entities.properties.skills.passive.endurance;

import android.graphics.Bitmap;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.skills.passive.PassiveSkill;
import com.walfen.antiland.gfx.Assets;

public class Perseverance extends PassiveSkill {

    public Perseverance(Handler handler) {
        super(handler, 10, null, Assets.enduranceSkills[1]);
    }

    @Override
    protected void onLevelUp() {
        handler.getPlayer().changeMagicalDefence(level+1);
    }

    @Override
    public boolean levelUpReqMeet() {
        int enduranceLv = handler.getPlayer().getSkillsManager().getEndurance().getLevel();
        return enduranceLv > level+1;
    }

    @Override
    public String getTitle() {
        return "Perseverance";
    }

    @Override
    public String getDesc() {
        return "Strength of the mind";
    }

    @Override
    public String getEffect() {
        return "Increase magical resistance by "+(level+1);
    }

    @Override
    public String getReq() {
        return "Endurance: Lv."+(level+2);
    }
}
