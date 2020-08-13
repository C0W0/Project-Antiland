package com.walfen.antiland.entities.properties.skills.active;

import android.graphics.Bitmap;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.effect.Shield;
import com.walfen.antiland.gfx.Assets;

public class TestShield extends ActiveSkill {

    private long shieldActiveTime, shieldActiveDuration;
    private boolean isShieldOn;

    public TestShield(Handler handler) {
        super(handler, 1, 10000, Assets.sharpWind, -1);
        level = 1;
        shieldActiveDuration = 5000;
        isShieldOn = false;
    }

    @Override
    protected void onTrigger() {
        handler.getPlayer().setShield(new Shield(2));
        shieldActiveTime = System.currentTimeMillis();
        isShieldOn = true;
    }

    @Override
    protected void updateData() {
        if(isShieldOn){
            if(System.currentTimeMillis() - shieldActiveTime > shieldActiveDuration){
                handler.getPlayer().setShield(null);
                isShieldOn = false;
            }
        }
    }

    @Override
    protected void onLevelUp() {

    }

    @Override
    public boolean levelUpReqMeet() {
        return true;
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public String getDesc() {
        return "";
    }

    @Override
    public String getEffect() {
        return "";
    }

    @Override
    public String getReq() {
        return "";
    }
}
