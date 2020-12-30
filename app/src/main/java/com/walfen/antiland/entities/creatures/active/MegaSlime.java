package com.walfen.antiland.entities.creatures.active;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.gfx.Animation;

public class MegaSlime extends Slime {


    public MegaSlime(){
        super(15, 209);
        setEntityHealth(30);
        width *= 3;
        height *= 3;
        leftMove.scale(width, height);
        rightMove.scale(width, height);
        idle.scale(width, height);
    }

    @Override
    protected void attack() {
        attack.checkAttackCollision();
        animationCtrlTimer.start(300);
        Animation temp = target.getX()-x < 0?attack.getOverridingAnimations().get(0):attack.getOverridingAnimations().get(1);
        temp.scale(width, height);
        currentAnimation = temp;
    }

    @Override
    public void update(){
        super.update();
        if(animationCtrlTimer.atTarget()){
            currentAnimation = xMove < 0?leftMove:rightMove;
        }
        currentAnimation.update();
    }

    @Override
    public void draw(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        currentAnimation.draw(canvas, new Rect(left, top, left+width, top+height));
    }

    @Override
    public String getName() {
        return "Mega Slime";
    }

    @Override
    protected void onDeath() {
        super.onDeath();
        handler.getUIManager().popUpMessage("You found a money bag on the dead body of the giant slime");
        handler.getPlayer().changeWealth(50);
    }
}
