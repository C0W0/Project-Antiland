package com.walfen.antiland.entities.properties.skills.active;

import android.graphics.Bitmap;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.PlayerAbilityAttack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.RangedAttacks;
import com.walfen.antiland.gfx.Assets;

public class SharpWind extends ActiveSkill {

    private int additionalDmg;
    private RangedAttacks attacks;

    public SharpWind(Handler handler) {
        super(handler, 10, 4000, Assets.sharpWind);
        additionalDmg = 5;
        attacks = new PlayerAbilityAttack(handler, () -> (handler.getPlayer().getPhysicalDamage()+additionalDmg));
    }

    @Override
    protected void onTrigger() {
        handler.getPlayer().setAttack(attacks);
    }

    @Override
    public void update() {
        updateData();
        if(handler.getPlayer().getAttack().equals(attacks))
            activeTimer = 0;
        else
            activeTimer += System.currentTimeMillis() - lastActiveTime;
        lastActiveTime = System.currentTimeMillis();
    }

    @Override
    protected void updateData() {
        if(!handler.getPlayer().getAttack().equals(attacks))
            attacks.update();
    }

    @Override
    protected void onLevelUp() {
        additionalDmg += level;
    }

    @Override
    public boolean levelUpReqMeet() {
        return handler.getPlayer().getSkillsManager().getStrength().getLevel() > level+1;
    }
}
