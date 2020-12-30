package com.walfen.antiland.entities.properties.skills.active.agility;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.effect.special.SpeedBoost;
import com.walfen.antiland.entities.properties.skills.active.ActiveSkill;
import com.walfen.antiland.gfx.Assets;

public class LastGasp extends ActiveSkill {

    public LastGasp(Handler handler) {
        super(handler, 10, 150000, Assets.agilitySkills[1], 201);
    }

    @Override
    protected void onTrigger() {
        int hp = handler.getPlayer().getHealth();
        if(hp < handler.getPlayer().getMaxHp()/10)
            return;
        handler.getPlayer().changeHealth(-hp/2);
        handler.getPlayer().addEffect(new SpeedBoost(handler.getPlayer(), 5000*level, 200));
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
        return handler.getPlayer().getSkillsManager().getAgility().getLevel() > level;
    }

    @Override
    public String getTitle() {
        return "Last Gasp";
    }

    @Override
    public String getDesc() {
        return "Temporarily double the speed by spending half of current hp. Cannot be activated with less than 10% hp";
    }

    @Override
    public String getEffect() {
        return "Half the hp, double the speed";
    }

    @Override
    public String getReq() {
        return "Agility: Lv."+(level+1);
    }
}
