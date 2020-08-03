package com.walfen.antiland.entities.properties.skills.active;

import android.graphics.Bitmap;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.PlayerAroundAttack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.RangedAttacks;
import com.walfen.antiland.gfx.Assets;

public class SwordStorm extends ActiveSkill {

    private int additionalDmg;
    private RangedAttacks attacks;

    public SwordStorm(Handler handler) {
        super(handler, 10, 5000, Assets.joystick_controller);
        additionalDmg = 1;
        attacks = new PlayerAroundAttack(handler, () -> (handler.getPlayer().getPhysicalDamage()+additionalDmg));
    }

    @Override
    protected void onTrigger() {
        attacks.generateAttack(0, 0);
    }

    @Override
    protected void onLevelUp() {
        additionalDmg += level;
    }

    @Override
    public boolean levelUpReqMeet() {
        int strengthLv = handler.getPlayer().getSkillsManager().getStrength().getLevel();
        int agilityLv = handler.getPlayer().getSkillsManager().getAgility().getLevel();
        return (strengthLv > level+1 && agilityLv > level+1);
    }

    @Override
    public String getTitle() {
        return "Sword Storm";
    }

    @Override
    public String getDesc() {
        return "Spin the weapon quickly, causing area of effect damage to entities surrounding the player.";
    }

    @Override
    public String getEffect() {
        return "Cause "+(handler.getPlayer().getPhysicalDamage()+additionalDmg)+" damage to entities within 1 grid of the player.";
    }

    @Override
    public String getReq() {
        return "Strength: Lv."+(level+2)+"; Agility: Lv."+(level+2);
    }

    @Override
    protected void updateData() {
        attacks.update();
    }
}
