package com.walfen.antiland.entities.properties.skills.active.knowledge;

import android.graphics.Canvas;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.effect.Shield;
import com.walfen.antiland.entities.properties.skills.active.ActiveSkill;
import com.walfen.antiland.gfx.Assets;

public class MageArmour extends ActiveSkill {

    private long shieldActiveDuration;
    private boolean isShieldOn;

    public MageArmour(Handler handler) {
        super(handler, 10, 10000, Assets.knowledgeSkills[1], 302);
        shieldActiveDuration = 10000;
        isShieldOn = false;
    }

    @Override
    public void triggerTest() {
        if(activeTimer < activeCooldown || handler.getPlayer().getMp() < 1)
            return;
        onTrigger();
        activeTimer = 0;
    }

    @Override
    protected void onTrigger() {
        handler.getPlayer().changeMp(-1);
        handler.getPlayer().setShield(new Shield(2*(level+1), shieldActiveDuration, 200, 200, 100, 100, 100, 100, 100, 100, 100, 100));
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
        int knowledgeLv = handler.getPlayer().getSkillsManager().getKnowledge().getLevel();
        return playerLv > 2*level+1 && knowledgeLv > level+1;
    }

    @Override
    public String getTitle() {
        return "Mage Armour";
    }

    @Override
    public String getDesc() {
        return "Using magic to create a shield to absorb damage. Consumes 1 mp.";
    }

    @Override
    public String getEffect() {
        return "Create a shield with a durability of "+2*(level+1)+" that lasts 10 seconds";
    }

    @Override
    public String getReq() {
        return "Endurance: Lv."+(level+2)+"; Player Lv."+(2*level+2);
    }

}
