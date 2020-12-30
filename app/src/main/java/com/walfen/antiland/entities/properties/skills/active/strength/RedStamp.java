package com.walfen.antiland.entities.properties.skills.active.strength;

import android.graphics.Canvas;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.PlayerAroundAttack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.PlayerStampAttack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.RangedAttack;
import com.walfen.antiland.entities.properties.skills.active.ActiveSkill;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;

public class RedStamp extends ActiveSkill {

    private int additionalDmg;
    private RangedAttack attack;
    private float chance;

    public RedStamp(Handler handler) {
        super(handler, 10, 5000, Assets.strengthSkills[3], 4);
        additionalDmg = 1;
        chance = 0.3f;
        attack = new PlayerStampAttack(handler, () -> (handler.getPlayer().getPhysicalDamage()/2+additionalDmg), () -> 128, () -> (int)(chance*100),
        () -> new Animation(0.5f, Assets.explosion));
    }

    @Override
    protected void onTrigger() {
        attack.generateAttack(0, 0);
    }

    @Override
    protected void onLevelUp() {
        additionalDmg += level;
    }

    @Override
    public boolean levelUpReqMeet() {
        return (handler.getPlayer().getSkillsManager().getStrengthSL().get(3).getLevel() > level);
    }

    @Override
    protected void updateData() {
        attack.update();
    }

    @Override
    public void draw(Canvas canvas) {
        attack.draw(canvas);
    }

    @Override
    public String getTitle() {
        return "Red Stamp";
    }

    @Override
    public String getDesc() {
        return "Stamp towards the ground with the weight of the body and armour.";
    }

    @Override
    public String getEffect() {
        return "Cause "+(handler.getPlayer().getPhysicalDamage()+additionalDmg)+" damage to entities within 1 grid of the player. " +
                (float)((int)(chance*100))+"% chance of causing a Stung";
    }

    @Override
    public String getReq() {
        return "Big League: Lv."+(level+1);
    }
}
