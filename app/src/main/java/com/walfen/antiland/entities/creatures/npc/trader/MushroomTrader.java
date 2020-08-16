package com.walfen.antiland.entities.creatures.npc.trader;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.items.Item;

import java.util.ArrayList;

public class MushroomTrader extends Trader {

    public MushroomTrader() {
        super(192, 232, 6);
    }

    @Override
    protected ArrayList<Item> initInvItems() {
        return TraderInvManager.getFootTraderInv(handler);
    }

    @Override
    protected void onDeath() {

    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        canvas.drawBitmap(Assets.npcMushroom[0], null, new Rect(left, top, left+width, top+height), Constants.getRenderPaint());
    }
}
