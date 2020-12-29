package com.walfen.antiland.entities.statics;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.tiles.Tile;

public class MagicalTree extends StaticEntity {

    public MagicalTree() {
        super(Tile.TILEWIDTH*3, Tile.TILEHEIGHT*4, 706);
        bounds.left = 116;
        bounds.top = Tile.TILEHEIGHT*2;
        bounds.right = 116+48;
        bounds.bottom = height;
    }

    @Override
    public void update() {

    }

    @Override
    public void receiveDamage(int num, int type) {
        if(num > 10){
            health = 0;
            active = false;
            die();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        canvas.drawBitmap(Assets.tree2, null, new Rect(left, top, left+width, top+height), Constants.getRenderPaint());
    }

    @Override
    protected void onDeath() {
        handler.getWorld().getItemManager().addItem(Item.woodItem.createNew((int)(x + width/2 - Item.ITEMWIDTH/2), (int)(y + height - Item.ITEMHEIGHT), (int)(Math.random()*5)+1));
        handler.getPlayer().increaseXp(10);
    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmap(Assets.tree2, xSize, ySize);
    }

    @Override
    public String getName() {
        return "Magical Tree";
    }
}
