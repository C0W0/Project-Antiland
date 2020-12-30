package com.walfen.antiland.entities.properties.skills.active.endurance;

import android.graphics.Canvas;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.effect.Shield;
import com.walfen.antiland.entities.properties.skills.active.ActiveSkill;
import com.walfen.antiland.gfx.Assets;

public class SkinHardening extends ActiveSkill {


    private long shieldActiveDuration;
    private boolean isShieldOn;

    public SkinHardening(Handler handler) {
        super(handler, 10, 10000, Assets.enduranceSkills[2], 103);
        shieldActiveDuration = 5000;
        isShieldOn = false;
    }

    @Override
    protected void onTrigger() {
        handler.getPlayer().setShield(new Shield(level+1, shieldActiveDuration, 100, 100, 200, 200, 200, 200, 200, 200, 200, 200));
        isShieldOn = true;
    }

    @Override
    protected void updateData() {
        if(isShieldOn){
            if(handler.getPlayer().getShield() == null)
                isShieldOn = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    protected void onLevelUp() {

    }

    @Override
    public boolean levelUpReqMeet() {
        int playerLv = handler.getPlayer().getLevel();
        int endLv = handler.getPlayer().getSkillsManager().getEndurance().getLevel();
        return playerLv > level+1 && endLv > level+1;
    }

    @Override
    public String getTitle() {
        return "Skin Hardening";
    }

    @Override
    public String getDesc() {
        return "Temporarily hardening the skin to absorb damage";
    }

    @Override
    public String getEffect() {
        return "Create a shield with a durability of "+(level+1)+" that lasts 5 seconds";
    }

    @Override
    public String getReq() {
        return "Endurance: Lv."+(level+2)+"; Player Lv."+(level+2);
    }
}
