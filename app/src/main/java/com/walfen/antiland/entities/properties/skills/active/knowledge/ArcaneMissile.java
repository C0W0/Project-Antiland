package com.walfen.antiland.entities.properties.skills.active.knowledge;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.attack.Attack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.PlayerAbilityAttack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.RangedAttack;
import com.walfen.antiland.entities.properties.skills.active.ActiveSkill;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;

public class ArcaneMissile extends ActiveSkill {

    private int additionalDmg;
    private RangedAttack attack;

    public ArcaneMissile(Handler handler) {
        super(handler, 10, 10000, Assets.knowledgeSkills[2], 305);
        additionalDmg = 10;
        attack = new PlayerAbilityAttack(handler, Attack.Type.MAGICAL_PURE, 1024, 30,
                () -> (handler.getPlayer().getMagicalDamage()+additionalDmg), () -> new Animation(0.5f, Assets.arcaneMissile));
    }

    @Override
    public void triggerTest() {
        if(activeTimer < activeCooldown || handler.getPlayer().getMp() < 3)
            return;
        onTrigger();
        activeTimer = 0;
    }

    @Override
    protected void onTrigger() {
        handler.getPlayer().changeMp(-3);
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
        additionalDmg += level*level*0.5;
    }

    @Override
    public boolean levelUpReqMeet() {
        int physLevel = handler.getPlayer().getSkillsManager().getPhysics().getLevel();
        int chemLevel = handler.getPlayer().getSkillsManager().getChemistry().getLevel();
        return physLevel > level && chemLevel > level;
    }

    @Override
    public String getTitle() {
        return "Arcane Missile";
    }

    @Override
    public String getDesc() {
        return "Summon an arcane missile and fire towards this direction of attack. Consumes 3 mp.";
    }

    @Override
    public String getEffect() {
        return "Cause "+(handler.getPlayer().getMagicalDamage()+additionalDmg)+" magic damage to entities of a small area at the direction of attack.";
    }

    @Override
    public String getReq() {
        return "Physics: Lv."+(level+1)+"; Chemistry: Lv."+(level+1);
    }
}
