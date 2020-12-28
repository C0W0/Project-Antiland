package com.walfen.antiland.entities.statics;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.tiles.Tile;

public class Rock1 extends StaticEntity {

    public Rock1() {
        super(Tile.TILEWIDTH, Tile.TILEHEIGHT, 730);
        bounds.left = 0;
        bounds.top = 0;
        bounds.bottom = height;
        bounds.right = width;
    }


    @Override
    public void receiveDamage(int num, int type) {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        canvas.drawBitmap(Assets.rock, null, new Rect(left, top, left+width, top+height), Constants.getRenderPaint());
    }

    @Override
    protected void onDeath() {
        handler.getWorld().getItemManager().addItem(Item.woodItem.createNew((int)(x + width/2 - Item.ITEMWIDTH/2), (int)(y + height - Item.ITEMHEIGHT), (int)(Math.random()*5)+1));
        handler.getPlayer().increaseXp(1);
    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmap(Assets.rock, xSize, ySize);
    }

    @Override
    public String getName() {
        return "Rock";
    }
}
