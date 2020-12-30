package com.walfen.antiland.entities.properties.skills.active.strength;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.effect.StatusEffect;
import com.walfen.antiland.entities.properties.skills.active.ActiveSkill;
import com.walfen.antiland.gfx.Assets;

public class BraveHeart extends ActiveSkill {

    public BraveHeart(Handler handler) {
        super(handler, 2, 20000, Assets.strengthSkills[5], 6);
    }

    @Override
    protected void onTrigger() {
        StatusEffect braveHeart = new com.walfen.antiland.entities.properties.effect.special.BraveHeart(handler.getPlayer(), 5000*level, level);
        handler.getPlayer().addEffect(braveHeart);
    }

    @Override
    public void update() {
        super.update();
        activeTimer += System.currentTimeMillis() - lastActiveTime;
        lastActiveTime = System.currentTimeMillis();
    }

    @Override
    protected void updateData() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    protected void onLevelUp() {

    }

    @Override
    public boolean levelUpReqMeet() {
        return handler.getPlayer().getSkillsManager().getStrength().getLevel() > 5*(level+1)-1;
    }

    @Override
    public String getTitle() {
        return "Brave Heart";
    }

    @Override
    public String getDesc() {
        return "Fighters always have brave hearts!";
    }

    @Override
    public String getEffect() {
        return "When triggered, increase the melee damage and defence for a duration, and regenerate 10% hit points after the effects is over.";
    }

    @Override
    public String getReq() {
        return "Strength: Lv."+((level+1)*5);
    }
}
