package com.walfen.antiland.entities.creatures.active;


import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.attack.meleeAttacks.SlimeBash;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.tiles.Tile;
import com.walfen.antiland.untils.MSTimeController;

public class Slime extends Active {

    private Animation leftMove, rightMove, idle, currentAnimation;
    private MSTimeController animationCtrlTimer = new MSTimeController();

    public Slime() {
        super(Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 1000, 4);
        spottingRange = 512;
        maxIdealRange = 128;
        minIdealRange = 0; // melee
        patrolRange = 512;
        giveUpRange = 1300;
        bounds.left = 0;
        bounds.top = 0;
        bounds.right = Tile.TILEWIDTH;
        bounds.bottom = Tile.TILEHEIGHT;
        faction = 1;
        leftMove = new Animation(0.3f, Assets.slimeMovementLeft);
        rightMove = new Animation(0.3f, Assets.slimeMovementRight);
        idle = new Animation(0.3f, Assets.npcSlime);
        currentAnimation = idle;
        health = 1;
        currentAnimation = leftMove;
        physicalDamage = 1; //no effect, as slime bash is hard coded to do only 1 damage
        setDefence(0);
        level = 1;
    }

    @Override
    protected void attack() {
        attack.checkAttackCollision();
        animationCtrlTimer.start(300);
        currentAnimation = target.getX()-x < 0?attack.getOverridingAnimations().get(0):attack.getOverridingAnimations().get(1);
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
    public void initialize(Handler handler, float x, float y, int oX, int oY) {
        super.initialize(handler, x, y, oX, oY);
        attack = new SlimeBash(handler, this);
    }
}
