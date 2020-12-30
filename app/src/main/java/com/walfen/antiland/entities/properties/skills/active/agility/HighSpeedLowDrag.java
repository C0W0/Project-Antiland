package com.walfen.antiland.entities.properties.skills.active.agility;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.effect.special.SpeedBoost;
import com.walfen.antiland.entities.properties.skills.active.ActiveSkill;
import com.walfen.antiland.gfx.Assets;

public class HighSpeedLowDrag extends ActiveSkill {

    public HighSpeedLowDrag(Handler handler) {
        super(handler, 10, 25000, Assets.agilitySkills[2], 202);
    }

    @Override
    protected void onTrigger() {
        handler.getPlayer().addEffect(new SpeedBoost(handler.getPlayer(), 10000, 5*(level+2)));
    }

    @Override
    protected void updateData() {

    }

    @Override
    public void draw(Canvas canvas) {

    }

    @Override
    protected void onLevelUp() {
        handler.getPlayer().changeSpeed(1);
    }

    @Override
    public boolean levelUpReqMeet() {
        return handler.getPlayer().getSkillsManager().getAgility().getLevel() > level;
    }

    @Override
    public String getTitle() {
        return "High Speed, Low Drag";
    }

    @Override
    public String getDesc() {
        return "Are you rushing to a place?";
    }

    @Override
    public String getEffect() {
        return "Temporally increase speed by "+5*(level+2)+" %.";
    }

    @Override
    public String getReq() {
        return "Agility: Lv."+(level+1);
    }
}
