package com.walfen.antiland.entities.creatures.npc.trader;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.entities.creatures.Creature;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.items.Item;

import java.util.ArrayList;

public class WandererCrab extends Trader {

    private Animation dynamicTexture;

    public WandererCrab() {
        super(Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT, 401);
        dynamicTexture = new Animation(3, Assets.npcCrab);
        dynamicTexture.scale(width, height);
    }

    @Override
    protected ArrayList<Item> initInvItems() {
        return TraderInvManager.getFootTraderInv(handler);
    }

    @Override
    public void update() {
        super.update();
        dynamicTexture.update();
    }

    @Override
    protected void drawEntity(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        dynamicTexture.draw(canvas, new Rect(left, top, left+width, top+height));
    }

    @Override
    protected void onDeath() {}

    @Override
    public void receiveDamage(int num, int type) {}

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmap(Assets.npcCrab[0], xSize, ySize);
    }

    @Override
    public String getName() {
        return "Crab Trader";
    }
}
