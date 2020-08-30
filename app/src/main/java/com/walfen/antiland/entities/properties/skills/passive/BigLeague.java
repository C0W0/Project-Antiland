package com.walfen.antiland.entities.properties.skills.passive;

import com.walfen.antiland.Handler;
import com.walfen.antiland.gfx.Assets;

public class BigLeague extends PassiveSkill {

    public BigLeague(Handler handler) {
        super(handler, 10, handler.getPlayer(), Assets.NULL); //TODO: icon
    }

    @Override
    protected void onLevelUp() {
        carrier.changePhysicalDamage(2*level);
    }

    @Override
    public boolean levelUpReqMeet() {
        int strengthLv = handler.getPlayer().getSkillsManager().getStrength().getLevel();
        int enduranceLv = handler.getPlayer().getSkillsManager().getEndurance().getLevel();
        return (strengthLv > level+1 && enduranceLv > level+1);
    }

    @Override
    public String getTitle() {
        return "Big League";
    }

    @Override
    public String getDesc() {
        return "Your are an experience fighter, better than any baseball player!";
    }

    @Override
    public String getEffect() {
        return "Increase physical damage by "+2*level+" .";
    }

    @Override
    public String getReq() {
        return "Strength: Lv."+(level+2)+"; Endurance: Lv."+(level+2);
    }
}
