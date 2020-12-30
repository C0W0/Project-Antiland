package com.walfen.antiland.entities.properties.skills.passive.strength;

import android.graphics.Bitmap;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.skills.passive.PassiveSkill;
import com.walfen.antiland.gfx.Assets;

public class DemoExpert extends PassiveSkill {

    public DemoExpert(Handler handler) {
        super(handler, 10, null, Assets.strengthSkills[4]);
    }

    @Override
    protected void onLevelUp() {
        handler.getPlayer().changePhysicalDamage((int)(0.5*level*level+1));
        handler.getPlayer().changeMagicalDamage((int)(0.5*level*level+1));
    }

    @Override
    public boolean levelUpReqMeet() {
        int strengthLv = handler.getPlayer().getSkillsManager().getStrength().getLevel();
        int playerLv = handler.getPlayer().getLevel();
        return (strengthLv > 2*level+1 && playerLv > level+1);
    }

    @Override
    public String getTitle() {
        return "Demolition Expert";
    }

    @Override
    public String getDesc() {
        return "You are well aware of the weak spot of any entity!";
    }

    @Override
    public String getEffect() {
        return "Increase physical and magical damage by "+(int)(0.5*level*level)+" .";
    }

    @Override
    public String getReq() {
        return "Strength: Lv."+(2*level+2)+"; Player Lv."+(level+2);
    }
}
