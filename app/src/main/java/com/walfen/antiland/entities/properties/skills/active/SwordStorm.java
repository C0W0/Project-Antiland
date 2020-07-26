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
    protected void updateData() {
        attacks.update();
    }
}