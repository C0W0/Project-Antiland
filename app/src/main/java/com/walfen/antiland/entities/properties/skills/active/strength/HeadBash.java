package com.walfen.antiland.entities.properties.skills.active.strength;

import android.graphics.Canvas;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.attack.Attack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.PlayerHeadBash;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.RangedAttack;
import com.walfen.antiland.entities.properties.skills.active.ActiveSkill;
import com.walfen.antiland.gfx.Assets;

public class HeadBash extends ActiveSkill {


    private RangedAttack attack;
    private float chance;

    public HeadBash(Handler handler) {
        super(handler, 5, 2000, Assets.strengthSkills[1], 2);
        chance = 0.3f;
        attack = new PlayerHeadBash(handler, () -> handler.getPlayer().getPhysicalDamage(), () -> (int)(chance*100));
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
        chance += 0.05;
    }

    @Override
    public boolean levelUpReqMeet() {
        return handler.getPlayer().getSkillsManager().getStrength().getLevel() > 2*level-1;
    }

    @Override
    public String getTitle() {
        return "Head Bash";
    }

    @Override
    public String getDesc() {
        return "Hit the enemy hard with the helmet.";
    }

    @Override
    public String getEffect() {
        return "Cause "+handler.getPlayer().getPhysicalDamage()+" damage to entities of a small area at the direction of attack. " +
                (float)((int)(chance*100))+"% chance of causing a Stung";
    }

    @Override
    public String getReq() {
        return "Strength: Lv."+(level*2);
    }
}
