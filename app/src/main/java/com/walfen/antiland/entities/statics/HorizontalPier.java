package com.walfen.antiland.entities.statics;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.tiles.Tile;

public class HorizontalPier extends StaticEntity {

    public HorizontalPier() {
        super(Tile.TILEWIDTH*3, Tile.TILEHEIGHT*2, 723);
        bounds.left = 0;
        bounds.top = 0;
        bounds.right = 1;
        bounds.bottom = 1;
    }

    @Override
    protected void onDeath() {

    }

    @Override
    public void receiveDamage(int num, int type) {

    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmapForced(Assets.pierHorizontal, xSize, ySize);
    }

    @Override
    public String getName() {
        return "Pier";
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        canvas.drawBitmap(Assets.pierHorizontal, null, new Rect(left, top, left+width, top+height), Constants.getRenderPaint());
    }

    @Override
    public boolean isBackground() {
        return true;
    }
}
