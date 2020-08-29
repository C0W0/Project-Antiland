package com.walfen.antiland.entities.properties.skills.active;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.RangedAttack;

public class HeadBash extends ActiveSkill {


    private RangedAttack attack;

    public HeadBash(Handler handler, int maxLevel, long msCooldown, Bitmap texture, int hID) {
        super(handler, maxLevel, msCooldown, texture, hID);
    }

    @Override
    protected void onTrigger() {

    }

    @Override
    public void update() {
        updateData();
        if(handler.getPlayer().getAttack().equals(attack))
            activeTimer = 0;
        else
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
        return false;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getDesc() {
        return null;
    }

    @Override
    public String getEffect() {
        return null;
    }

    @Override
    public String getReq() {
        return null;
    }
}
