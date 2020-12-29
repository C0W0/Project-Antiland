package com.walfen.antiland.entities.creatures.npc.trader;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.Animation;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.tiles.Tile;

import java.util.ArrayList;

public class FoxKeeper extends Trader {

    private Animation dynamicTexture;

    public FoxKeeper() {
        super(Tile.TILEWIDTH*2, Tile.TILEHEIGHT, 403);
        dynamicTexture = new Animation(1, Assets.foxKeeper);
    }

    @Override
    public boolean interactionCheck(int innerDeduction) {
        if(status == 0)
            return false;
        return super.interactionCheck(innerDeduction);
    }

    @Override
    public void receiveDamage(int num, int type) {}

    @Override
    public void update() {
        super.update();
        dynamicTexture.update();
    }

    @Override
    protected void drawHeadSign(Canvas canvas) {
        if(status == 0){
            int iX = (int)(x+width/2-32-handler.getGameCamera().getxOffset());
            int iY = (int)(y-68-handler.getGameCamera().getyOffset());
            Rect destRect = new Rect(iX, iY, iX+64, iY+64);
            canvas.drawBitmap(Assets.headSignGray, null, destRect, Constants.getRenderPaint());
            canvas.drawBitmap(Assets.hsgTrade, null, destRect, Constants.getRenderPaint());
        }else
            super.drawHeadSign(canvas);
    }

    @Override
    protected ArrayList<Item> initInvItems() {
        return TraderInvManager.getPortTraderInv(handler);
    }

    @Override
    protected void drawEntity(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        dynamicTexture.draw(canvas, new Rect(left, top, left+width, top+height));
    }

    @Override
    protected void onDeath() {

    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmapForced(Assets.foxKeeperIcon, xSize, ySize);
    }

    @Override
    public String getName() {
        return "Mr. Fox Keeper";
    }
}
