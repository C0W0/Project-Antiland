package com.walfen.antiland.entities.creatures.active;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.MotionEvent;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.properties.attack.meleeAttacks.SlimeBash;
import com.walfen.antiland.entities.properties.attack.meleeAttacks.SoulBash;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.tiles.Tile;
import com.walfen.antiland.ui.conversation.Conversation;
import com.walfen.antiland.untils.MSTimeController;

import java.util.ArrayList;

public class TrappedSpirit extends Active {

    private Animation leftMove, rightMove, upMove, downMove, currentAnimation;
    private Animation leftIdle, rightIdle;
    private MSTimeController animationCtrlTimer = new MSTimeController();

    public TrappedSpirit() {
        super(DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT, 3000, 204, 15);
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
        leftMove = new Animation(0.3f, new Bitmap[]{Assets.trappedSpiritMovementLeft[0]});
        rightMove = new Animation(0.3f, new Bitmap[]{Assets.trappedSpiritMovementRight[0]});
        upMove = new Animation(0.3f, new Bitmap[]{Assets.trappedSpiritMovementUp});
        downMove = new Animation(0.3f, new Bitmap[]{Assets.trappedSpiritMovementDown});
        leftIdle = new Animation(0.5f, Assets.trappedSpiritMovementLeft);
        rightIdle = new Animation(0.5f, Assets.trappedSpiritMovementRight);

        currentAnimation = downMove;
        setEntityHealth(15);
        currentAnimation = leftMove;
        physicalDamage = 2;
        setDefence(0);
        level = 1;
    }

    public TrappedSpirit(int id){
        super(DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT, 3000, id, 15);
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
        leftMove = new Animation(0.3f, new Bitmap[]{Assets.trappedSpiritMovementLeft[0]});
        rightMove = new Animation(0.3f, new Bitmap[]{Assets.trappedSpiritMovementRight[0]});
        upMove = new Animation(0.3f, new Bitmap[]{Assets.trappedSpiritMovementUp});
        downMove = new Animation(0.3f, new Bitmap[]{Assets.trappedSpiritMovementDown});
        leftIdle = new Animation(0.5f, Assets.trappedSpiritMovementLeft);
        rightIdle = new Animation(0.5f, Assets.trappedSpiritMovementRight);

        currentAnimation = downMove;
        setEntityHealth(15);
        currentAnimation = leftMove;
        physicalDamage = 2;
        setDefence(0);
        level = 1;
    }


    @Override
    public void update(){
        super.update();
        if(animationCtrlTimer.atTarget()){
            if(xMove == 0 && yMove == 0){ //idle animation
                int distanceX = (int)(target.getX()-x);
                int distanceY = (int)(target.getY()-y);
                if(Math.abs(distanceX) > Math.abs(distanceY)) //left or right
                    currentAnimation = distanceX < 0?leftIdle:rightIdle;
                else //up or down
                    currentAnimation = distanceY < 0?upMove:downMove;
            } else if(Math.abs(xMove) > Math.abs(yMove)) //left or right
                currentAnimation = xMove < 0?leftMove:rightMove;
            else //up or down
                currentAnimation = yMove < 0?upMove:downMove;
        }
        currentAnimation.update();
    }

    @Override
    protected void attack() {
        attack.checkAttackCollision();
        int distanceX = (int)(target.getX()-x);
        int distanceY = (int)(target.getY()-y);
        if(Math.abs(distanceX) > Math.abs(distanceY)) //left or right
            currentAnimation = distanceX < 0?attack.getOverridingAnimations().get(0):attack.getOverridingAnimations().get(1);
        else //up or down
            currentAnimation = distanceY < 0?attack.getOverridingAnimations().get(2):attack.getOverridingAnimations().get(3);
        animationCtrlTimer.start(600);
    }

    @Override
    protected void onDeath() {
        handler.getUIManager().getCGUI().resetJoystick();
        handler.getUIManager().getCGUI().getAttackJoystick().lockUp();
        handler.getUIManager().getCGUI().getMovementJoystick().lockUp();
        ArrayList<Conversation> c = new ArrayList<>();
        c.add(new Conversation("No! This is impossible! You can't just destroy me!", Assets.trappedSpiritMovementDown, false));
        c.add(new Conversation("After all these year! After all of the suffering!", Assets.trappedSpiritMovementDown, false));
        handler.getUIManager().hideUI();
        handler.getUIManager().getConvBox().setConversationList(c, () -> {
            handler.getWorld().triggerWorldEvent(1);
            handler.getUIManager().popUpMessage("As the evil spirit fades away, the entire temple starts shaking. Suddenly, a piece of wall collapsed and " +
                    "a tunnel towards the exit was presented in front of you. /nl/Hurry! The entire temple is /nl/disintegrating!");
            handler.getUIManager().getCGUI().resetJoystick();
            handler.getUIManager().getCGUI().getAttackJoystick().unlock();
            handler.getUIManager().getCGUI().getMovementJoystick().unlock();
        });
        handler.getUIManager().getConvBox().setActive();
    }

    @Override
    public void initialize(Handler handler, float x, float y, int oX, int oY, int status) {
        super.initialize(handler, x, y, oX, oY, 0);
        attack = new SoulBash(handler, physicalDamage, this);
    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return Assets.trappedSpiritMovementDown;
    }

    @Override
    public String getName() {
        return "? ? ?";
    }

    @Override
    public void draw(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        currentAnimation.draw(canvas, new Rect(left, top, left+width, top+height));
    }
}
