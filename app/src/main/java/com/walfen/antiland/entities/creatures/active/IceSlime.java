package com.walfen.antiland.entities.creatures.active;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Handler;
import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.entities.properties.attack.meleeAttacks.SlimeBash;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.IceSpike;
import com.walfen.antiland.entities.properties.attack.rangedAttacks.RangedAttack;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.tiles.Tile;
import com.walfen.antiland.untils.MSTimeController;

public class IceSlime extends Active {

    private Animation leftMove, rightMove, attackLeft, attackRight, idle, currentAnimation;
    private MSTimeController animationCtrlTimer = new MSTimeController();

    public IceSlime() {
        super(Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 5000, 202, 5);
        spottingRange = 512;
        maxIdealRange = 384;
        minIdealRange = 128;
        patrolRange = 512;
        giveUpRange = 1300;
        bounds.left = 0;
        bounds.top = 0;
        bounds.right = Tile.TILEWIDTH;
        bounds.bottom = Tile.TILEHEIGHT;
        faction = 1;
        leftMove = new Animation(0.6f, Assets.iceSlimeMovementLeft);
        rightMove = new Animation(0.6f, Assets.iceSlimeMovementRight);
        attackLeft = new Animation(0.3f, Assets.iceSlimeAttackLeft);
        attackRight = new Animation(0.3f, Assets.iceSlimeAttackRight);
        idle = new Animation(0.3f, Assets.ice_spike);
        currentAnimation = idle;
        setEntityHealth(5);
        currentAnimation = leftMove;
        magicalDamage = 2; //no effect, as slime bash is hard coded to do only 1 damage
        setDefence(1);
        level = 3;
    }


    @Override
    protected void attack() {
        RangedAttack ra = (RangedAttack)attack;
        ra.generateAttack(100, 100);
        animationCtrlTimer.start(1000);
        currentAnimation = target.getX()-x < 0?attackLeft:attackRight;
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
        attack.draw(canvas);
    }

    @Override
    protected void onDeath() {
        if(Math.random() <= 0.2)
            handler.getWorld().getItemManager().addItem(Item.slimeGel.createNew((int)(x + width/2 - Item.ITEMWIDTH/2), (int)(y + height - Item.ITEMHEIGHT), (int)(Math.random()*5)+1));
    }

    @Override
    public void initialize(Handler handler, float x, float y, int oX, int oY) {
        super.initialize(handler, x, y, oX, oY);
        attack = new IceSpike(handler, () -> magicalDamage, this);
    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmap(Assets.iceSlimeMovementLeft[1], xSize, ySize);
    }

    @Override
    public String getName() {
        return "Slime (Ice)";
    }
}
