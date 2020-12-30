package com.walfen.antiland.entities.properties.skills.passive.endurance;

import android.graphics.Bitmap;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.skills.passive.PassiveSkill;
import com.walfen.antiland.gfx.Assets;

public class Dreadnought extends PassiveSkill {

    public Dreadnought(Handler handler) {
        super(handler, 5, null, Assets.enduranceSkills[3]);
    }

    @Override
    protected void onLevelUp() {
        handler.getPlayer().changeMagicalDefence((level)*2);
        handler.getPlayer().changeDefence((int)(0.5*level*level+1));
        handler.getPlayer().changePhysicalDamage(level*4);
    }

    @Override
    public boolean levelUpReqMeet() {
        int strengthLv = handler.getPlayer().getSkillsManager().getStrength().getLevel();
        int enduranceLv = handler.getPlayer().getSkillsManager().getEndurance().getLevel();
        return (strengthLv > (level+1)*2-1 && enduranceLv > (level+1)*2-1);
    }

    @Override
    public String getTitle() {
        return "Dreadnought";
    }

    @Override
    public String getDesc() {
        return "Overwhelming strength and impenetrable defence, just like battleships";
    }

    @Override
    public String getEffect() {
        return "Increase magical resistance by "+(level)*2+" ;" +
                "Increase defence by "+(int)(0.5*level*level+1)+" ;" +
                "Increase physical damage by "+level*4+" .";
    }

    @Override
    public String getReq() {
        return "Strength: Lv."+((level+1)*2)+"; Endurance: Lv."+((level+1)*2);
    }
}
