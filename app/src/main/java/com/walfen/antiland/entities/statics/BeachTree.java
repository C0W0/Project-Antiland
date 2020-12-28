package com.walfen.antiland.entities.statics;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.tiles.Tile;

public class BeachTree extends StaticEntity {

    public BeachTree() {
        super(Tile.TILEWIDTH*3, Tile.TILEHEIGHT*3, 705);
        bounds.left = 40;
        bounds.top = Tile.TILEHEIGHT*2;
        bounds.right = 84;
        bounds.bottom = height;
    }

    @Override
    public void receiveDamage(int num, int type) {
        super.receiveDamage(num, type);
        handler.getWorld().getItemManager().addItem(Item.apple.createNew((int)(x + width/2 - Item.ITEMWIDTH/2 + Math.random()*32-16),
                (int)(y + height - Item.ITEMHEIGHT + 32), (int)(Math.random()*2)+1));
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        canvas.drawBitmap(Assets.tree3, null, new Rect(left, top, left+width, top+height), Constants.getRenderPaint());
    }

    @Override
    protected void onDeath() {
        handler.getWorld().getItemManager().addItem(Item.woodItem.createNew((int)(x + width/2 - Item.ITEMWIDTH/2), (int)(y + height - Item.ITEMHEIGHT), (int)(Math.random()*5)+1));
        handler.getPlayer().increaseXp(1);
    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmap(Assets.tree3, xSize, ySize);
    }

    @Override
    public String getName() {
        return "Tree";
    }
}
