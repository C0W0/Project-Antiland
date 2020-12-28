package com.walfen.antiland.entities.statics;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.tiles.Tile;

public class Boat extends StaticEntity {

    private Bitmap texture;

    public Boat(Bitmap texture, int id) {
        super(Tile.TILEWIDTH*3, Tile.TILEHEIGHT*2, id);
        bounds.left = 0;
        bounds.top = 40;
        bounds.right = width;
        bounds.bottom = height;
        this.texture = texture;
    }

    @Override
    public void receiveDamage(int num, int type) {

    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmapForced(texture, xSize, ySize);
    }

    @Override
    public String getName() {
        return "Boat";
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        canvas.drawBitmap(texture, null, new Rect(left, top, left+texture.getWidth(), top+texture.getHeight()), Constants.getRenderPaint());
    }

    @Override
    protected void onDeath() {

    }

}
