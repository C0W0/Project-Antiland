package com.walfen.antiland.entities.properties.skills.active.intelligence;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.PlayerIceSpike;
import com.walfen.antiland.entities.properties.skills.active.ActiveSkill;
import com.walfen.antiland.gfx.Assets;

public class IceSpike extends ActiveSkill {

    private PlayerIceSpike iceSpike;

    public IceSpike(Handler handler) {
        super(handler, 10, 8000, Assets.intelligenceSkills[1], 402);
        iceSpike = new PlayerIceSpike(handler, () -> handler.getPlayer().getMagicalDamage()+level*2);
    }

    @Override
    public void triggerTest() {
        if(activeTimer < activeCooldown || handler.getPlayer().getMp() < 2)
            return;
        onTrigger();
        activeTimer = 0;
    }

    @Override
    protected void onTrigger() {
        handler.getPlayer().changeMp(-2);
        iceSpike.generateAttack(handler.getPlayer().getTracker().getTrackingEntities());
    }

    @Override
    protected void updateData() {
        iceSpike.update();
    }

    @Override
    public void draw(Canvas canvas) {
        iceSpike.draw(canvas);
    }

    @Override
    protected void onLevelUp() {

    }

    @Override
    public boolean levelUpReqMeet() {
        int intelLevel = handler.getPlayer().getSkillsManager().getIntelligence().getLevel();
        int physLevel = handler.getPlayer().getSkillsManager().getPhysics().getLevel();
        return intelLevel > level && physLevel > level;
    }

    @Override
    public String getTitle() {
        return "Ice Spike";
    }

    @Override
    public String getDesc() {
        return "Generate an ice spike from the ground to attack the currently engaged enemies. Consumes 2 mp.";
    }

    @Override
    public String getEffect() {
        return "Cause "+(handler.getPlayer().getMagicalDamage()+level*2)+" magic damage to all engaged enemies";
    }

    @Override
    public String getReq() {
        return "Intelligence: Lv."+(level+1)+"; Physics: Lv."+(level+1);
    }
}
