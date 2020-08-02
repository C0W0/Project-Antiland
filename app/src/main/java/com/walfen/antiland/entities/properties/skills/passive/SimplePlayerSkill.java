package com.walfen.antiland.entities.properties.skills.passive;

import android.graphics.Bitmap;

import com.walfen.antiland.Handler;
import com.walfen.antiland.ui.ChangeEvent;

public class SimplePlayerSkill extends PassiveSkill {

    private ChangeEvent event;

    public SimplePlayerSkill(Handler handler, int maxLevel, ChangeEvent event, Bitmap texture) {
        super(handler, maxLevel, null, texture);
        this.event = event;
    }

    @Override
    protected void onLevelUp() {
        event.onChange();
    }

    @Override
    public boolean levelUpReqMeet() {
        return true;
    }

    public void setLevel(int level) {
        int d = level-this.level;
        for(int i = 0; i < d; i++){
            this.level ++;
            event.onChange();
        }
    }
}
