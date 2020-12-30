package com.walfen.antiland.entities.properties.skills.active.agility;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.attack.Attack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.PlayerAbilityAttack;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.RangedAttack;
import com.walfen.antiland.entities.properties.effect.special.SpeedBoost;
import com.walfen.antiland.entities.properties.skills.active.ActiveSkill;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;

public class HitAndRun extends ActiveSkill {

    private int additionalDmg;
    private RangedAttack attack;

    public HitAndRun(Handler handler) {
        super(handler, 10, 15000, Assets.agilitySkills[3], 203);
        additionalDmg = 5;
        attack = new PlayerAbilityAttack(handler, Attack.Type.PHYSICAL, 256, 10,
                () -> (handler.getPlayer().getPhysicalDamage()+additionalDmg), () -> new Animation(2, new Bitmap[]{Assets.player_sharpWind}));
        additionalDmg = 0;
    }

    @Override
    protected void onTrigger() {
        handler.getPlayer().addEffect(new SpeedBoost(handler.getPlayer(), 5000, 5*(level+2)));
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
        int aglLv = handler.getPlayer().getSkillsManager().getAgility().getLevel();
        int strengthLv = handler.getPlayer().getSkillsManager().getStrength().getLevel();
        return aglLv > level && strengthLv > level;
    }

    @Override
    public String getTitle() {
        return "Hit and Run";
    }

    @Override
    public String getDesc() {
        return "Hide and strike like a viper!";
    }

    @Override
    public String getEffect() {
        return "Gain a speed boost. The next attack causes an additional "+(handler.getPlayer().getPhysicalDamage()+additionalDmg)+" damage";
    }

    @Override
    public String getReq() {
        return "Agility: Lv."+(level+1)+" ; Strength: Lv."+(level+1);
    }
}
