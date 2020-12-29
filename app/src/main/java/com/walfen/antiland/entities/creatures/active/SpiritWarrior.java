package com.walfen.antiland.entities.creatures.active;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.attack.meleeAttacks.SoulStrike;
import com.walfen.antiland.entities.properties.attack.meleeAttacks.SoulSwipe;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.untils.MSTimeController;

public class SpiritWarrior extends Active {

    private Animation movement, currentAnimation;
    private MSTimeController animationCtrlTimer = new MSTimeController();

    public SpiritWarrior() {
        super(DEFAULT_CREATURE_WIDTH*2, DEFAULT_CREATURE_HEIGHT*2, 3000, 207, 15);
        spottingRange = 1024;
        maxIdealRange = 128;
        minIdealRange = 0; // melee
        patrolRange = 640;
        giveUpRange = 2048;
        bounds.left = 40;
        bounds.top = 0;
        bounds.right = 90;
        bounds.bottom = 120;
        faction = 1;
        speed = 14;
        movement = new Animation(0.6f, Assets.spiritWarriorMovement);
        movement.scale(width, height);

        currentAnimation = movement;
        setEntityHealth(15);
        magicalDamage = 10;
        setDefence(20);
        setMagicalDefence(5);
        level = 20;
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
        currentAnimation.scale(width, height);
        animationCtrlTimer.start(600);
    }

    @Override
    protected void onDeath() {

    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmapForced(Assets.spiritWarriorMovement[0], xSize, ySize);
    }

    @Override
    public String getName() {
        return "Spirit Warrior";
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
        attack = new SoulSwipe(handler, magicalDamage, this);
    }
}
