package com.walfen.antiland.entities.creatures.npc.trader;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.items.Item;

import java.util.ArrayList;

public class MushroomTrader extends Trader {

    public MushroomTrader() {
        super(192, 232, 402);
    }

    @Override
    protected ArrayList<Item> initInvItems() {
        return TraderInvManager.getFootTraderInv(handler);
    }

    @Override
    protected void onDeath() {

    }

    @Override
    protected void drawEntity(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        canvas.drawBitmap(Assets.npcMushroom[0], null, new Rect(left, top, left+width, top+height), Constants.getRenderPaint());
    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmap(Assets.npcMushroom[0], xSize, ySize);
    }

    @Override
    public String getName() {
        return "Mushroom Trader";
    }

    @Override
    public void receiveDamage(int num, int type) {}
}
