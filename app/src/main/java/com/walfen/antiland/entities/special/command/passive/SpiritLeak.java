package com.walfen.antiland.entities.special.command.passive;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.entities.Entity;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.untils.Utils;

public class SpiritLeak extends EntityGenerator {

    private Animation gapAnimation;

    public SpiritLeak() {
        super(206, 256, 5, GenerationSpeed.NORMAL_GENERATION, 1103);
        gapAnimation = new Animation(0.3f, Assets.spiritLeak);
    }

    @Override
    public void update() {
        super.update();
        gapAnimation.update();
    }

    @Override
    protected void spawnEntity() {
        int locationX, locationY;

        locationX = Utils.pickNumberBetween((int) (x - spawnRange), (int) (x + spawnRange));
        locationY = Utils.pickNumberBetween((int) (y - spawnRange), (int) (y + spawnRange));
        int ge = Math.random()>0.4?generatedEntity:205;
        Entity e = Entity.entityList[ge].clone();
        e.initialize(handler, locationX, locationY, locationX, locationY, 0);
        handler.getWorld().getEntityManager().addEntityHot(e);
    }

    @Override
    public void draw(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        gapAnimation.draw(canvas, new Rect(left, top, left+128, top+128));
    }
}
