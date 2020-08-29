package com.walfen.antiland.entities.properties.skills.active;

import android.graphics.Canvas;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.attack.Attack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.PlayerAbilityAttack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.PlayerMeleeAttack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.RangedAttack;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;

public class SharpWind extends ActiveSkill {

    private int additionalDmg;
    private RangedAttack attack;

    public SharpWind(Handler handler) {
        super(handler, 10, 4000, Assets.strengthSkills[0], 1);
        additionalDmg = 5;
        attack = new PlayerAbilityAttack(handler, Attack.Type.PHYSICAL, 256, 10,
                () -> (handler.getPlayer().getPhysicalDamage()+additionalDmg), () -> new Animation(2, Assets.player_SharpWind));
    }

    @Override
    protected void onTrigger() {
        handler.getPlayer().setAttack(attack);
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
        if(!handler.getPlayer().getAttack().equals(attack))
            attack.update();
    }

    @Override
    public void draw(Canvas canvas) {
        if(!handler.getPlayer().getAttack().equals(attack))
            attack.draw(canvas);
    }

    @Override
    protected void onLevelUp() {
        additionalDmg += level;
    }

    @Override
    public boolean levelUpReqMeet() {
        return handler.getPlayer().getSkillsManager().getStrength().getLevel() > level+1;
    }

    @Override
    public String getTitle() {
        return "Sharp Wind Strike";
    }

    @Override
    public String getDesc() {
        return "Concentrate the strength and sent off a lethal strike.";
    }

    @Override
    public String getEffect() {
        return "Cause "+(handler.getPlayer().getPhysicalDamage()+additionalDmg)+" damage to entities of a small area at the direction of attack.";
    }

    @Override
    public String getReq() {
        return "Strength: Lv."+(level+2);
    }
}
