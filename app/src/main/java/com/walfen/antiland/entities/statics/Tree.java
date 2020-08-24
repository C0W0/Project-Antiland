package com.walfen.antiland.entities.statics;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.walfen.antiland.Constants;
import com.walfen.antiland.entities.properties.attack.Attacks;
import com.walfen.antiland.gfx.Assets;
import com.walfen.antiland.gfx.ImageEditor;
import com.walfen.antiland.items.Item;
import com.walfen.antiland.tiles.Tile;

public class Tree extends StaticEntity {

    public Tree() {
        super(Tile.TILEWIDTH, Tile.TILEHEIGHT*4, 2);

        bounds.left = 50;
        bounds.top = 100;
        bounds.right = bounds.left + width - 100;
        bounds.bottom = bounds.top + height - 100;
        setEntityHealth(2);
        setDefence(1);
        dmgPercentMod[Attacks.Type.MAGICAL_FIRE] = 400;
        id = 2;
    }

    @Override
    public void receiveDamage(int num, int type) {
        super.receiveDamage(num, type);
        handler.getWorld().getItemManager().addItem(Item.apple.createNew((int)(x + width/2 - Item.ITEMWIDTH/2 + Math.random()*64-32),
                (int)(y + height - Item.ITEMHEIGHT + 64), (int)(Math.random()*2)+1));
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        int left = (int)(x - handler.getGameCamera().getxOffset());
        int top = (int)(y - handler.getGameCamera().getyOffset());
        canvas.drawBitmap(Assets.tree, null, new Rect(left, top, left+width, top+height), Constants.getRenderPaint());
    }

    @Override
    protected void onDeath() {
        handler.getWorld().getItemManager().addItem(Item.woodItem.createNew((int)(x + width/2 - Item.ITEMWIDTH/2), (int)(y + height - Item.ITEMHEIGHT), (int)(Math.random()*5)+1));
        handler.getPlayer().increaseXp(1);
    }

    @Override
    public Bitmap getTexture(int xSize, int ySize) {
        return ImageEditor.scaleBitmap(Assets.tree, xSize, ySize);
    }

    @Override
    public String getName() {
        return "Tree";
    }
}
