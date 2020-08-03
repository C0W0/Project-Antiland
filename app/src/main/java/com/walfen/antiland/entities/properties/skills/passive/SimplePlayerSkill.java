package com.walfen.antiland.entities.properties.skills.passive;

import android.graphics.Bitmap;

import com.walfen.antiland.Handler;
import com.walfen.antiland.ui.ChangeEvent;

public class SimplePlayerSkill extends PassiveSkill {

    private ChangeEvent event;
    private String title, desc;
    private String effects;

    public SimplePlayerSkill(Handler handler, int maxLevel, ChangeEvent event, Bitmap texture,
                             String title, String desc, String effects) {
        super(handler, maxLevel, null, texture);
        this.title = title;
        this.desc = desc;
        this.effects = effects;
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

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public String getEffect() {
        return effects;
    }

    @Override
    public String getReq() {
        return "";
    }

    public void setLevel(int level) {
        int d = level-this.level;
        for(int i = 0; i < d; i++){
            this.level ++;
            event.onChange();
        }
    }
}
