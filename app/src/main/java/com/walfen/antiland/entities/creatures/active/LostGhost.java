package com.walfen.antiland.entities.creatures.active;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.attack.meleeAttacks.SoulStrike;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.untils.MSTimeController;

public class LostGhost extends Active {


    private Animation movement, currentAnimation;
    private MSTimeController animationCtrlTimer = new MSTimeController();

    public LostGhost() {
        super(DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT, 3500, 206, 15);
        spottingRange = 640;
        maxIdealRange = 128;
        minIdealRange = 0; // melee
        patrolRange = 640;
        giveUpRange = 1300;
        bounds.left = 40;
        bounds.top = 0;
        bounds.right = 90;
        bounds.bottom = 120;
        faction = 1;
        speed = 10;
        movement = new Animation(0.8f, Assets.lostGhostMovement);

        currentAnimation = movement;
        magicalDamage = 4;
        setEntityHealth(4);
        setDefence(0);
        level = 5;
    }

    @Override
    public void update() {
        super.update();
        if(animationCtrlTimer.atTarget())
            currentAnimation = movement;
        currentAnimation.update();
    }

    @Override
    protected void attack() {
        attack.checkAttackCollision();
        currentAnimation = attack.getOverridingAnimations().get(0);
        animationCtrlTimer.start(600);
    }

    @Override
    protected void onDeath() {
        handler.getPlayer().increaseXp(2);
    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmapForced(Assets.lostGhostMovement[0], xSize, ySize);
    }

    @Override
    public String getName() {
        return "Lost Ghost";
    }

    @Override
    public void draw(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        currentAnimation.draw(canvas, new Rect(left, top, left+width, top+height));
    }

    @Override
    public void initialize(Handler handler, float x, float y, int oX, int oY, int status) {
        super.initialize(handler, x, y, oX, oY, 0);
        attack = new SoulStrike(handler, magicalDamage, this);
    }
}
