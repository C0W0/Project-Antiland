package com.walfen.antiland.entities.properties.skills.active.intelligence;

import android.graphics.Canvas;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.attack.Attack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.PlayerAbilityAttack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.RangedAttack;
import com.walfen.antiland.entities.properties.skills.active.ActiveSkill;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;

public class WindBlade extends ActiveSkill {

    private int additionalDmg;
    private RangedAttack attack;

    public WindBlade(Handler handler) {
        super(handler, 10, 10000, Assets.intelligenceSkills[2], 403);
        additionalDmg = 3;
        attack = new PlayerAbilityAttack(handler, Attack.Type.PHYSICAL, 1024, 15,
                () -> (handler.getPlayer().getMagicalDamage()+additionalDmg), () -> new Animation(2, Assets.player_wind));
    }

    @Override
    public void triggerTest() {
        if(activeTimer < activeCooldown || handler.getPlayer().getMp() < 1)
            return;
        onTrigger();
        activeTimer = 0;
    }

    @Override
    protected void onTrigger() {
        handler.getPlayer().changeMp(-1);
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
        int intelLevel = handler.getPlayer().getSkillsManager().getIntelligence().getLevel();
        int chemLevel = handler.getPlayer().getSkillsManager().getChemistry().getLevel();
        return intelLevel > level && chemLevel > level;
    }

    @Override
    public String getTitle() {
        return "Wind Blade";
    }

    @Override
    public String getDesc() {
        return "Use a swarm of hypersonic wind to strike an area. Consumes 1 mp.";
    }

    @Override
    public String getEffect() {
        return "Cause "+(handler.getPlayer().getMagicalDamage()+additionalDmg)+" magic damage to entities of a small area at the direction of attack.";
    }

    @Override
    public String getReq() {
        return "Intelligence: Lv."+(level+1);
    }
}
