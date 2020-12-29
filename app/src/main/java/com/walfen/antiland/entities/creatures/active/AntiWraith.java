package com.walfen.antiland.entities.creatures.active;

import com.walfen.antiland.gfx.Animation;

public class AntiWraith extends TrappedSpirit {

    public AntiWraith(){
        super(208);
        setEntityHealth(50);
        width *= 2;
        height *= 2;
        leftMove.scale(width, height);
        rightMove.scale(width, height);
        upMove.scale(width, height);
        downMove.scale(width, height);
        leftIdle.scale(width, height);
        rightIdle.scale(width, height);
    }

    @Override
    protected void attack() {
        attack.checkAttackCollision();
        Animation temp;
        int distanceX = (int)(target.getX()-x);
        int distanceY = (int)(target.getY()-y);
        if(Math.abs(distanceX) > Math.abs(distanceY)) //left or right
            temp = distanceX < 0?attack.getOverridingAnimations().get(0):attack.getOverridingAnimations().get(1);
        else //up or down
            temp = distanceY < 0?attack.getOverridingAnimations().get(2):attack.getOverridingAnimations().get(3);
        temp.scale(width, height);
        currentAnimation = temp;
        animationCtrlTimer.start(600);
    }

    @Override
    protected void onDeath() {

    }

    @Override
    public String getName() {
        return "AntiWraith";
    }
}
