package com.walfen.antiland.entities.properties.skills.active.endurance;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.skills.active.ActiveSkill;
import com.walfen.antiland.gfx.Assets;

public class SecondWind extends ActiveSkill {

    private int hpRegen;

    public SecondWind(Handler handler) {
        super(handler, 10, 5000, Assets.enduranceSkills[0], 101);
        hpRegen = 1;
    }

    @Override
    protected void onTrigger() {
        handler.getPlayer().changeHealth(hpRegen);
    }

    @Override
    public void update() {
        updateData();
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
        hpRegen += 2;
    }

    @Override
    public boolean levelUpReqMeet() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Second Wind";
    }

    @Override
    public String getDesc() {
        return "A knight knows how to recover quickly!";
    }

    @Override
    public String getEffect() {
        return "Regenerate "+hpRegen+" hit points.";
    }

    @Override
    public String getReq() {
        return "";
    }
}
