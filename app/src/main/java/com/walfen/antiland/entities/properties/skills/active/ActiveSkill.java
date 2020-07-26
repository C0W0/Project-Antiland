package com.walfen.antiland.entities.properties.skills.active;

import android.graphics.Bitmap;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.skills.Skill;
import com.walfen.antiland.gfx.ImageEditor;

public abstract class ActiveSkill extends Skill {

    protected Bitmap icon;
    protected long lastActiveTime, activeCooldown, activeTimer;

    public ActiveSkill(Handler handler, int maxLevel, long msCooldown, Bitmap icon) {
        super(handler, maxLevel, null);
        this.icon = ImageEditor.scaleBitmapForced(icon, 128);
        activeCooldown = msCooldown;
        activeTimer = activeCooldown;
    }

    public void triggerTest(){
        if(activeTimer < activeCooldown)
            return;
        onTrigger();
        activeTimer = 0;
    }

    protected abstract void onTrigger();

    public void update(){
        updateData();
        activeTimer += System.currentTimeMillis() - lastActiveTime;
        lastActiveTime = System.currentTimeMillis();
    }

    protected abstract void updateData();

    public Bitmap getIcon() {
        return icon;
    }

    public float getCooldownSecond(){
        return Math.max((activeCooldown - activeTimer)/1000.f, 0);
    }

    @Override
    public void setLevel(int level) {
        int d = level-this.level;
        for(int i = 0; i < d; i++){
            this.level ++;
            onLevelUp();
        }
    }
}
