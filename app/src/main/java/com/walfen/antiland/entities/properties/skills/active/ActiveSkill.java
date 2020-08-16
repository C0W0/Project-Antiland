package com.walfen.antiland.entities.properties.skills.active;

import android.graphics.Bitmap;

import com.walfen.antiland.GameHierarchyElement;
import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.skills.Skill;
import com.walfen.antiland.gfx.ImageEditor;

public abstract class ActiveSkill extends Skill implements GameHierarchyElement {

    protected long lastActiveTime, activeCooldown, activeTimer;

    protected int hID;

    public ActiveSkill(Handler handler, int maxLevel, long msCooldown, Bitmap texture, int hID) {
        super(handler, maxLevel, null, texture);
        this.hID = hID;
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

    @Override
    public void update(){
        updateData();
        activeTimer += System.currentTimeMillis() - lastActiveTime;
        lastActiveTime = System.currentTimeMillis();
    }

    protected abstract void updateData();

    /** get the encoded hierarchyID of the skill
     * ArrayList id: (int)(id/100)
     * ArrayList index: id%100
     * @return
     */
    public int getHierarchyID(){
        return hID;
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
